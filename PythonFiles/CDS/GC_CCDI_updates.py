"""
General **QA2** Data Commons — CCDI updates: compare Participants / Samples / Files to a local workbook.

- **Site (default):** ``https://general-stage.datacommons.cancer.gov/#/data``
  Override with env **GC_CCDI_DATA_COMMONS_URL**.

- **Base counts Excel:** default path is **InputFiles/CDS/CCDI basecounts.xlsx** (repo root;
  same layout as Kids First: header row with *Study Name*, *Participants* / *Samples* / *Files*
  columns, and a column whose name contains ``phs`` for PHS accession). Override with
  **GC_CCDI_EXCEL_PATH**.

- **After Continue:** seconds to wait for the app to load (default **4**). Override with
  **GC_CCDI_POST_CONTINUE_WAIT_SEC**.

Studies listed in **EXCLUDED_STUDY_NAMES** (e.g. the two HTAN imaging / primary sequencing rows)
are skipped. Each other workbook row with **PHS accession** and **Study Name** is verified in order:

1. Apply **PHS Accession** facet filter for that row’s PHS.
2. Open **Study Name** and select the matching study (same row).
3. Read **Participants**, **Samples**, and **Files** tab counts from the UI.
4. Compare to **base counts** in the Excel row; **PASS** only if all three match.

Override post-filter wait with **GC_CCDI_POST_FILTER_WAIT_SEC** (seconds after study select before
reading tabs; default **2**).
"""

from __future__ import annotations

import html
import os
import re
import sys
import time
from pathlib import Path

import pandas as pd
from playwright.sync_api import (
    Error as PlaywrightError,
    sync_playwright,
    TimeoutError as PlaywrightTimeoutError,
)

from cds_playwright_launch import chromium_launch_kwargs, launch_chromium

if sys.platform == "win32" and hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    sys.stderr.reconfigure(encoding="utf-8", errors="replace")

# -----------------------------------
# CONFIG
# -----------------------------------
_DEFAULT_DATA_COMMONS_URL = "https://general-stage.datacommons.cancer.gov/#/data"

REPORT_PATH = "GC_CCDI_updates_Report.html"


def _data_commons_url() -> str:
    v = os.environ.get("GC_CCDI_DATA_COMMONS_URL", "").strip()
    return v if v else _DEFAULT_DATA_COMMONS_URL


def _post_continue_settle_sec() -> float:
    """Seconds to sleep after **Continue** so the Data page can render before filters."""
    raw = os.environ.get("GC_CCDI_POST_CONTINUE_WAIT_SEC", "").strip()
    if raw:
        try:
            return max(0.0, float(raw))
        except ValueError:
            pass
    return 4.0


def _post_filter_settle_sec() -> float:
    """Seconds to sleep after PHS + study filters before reading tab counts (UI may update async)."""
    raw = os.environ.get("GC_CCDI_POST_FILTER_WAIT_SEC", "").strip()
    if raw:
        try:
            return max(0.0, float(raw))
        except ValueError:
            pass
    return 2.0


_SCRIPT_DIR = Path(__file__).resolve().parent
_REPO_ROOT = _SCRIPT_DIR.parent.parent  # …/PythonFiles/CDS → repo root (Commons_Automation)
DEFAULT_BASECOUNTS_WORKBOOK = _REPO_ROOT / "InputFiles" / "CDS" / "CCDI basecounts.xlsx"

# Optional: hardcode ``(phs, study_name)`` pairs here to **restrict** the run to a subset.
# If empty, every valid row in the Excel file is used.
SCENARIOS_OVERRIDE: list[tuple[str, str]] = []

# Omit these studies from PHS + Study verification (workbook rows and SCENARIOS_OVERRIDE).
EXCLUDED_STUDY_NAMES: tuple[str, ...] = (
    "Human Tumor Atlas Network (HTAN) imaging data",
    "Human Tumor Atlas Network (HTAN) primary sequencing data",
)


def _norm_study_for_compare(s: str) -> str:
    return re.sub(r"\s+", " ", (s or "").replace("\n", " ").strip()).casefold()


def _is_excluded_study(study: str) -> bool:
    key = _norm_study_for_compare(study)
    return any(_norm_study_for_compare(ex) == key for ex in EXCLUDED_STUDY_NAMES)


