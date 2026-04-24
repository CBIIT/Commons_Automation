"""
Shared Playwright **chromium.launch** options for CDS scripts.

- **Jenkins / CI / PLAYWRIGHT_HEADLESS:** headless, bundled Chromium (no ``channel`` unless
  ``PLAYWRIGHT_CHANNEL_CHROME=1``). On Linux, adds typical container args
  (``--no-sandbox``, etc.).
- **Local:** headed **Google Chrome** via ``channel="chrome"`` (override with env above).

Set ``PLAYWRIGHT_CHANNEL_CHROME=1`` on an agent that only has Chrome installed (no
``playwright install chromium``).
"""

from __future__ import annotations

import os
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

    Default Chrome when **not** headless. When headless, omit ``channel`` so Playwright
    uses bundled Chromium unless ``PLAYWRIGHT_CHANNEL_CHROME`` is set.
    """
    headless = should_run_headless()
    kw: dict[str, Any] = {"headless": headless}
    force_chrome_channel = _env_truthy("PLAYWRIGHT_CHANNEL_CHROME")

    if force_chrome_channel or not headless:
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
