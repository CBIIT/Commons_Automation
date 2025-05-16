# Commons_Automation

This repository contains automated test scripts for **Data Commons** applications. It provides a framework to validate data consistency by comparing UI data with CSV/TSV file sources using a hybrid tech stack including **Groovy**, **Java**, **Python**, and **Selenium**.

---

## Overview

The framework performs automated comparisons between:
- **UI data** collected via Katalon and Selenium.
- **Reference data** loaded from TSV files into Python pandas DataFrames.

### Execution Flow

1. **Query File Input**: Execution begins by reading an Excel query file.
2. **UI Interaction**: 
   - Filters are applied on the UI using Selenium Keywords.
   - Data is extracted and saved into an output Excel file.
3. **Reference Data Load**:
   - TSV files are read using Python’s pandas into DataFrames.
   - Data is joined and transformed as defined in the query schema.
   - Result is saved into a second output Excel file.
4. **Comparison**:
   - The two outputs are compared.
   - A test passes if data matches; mismatches result in test failure.

---

## 📁 Project Structure

| Folder / File              | Description |
|---------------------------|-------------|
| `.settings/`               | IDE and project settings |
| `Data Files/`              | Input data such as queries and expected outputs |
| `Drivers/`                 | Web drivers and support files |
| `Include/config/`          | Configuration files |
| `InputFiles/`              | Source data files (TSV, CSV, Excel) |
| `Keywords/utilities/`      | Custom Groovy keywords (e.g., UI actions, Python integration) |
| `Libs/internal/`           | Internal libraries |
| `Object Repository/`       | UI object mappings for Selenium |
| `Plugins/`                 | Plugin files |
| `Profiles/`                | Katalon profiles for environment configurations |
| `PythonFiles/`             | Python scripts for data processing |
| `Scripts/`                 | Core automation scripts |
| `Test Cases/`              | Groovy/Java-based test case implementations |
| `Test Listeners/`          | Katalon test lifecycle hooks |
| `Test Suites/`             | Collections of test cases for batch execution |
| `jenkins/`                 | Jenkins integration scripts |
| `requirements.txt`         | Python library dependencies |
| `console.properties`       | Console settings for Katalon |
| `Commons_Automation.prj`   | Project descriptor for Katalon Studio |

---

## Getting Started

### Prerequisites

- [Katalon Studio](https://www.katalon.com/)
- [Python 3.x](https://www.python.org/)
- Access to shared **Profiles** from SharePoint

### Cloning the Repo

```bash
git clone https://github.com/CBIIT/Commons_Automation.git
cd Commons_Automation