def _find_phs_column(df: pd.DataFrame) -> str | None:
    for c in df.columns:
        if "phs" in str(c).lower():
            return str(c)
    return None


def _norm_phs(val: str) -> str:
    return re.sub(r"\s+", "", str(val).strip().lower())


def load_expectations_workbook(excel_path: str) -> pd.DataFrame:
    path = Path(excel_path)
    if not path.is_file():
        raise FileNotFoundError(
            f"Base counts Excel not found: {path}\n"
            f"Expected default: InputFiles/CDS/CCDI basecounts.xlsx under repo root ({_REPO_ROOT}), "
            "or set env GC_CCDI_EXCEL_PATH.\n"
            "Expected columns include: Study Name, Number of Participants (or Participants), "
            "Samples, Number of Files (and a PHS / PHS ACCESSION column)."
        )

    raw_df = pd.read_excel(path, engine="openpyxl", header=None)
    try:
        header_row = next(
            i
            for i in range(min(15, len(raw_df)))
            if "study name" in " ".join(raw_df.iloc[i].astype(str).str.lower())
        )
    except StopIteration as e:
        raise RuntimeError(
            "Could not find a header row containing 'study name' in the first 15 rows."
        ) from e

    df = pd.read_excel(path, engine="openpyxl", header=header_row)
    df.columns = df.columns.map(str).str.strip()
    df = df.loc[:, ~df.columns.str.contains("^Unnamed", na=False)]
    if "Study Name" not in df.columns:
        raise RuntimeError("Excel must contain a 'Study Name' column.")
    df["Study Clean"] = (
        df["Study Name"].astype(str).str.replace("\n", " ").str.strip()
    )
    return df


def scenarios_from_workbook(df: pd.DataFrame) -> list[tuple[str, str]]:
    """Build (phs, study display) tuples from Excel when SCENARIOS_OVERRIDE is empty."""
    if SCENARIOS_OVERRIDE:
        filtered = [(p, s) for p, s in SCENARIOS_OVERRIDE if not _is_excluded_study(s)]
        if not filtered:
            raise RuntimeError(
                "SCENARIOS_OVERRIDE is set but every study is in EXCLUDED_STUDY_NAMES."
            )
        return filtered

    phs_col = _find_phs_column(df)
    if not phs_col:
        raise RuntimeError(
            "No column containing 'phs' in the workbook — add e.g. 'PHS Accession' for CCDI rows."
        )

    out: list[tuple[str, str]] = []
    seen: set[tuple[str, str]] = set()
    for _, row in df.iterrows():
        raw_phs = row[phs_col]
        if pd.isna(raw_phs):
            continue
        phs = _norm_phs(str(raw_phs))
        if not phs or phs == "nan":
            continue
        study = str(row["Study Clean"]).strip()
        if not study or study.lower() == "nan":
            continue
        if _is_excluded_study(study):
            continue
        key = (phs, study)
        if key in seen:
            continue
        seen.add(key)
        out.append((phs, study))

    if not out:
        raise RuntimeError(
            "No scenarios: add rows with PHS accession and Study Name to the workbook, "
            "or set SCENARIOS_OVERRIDE in GC_CCDI_updates.py."
        )
    return out


