"""
Shared Playwright **chromium.launch** options for CDS scripts.

- **Jenkins:** headless **Google Chrome** via ``channel="chrome"`` (same binary family as
  Katalon's Chrome). No runtime ``playwright install chromium`` download — bake browsers
  into the image if you need bundled Chromium instead.
- **Other CI** (``CI=true``): headless **bundled** Chromium unless ``PLAYWRIGHT_CHANNEL_CHROME=1``.
- **Local:** headed **Google Chrome** via ``channel="chrome"``, unless headless overrides apply.

**Env overrides**

- ``PLAYWRIGHT_CHANNEL_CHROME=1`` — force system Chrome (any environment).
- ``PLAYWRIGHT_USE_BUNDLED_CHROMIUM=1`` — on Jenkins only: do **not** prefer system Chrome;
  use bundled Chromium (must be pre-installed in the image; Jenkins will not auto-download).

On non-Jenkins machines, if bundled Chromium is missing, ``launch_chromium`` may run
``python -m playwright install chromium`` once and retry (needs network unless cached).
"""

from __future__ import annotations

import os
import subprocess
import sys
from typing import Any


def _env_truthy(name: str) -> bool:
    return os.environ.get(name, "").strip().lower() in ("1", "true", "yes", "on")


def is_jenkins_environment() -> bool:
    """True when the job appears to be running under Jenkins."""
    if os.environ.get("JENKINS_URL") or os.environ.get("HUDSON_URL"):
        return True
    # Agent builds typically have both; avoids false positives from BUILD_ID alone.
    if os.environ.get("BUILD_ID") and os.environ.get("JENKINS_HOME"):
        return True
    return False


def should_run_headless() -> bool:
    """Headless for Jenkins, generic CI, or explicit PLAYWRIGHT_HEADLESS."""
    if _env_truthy("PLAYWRIGHT_HEADLESS"):
        return True
    if os.environ.get("CI", "").strip().lower() in ("true", "1", "yes"):
        return True
    if is_jenkins_environment():
        return True
    return False


def chromium_launch_kwargs() -> dict[str, Any]:
    """
    Keyword arguments for ``sync_playwright().chromium.launch(**kwargs)``.

    Uses **Google Chrome** when not headless, when ``PLAYWRIGHT_CHANNEL_CHROME`` is set,
    or on **Jenkins** while headless (Katalon/agents already ship Chrome — avoids slow
    ``playwright install chromium``). Set ``PLAYWRIGHT_USE_BUNDLED_CHROMIUM=1`` on Jenkins
    to use bundled Chromium instead (must be pre-installed).
    """
    headless = should_run_headless()
    kw: dict[str, Any] = {"headless": headless}
    force_chrome_channel = _env_truthy("PLAYWRIGHT_CHANNEL_CHROME")
    jenkins_wants_bundled = is_jenkins_environment() and _env_truthy("PLAYWRIGHT_USE_BUNDLED_CHROMIUM")
    # Jenkins + headless: prefer system Chrome unless explicitly asking for bundled Chromium.
    jenkins_use_system_chrome = is_jenkins_environment() and headless and not jenkins_wants_bundled

    if force_chrome_channel or not headless or jenkins_use_system_chrome:
        kw["channel"] = "chrome"

    if headless and sys.platform.startswith("linux"):
        extra = (
            "--no-sandbox",
            "--disable-setuid-sandbox",
            "--disable-dev-shm-usage",
        )
        existing = list(kw.get("args") or [])
        for a in extra:
            if a not in existing:
                existing.append(a)
        kw["args"] = existing

    return kw


def _browser_missing_message(exc: BaseException) -> bool:
    m = str(exc).lower()
    return any(
        s in m
        for s in (
            "executable doesn't exist",
            "browser has not been found",
            "playwright install",
            "could not find chrome",
        )
    )


def launch_chromium(chromium_browser_type: Any, **kwargs: Any) -> Any:
    """
    Call ``chromium_browser_type.launch(**kwargs)`` (pass ``p.chromium`` from sync_playwright).

    On **Jenkins**, missing browsers never trigger a runtime ``playwright install`` (too slow
    for CI); fix the image or use system Chrome (default). Elsewhere, may install chromium once.
    """
    try:
        return chromium_browser_type.launch(**kwargs)
    except Exception as e:
        if not _browser_missing_message(e):
            raise
        if kwargs.get("channel") == "chrome":
            raise RuntimeError(
                "Playwright could not launch Google Chrome (channel=chrome). "
                "Install Google Chrome on the agent, or on Jenkins set PLAYWRIGHT_USE_BUNDLED_CHROMIUM=1 "
                "and pre-run `python -m playwright install chromium` in the Docker image."
            ) from e
        if is_jenkins_environment():
            raise RuntimeError(
                "Bundled Chromium is not available for this Python. Jenkins jobs do not auto-download "
                "browsers during tests. Prefer system Chrome (default: unset PLAYWRIGHT_USE_BUNDLED_CHROMIUM), "
                "or bake `python -m playwright install chromium` into the image and set "
                "PLAYWRIGHT_USE_BUNDLED_CHROMIUM=1."
            ) from e
        print(
            "⚙️  Playwright Chromium missing for this interpreter; "
            "running: python -m playwright install chromium",
            flush=True,
        )
        proc = subprocess.run(
            [sys.executable, "-m", "playwright", "install", "chromium"],
            check=False,
        )
        if proc.returncode != 0:
            raise RuntimeError(
                f"playwright install chromium exited with {proc.returncode}. "
                "Pre-install browsers in the Jenkins image or pipeline, or set "
                "PLAYWRIGHT_CHANNEL_CHROME=1 if only Google Chrome is available."
            ) from e
        return chromium_browser_type.launch(**kwargs)
