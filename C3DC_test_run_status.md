# C3DC study test run

Run attempted: 3 Oct 2026, Katalon Studio 9.7.7, profile C3DC_QA, Chrome headless, suite of one case (`TC01_C3DC_phs000466_SexAtBirth-Male`).

Katalon opened the Studio workbench and started a web view on port 51781, then sat idle at 0% CPU. No test case started, and no pass/fail result was produced. The full set of 237 study cases was not run.

## Checkbox fixes applied before the run

These clicks pointed at object files that did not exist. They are updated so the next run can find the checkbox.

| Test case | Problem | Change |
| --- | --- | --- |
| TC05_C3DC_phs002517_Diagnosis-AdenomaNOS | Study checkbox `phs0002517-Chkbx` does not exist | Clicks `phs002517-Chkbx` |
| TC05_C3DC_phs002517_Diagnosis-AdenomaNOS | `AdenomaNOS-Chkbx` did not exist | Added the object for `checkbox_Diagnosis_Adenoma, NOS` |
| TC04_C3DC_phs002599_AnatomicSite-Blood | Clicked missing `C420_Blood-Chkbx` | Clicks the existing `Blood-Chkbx` (`C42.0 : Blood`) |

The Adenoma checkbox id was taken from the diagnosis value stored for that study. It has not been confirmed on the QA page.

## Local data that does not match the checkbox label

These queries use the checkbox text and return no rows on the local Memgraph. If the QA page shows rows for that checkbox, the comparison will fail as a data mismatch.

| Test case | Checkbox label | Value stored locally |
| --- | --- | --- |
| TC04_C3DC_phs000466_AnatomicSite-C649KidneyNOS | `C64.9 : Kidney, NOS` | `kidney` |
| TC04_C3DC_phs002599_AnatomicSite-Blood | `C42.0 : Blood` | `blood` |
| TC11_C3DC_phs002790_Response-NotDone | `Not Done` | no `Not Done` response on phs002790 (`Complete Remission`, `Progressive Disease`, `Unknown`) |