def lookup_expected_counts(
    df: pd.DataFrame, phs: str, study_display: str
) -> tuple[int, int, int]:
    """Return (participants, samples, files) from the workbook for this PHS + study."""
    study_key = study_display.replace("\n", " ").strip()
    mask_s = df["Study Clean"].str.strip() == study_key
    if not mask_s.any():
        mask_s = df["Study Clean"].str.lower().str.strip() == study_key.lower()

    phs_col = _find_phs_column(df)
    if phs_col:
        mask_p = df[phs_col].map(_norm_phs) == _norm_phs(phs)
        subset = df.loc[mask_s & mask_p]
    else:
        subset = df.loc[mask_s]

    if subset.empty:
        raise ValueError(
            f"No Excel row for PHS={phs!r} and study matching {study_key[:80]!r}…"
        )
    if len(subset) > 1:
        raise ValueError(
            f"Multiple Excel rows ({len(subset)}) for PHS={phs!r}; "
            "fix duplicate PHS + Study Name in the workbook."
        )

    row = subset.iloc[0]

    def _norm_col(s: str) -> str:
        return re.sub(r"\s+", " ", str(s).strip().lower())

    def _col(*names: str) -> int:
        wanted = [_norm_col(n) for n in names]
        # 1) Exact match (case / spacing insensitive)
        for n in wanted:
            for c in df.columns:
                if _norm_col(c) == n:
                    v = row[c]
                    if pd.isna(v):
                        raise ValueError(f"Empty cell in column {c!r}")
                    return int(float(v))
        # 2) Substring: column header contains the key phrase (Excel variants)
        for n in wanted:
            if len(n) < 4:
                continue
            for c in df.columns:
                cn = _norm_col(c)
                if n in cn or cn in n:
                    v = row[c]
                    if pd.isna(v):
                        continue
                    return int(float(v))
        raise KeyError(f"Missing column; tried {names!r}. Headers: {list(df.columns)!r}")

    p = _col("Number of Participants", "Participants", "# Participants")
    s = _col("Samples", "Number of Samples")
    f = _col("Number of Files", "Files", "Number of files")
    return p, s, f


# -----------------------------------
# UI helpers (aligned with GC_kidsfirst_Pgm.py)
# -----------------------------------
def dismiss_continue_if_present(page) -> None:
    try:
        page.get_by_role("button", name=re.compile(r"^Continue$", re.I)).click(
            timeout=12000
        )
        time.sleep(0.35)
    except PlaywrightTimeoutError:
        try:
            page.get_by_text("Continue").click(timeout=5000)
            time.sleep(0.35)
        except PlaywrightTimeoutError:
            pass


def get_tab_count(page, tab_name: str, *, attempts: int = 4) -> int:
    """Read ``(N)`` count from a Data page tab; retry briefly while counts populate."""
    last_err: Exception | None = None
    for _attempt in range(attempts):
        try:
            tab = page.get_by_role("tab", name=re.compile(tab_name, re.I))
            tab.wait_for(timeout=15000)
            text = tab.inner_text()
            match = re.search(r"\(([\d,]+)\)", text)
            if not match:
                raise ValueError(
                    f"No count in parentheses for tab {tab_name!r}; text was: {text!r}"
                )
            return int(match.group(1).replace(",", ""))
        except Exception as e:
            last_err = e
            time.sleep(0.85)
    raise ValueError(
        f"Could not read tab count for {tab_name!r} after {attempts} tries: {last_err}"
    )


def read_ui_psf_counts(page) -> tuple[int, int, int]:
    """Participants, Samples, Files tab counts from the current filtered Data view."""
    p_ui = get_tab_count(page, "Participants")
    s_ui = get_tab_count(page, "Samples")
    f_ui = get_tab_count(page, "Files")
    return p_ui, s_ui, f_ui


def _click_phs_facet_header(page) -> None:
    hdr = page.locator('[id="PHS Accession"]')
    if hdr.count() > 0:
        hdr.first.click()
    else:
        page.get_by_role(
            "button", name=re.compile(r"PHS\s+Accession", re.I)
        ).first.click()


def _phs_accordion_root(page):
    """The ``MuiAccordion-root`` that directly contains **PHS Accession** (not a parent of all facets)."""
    return page.locator('[id="PHS Accession"]').first.locator(
        'xpath=ancestor::div[contains(@class,"MuiAccordion-root")][1]'
    )


def _phs_search_input(page):
    """
    Text input for **PHS** facet search only.

    A loose ``//input`` under an expanded region can resolve to the **Study** facet box first
    (``placeholder="e.g. Study Name"``, ``aria-label="Search within Study facet"``). We scope to
    the PHS accordion and prefer PHS-specific placeholders/labels, then skip Study-labeled inputs.
    """
    acc = _phs_accordion_root(page)
    # Typical general-qa / qa2: phs000000 placeholder or PHS in aria-label
    for sel in (
        'input[type="text"][placeholder*="phs" i]',
        'input[type="text"][placeholder*="PHS" i]',
        'input[type="text"][aria-label*="PHS" i]',
        'input[type="text"][aria-label*="Accession" i]',
    ):
        loc = acc.locator(sel)
        try:
            if loc.count() > 0:
                return loc.first
        except PlaywrightError:
            continue

    # Walk inputs in this accordion only; skip Study facet search
    try:
        n = acc.locator("input[type='text']").count()
    except PlaywrightError:
        n = 0
    for i in range(n):
        inp = acc.locator("input[type='text']").nth(i)
        try:
            alb = (inp.get_attribute("aria-label") or "").lower()
            ph = (inp.get_attribute("placeholder") or "").lower()
        except PlaywrightError:
            continue
        if "study" in alb or "study name" in ph:
            continue
        if "phs" in ph or "phs" in alb or "accession" in alb:
            return inp

    # Fallback: first non-Study text input in PHS accordion
    for i in range(n):
        inp = acc.locator("input[type='text']").nth(i)
        try:
            alb = (inp.get_attribute("aria-label") or "").lower()
            ph = (inp.get_attribute("placeholder") or "").lower()
        except PlaywrightError:
            continue
        if "study" in alb or "study name" in ph:
            continue
        return inp

    return acc.locator("input[type='text']").first


def _click_phs_suggestion(page, phs_value: str, *, pause: float = 1.5) -> None:
    time.sleep(pause)
    try:
        page.locator(
            "[role='listbox'] [role='option'], "
            ".MuiAutocomplete-listbox li, "
            ".MuiMenu-list li"
        ).filter(has_text=re.compile(re.escape(phs_value), re.I)).first.click(
            timeout=15000
        )
    except PlaywrightTimeoutError:
        page.get_by_text(phs_value, exact=False).first.click(timeout=15000)


def apply_phs_filter(page, phs_value: str) -> None:
    print(f"🔍 PHS ACCESSION → {phs_value}")
    _click_phs_facet_header(page)
    time.sleep(0.7)
    pinp = _phs_search_input(page)
    for attempt in range(3):
        try:
            pinp.wait_for(state="visible", timeout=12_000)
            break
        except PlaywrightTimeoutError:
            if attempt < 2:
                print("   …PHS search not visible yet — clicking PHS facet header again")
                _click_phs_facet_header(page)
                time.sleep(0.6)
                pinp = _phs_search_input(page)
            else:
                raise
    pinp.scroll_into_view_if_needed()
    pinp.fill(phs_value, force=True)
    _click_phs_suggestion(page, phs_value, pause=2.2)
    time.sleep(3)


def clear_phs_filter(page, phs_value: str) -> None:
    print(f"🔍 Clear PHS ACCESSION → {phs_value}")
    _click_phs_facet_header(page)
    time.sleep(0.7)
    inp = _phs_search_input(page)
    try:
        inp.scroll_into_view_if_needed(timeout=5000)
    except PlaywrightTimeoutError:
        pass
    inp.fill("", force=True)
    time.sleep(0.25)
    inp.fill(phs_value, force=True)
    try:
        _click_phs_suggestion(page, phs_value, pause=1.2)
    except PlaywrightError:
        pass
    time.sleep(2)
    page.keyboard.press("Escape")
    time.sleep(1)


def open_study_filter(page) -> None:
    page.locator("//div[@id='Study Name']").first.click()
    page.locator("//p[contains(@class,'MuiTypography-body1')]").first.wait_for(
        timeout=15000
    )
    time.sleep(2)


def build_xpath_text(text: str) -> str:
    if "'" in text:
        parts = text.split("'")
        return "concat(" + ", \"'\", ".join([f"'{p}'" for p in parts]) + ")"
    return f"'{text}'"


def toggle_study_checkbox(page, study_name: str) -> None:
    name = study_name.strip()
    safe_text = build_xpath_text(name)

    def find_and_click() -> bool:
        study = page.locator(f"xpath=//p[contains(normalize-space(), {safe_text})]")
        n = study.count()
        if n == 0:
            return False
        if n > 1:
            exact = page.locator(f"xpath=//p[normalize-space()={safe_text}]")
            if exact.count() >= 1:
                study = exact.first
            else:
                study = study.filter(has_text=name).first
        else:
            study = study.first

        study.scroll_into_view_if_needed()
        time.sleep(0.5)
        box = study.bounding_box()
        if box:
            page.mouse.click(box["x"] - 15, box["y"] + box["height"] / 2)
            return True
        return False

    for _ in range(5):
        if find_and_click():
            return
        page.mouse.wheel(0, 300)
        time.sleep(1)

    page.mouse.wheel(0, -10000)
    time.sleep(1.5)
    for i in range(35):
        if find_and_click():
            return
        print(f"↘️ Scrolling for study (step {i + 1})")
        page.mouse.wheel(0, 400)
        time.sleep(1.5)

    raise RuntimeError(f"Study row not found: {study_name[:100]}…")


def select_study(page, study_name: str) -> None:
    print(f"🔍 Study Name → {study_name[:70]}…")
    open_study_filter(page)
    toggle_study_checkbox(page, study_name)
    page.keyboard.press("Escape")
    time.sleep(2.5)


def deselect_study(page, study_name: str) -> None:
    print(f"🔍 Unselect study → {study_name[:70]}…")
    open_study_filter(page)
    toggle_study_checkbox(page, study_name)
    time.sleep(1.5)
    page.keyboard.press("Escape")
    time.sleep(2)


def generate_html_report(
    rows: list[dict], path: str = REPORT_PATH, *, page_url: str
) -> None:
    doc = f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>CCDI updates — GC QA2 Report</title>
  <style>
    body {{ font-family: Arial, sans-serif; margin: 24px; }}
    h1 {{ font-size: 1.25rem; }}
    table {{ border-collapse: collapse; width: 100%; max-width: 1400px; }}
    th, td {{ border: 1px solid #ccc; padding: 8px 10px; text-align: left; vertical-align: top; }}
    th {{ background: #333; color: #fff; text-align: center; }}
    td.count {{ text-align: center; font-weight: 600; }}
    td.pass {{ background: #d4edda; }}
    td.fail {{ background: #f8d7da; }}
    .meta {{ color: #444; margin-bottom: 16px; }}
  </style>
</head>
<body>
  <h1>CCDI updates — count check report (General QA2)</h1>
  <div class="meta"><strong>URL:</strong> {html.escape(page_url)}</div>
  <table>
    <thead>
      <tr>
        <th>#</th>
        <th>PHS accession</th>
        <th>Study name</th>
        <th>Participants (UI / base)</th>
        <th>Samples (UI / base)</th>
        <th>Files (UI / base)</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
"""
    for i, r in enumerate(rows, start=1):
        st = r["status"]
        cls = "pass" if st == "PASS" else "fail"
        doc += f"""      <tr>
        <td class="count">{i}</td>
        <td>{html.escape(r["phs"])}</td>
        <td>{html.escape(r["study"])}</td>
        <td class="count {cls}">{html.escape(r["participants_cell"])} <span style="color:#666">/ {html.escape(str(r.get("exp_p", "")))}</span></td>
        <td class="count {cls}">{html.escape(r["samples_cell"])} <span style="color:#666">/ {html.escape(str(r.get("exp_s", "")))}</span></td>
        <td class="count {cls}">{html.escape(r["files_cell"])} <span style="color:#666">/ {html.escape(str(r.get("exp_f", "")))}</span></td>
        <td class="count {cls}"><strong>{html.escape(st)}</strong></td>
      </tr>
"""
    doc += """    </tbody>
  </table>
</body>
</html>
"""
    with open(path, "w", encoding="utf-8") as f:
        f.write(doc)


def _fmt_cell(ui: int, exp: int) -> str:
    if ui == exp:
        return str(ui)
    return f"{ui} (base {exp})"


def run() -> None:
    excel_path = os.environ.get("GC_CCDI_EXCEL_PATH", "").strip()
    workbook = Path(excel_path).expanduser() if excel_path else DEFAULT_BASECOUNTS_WORKBOOK
    print(f"📎 Loading expectations from Excel: {workbook}")
    df = load_expectations_workbook(str(workbook))

    scenarios = scenarios_from_workbook(df)
    print(f"📋 Scenarios: {len(scenarios)} (from workbook or SCENARIOS_OVERRIDE)")

    url = _data_commons_url()
    print(f"🌐 Data Commons URL: {url}")

    results: list[dict] = []

    with sync_playwright() as p:
        _launch = chromium_launch_kwargs()
        print(f"⚙️  Playwright launch → {_launch}")
        browser = launch_chromium(p.chromium, **_launch)
        page = browser.new_page()
        try:
            page.goto(url, timeout=120_000, wait_until="domcontentloaded")
            dismiss_continue_if_present(page)
            settle = _post_continue_settle_sec()
            if settle:
                print(
                    f"⏳ Waiting {settle}s after **Continue** for the Data page to load…"
                )
                time.sleep(settle)
            try:
                page.wait_for_load_state("networkidle", timeout=90_000)
            except PlaywrightTimeoutError:
                print("ℹ️ networkidle timeout — continuing once DOM is usable.")
            try:
                page.wait_for_load_state("load", timeout=30_000)
            except PlaywrightTimeoutError:
                pass
            time.sleep(2)

            settle_tabs = _post_filter_settle_sec()
            n_sc = len(scenarios)
            for idx, (phs, study) in enumerate(scenarios, start=1):
                print(
                    f"\n{'=' * 72}\n"
                    f"Scenario {idx}/{n_sc} — PHS **{phs}** + study (base counts row)\n"
                    f"Study name: {study[:120]}{'…' if len(study) > 120 else ''}\n"
                    f"{'=' * 72}"
                )
                try:
                    exp_p, exp_s, exp_f = lookup_expected_counts(df, phs, study)
                except Exception as e:
                    print(f"⚠️ Excel lookup failed: {e}")
                    results.append(
                        {
                            "phs": phs,
                            "study": study,
                            "exp_p": "—",
                            "exp_s": "—",
                            "exp_f": "—",
                            "participants_cell": f"— ({e})",
                            "samples_cell": "—",
                            "files_cell": "—",
                            "status": "FAIL",
                        }
                    )
                    continue

                print(
                    f"📊 Base counts (Excel): Participants={exp_p}, Samples={exp_s}, Files={exp_f}"
                )
                print("   Step 1/3: Apply PHS Accession filter…")
                apply_phs_filter(page, phs)
                print("   Step 2/3: Select Study Name in facet…")
                select_study(page, study)
                if settle_tabs:
                    print(
                        f"   Step 3/3: Wait {settle_tabs}s for tab counts to match filters…"
                    )
                    time.sleep(settle_tabs)

                try:
                    p_ui, s_ui, f_ui = read_ui_psf_counts(page)
                except Exception as e:
                    print(f"❌ Could not read UI tab counts: {e}")
                    results.append(
                        {
                            "phs": phs,
                            "study": study,
                            "exp_p": str(exp_p),
                            "exp_s": str(exp_s),
                            "exp_f": str(exp_f),
                            "participants_cell": f"— ({e})",
                            "samples_cell": "—",
                            "files_cell": "—",
                            "status": "FAIL",
                        }
                    )
                    deselect_study(page, study)
                    clear_phs_filter(page, phs)
                    continue

                ok = p_ui == exp_p and s_ui == exp_s and f_ui == exp_f
                status = "PASS" if ok else "FAIL"
                print(
                    f"   UI tabs: Participants={p_ui}, Samples={s_ui}, Files={f_ui}\n"
                    f"   Verify: P {p_ui} vs base {exp_p} | S {s_ui} vs {exp_s} | F {f_ui} vs {exp_f}\n"
                    f"   → {status}"
                )

                results.append(
                    {
                        "phs": phs,
                        "study": study,
                        "exp_p": exp_p,
                        "exp_s": exp_s,
                        "exp_f": exp_f,
                        "participants_cell": _fmt_cell(p_ui, exp_p),
                        "samples_cell": _fmt_cell(s_ui, exp_s),
                        "files_cell": _fmt_cell(f_ui, exp_f),
                        "status": status,
                    }
                )

                print("   Cleanup: clear study + PHS for next row…")
                deselect_study(page, study)
                clear_phs_filter(page, phs)

            generate_html_report(results, page_url=url)
            print(f"\n📄 HTML report written: {REPORT_PATH}")
        finally:
            browser.close()


if __name__ == "__main__":
    run()
