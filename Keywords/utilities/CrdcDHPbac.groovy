package utilities
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.Iterator;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.openqa.selenium.Keys as Keys

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Cookie as Cookie
import com.kms.katalon.core.configuration.RunConfiguration
import org.apache.poi.ss.usermodel.*

import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import java.awt.Color
import java.util.*;

class CrdcDHPbac extends TestRunner {

	public static WebDriver driver

	//*************** Input functions start here ****************

	/**
	 * Find a user in the Manage Users table by name
	 * @param User role Fedlead, Dcp, Admin, Submitter, User
	 */
	@Keyword
	public static void findAndEditUserByName(String userRole) {
		String userNameAutomation = "pbac." + userRole + "-automation"
		boolean userFound = false
		WebUI.delay(2)

		while (!userFound) {
			driver = DriverFactory.getWebDriver()
			WebDriverWait wait = new WebDriverWait(driver, 10)
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tr[td]")))

			List<WebElement> rows = driver.findElements(By.xpath("//table//tr[td]"))

			for (WebElement row : rows) {
				try {
					List<WebElement> cells = row.findElements(By.tagName("td"))
					if (cells.size() < 6) continue // not enough columns, skip

						String nameText = cells[0].getText().trim()
					System.out.println("Name in cell: " + nameText)
					if (nameText.toLowerCase().contains(userNameAutomation.trim().toLowerCase())) {
						WebElement editBtn = cells[5].findElement(By.xpath(".//button[contains(text(),'Edit')]"))
						editBtn.click()
						System.out.println("User '${userNameAutomation}' found and Edit clicked.")
						userFound = true
						break
					}
				} catch (org.openqa.selenium.StaleElementReferenceException e) {
					System.out.println("Stale row element, trying next row...")
					continue
				}
			}

			// If not found, check for next page until last page
			if (!userFound) {
				TestObject nextButton = findTestObject('CRDC/ManageUsers/Next-Btn')
				boolean isDisabled = WebUI.getAttribute(nextButton, 'disabled') != null

				if (isDisabled) {
					KeywordUtil.markFailedAndStop("Reached the last page. User '${userNameAutomation}' not found in Manage Users table. Stopping test.")
				} else {
					WebUI.click(nextButton)
					WebUI.delay(2) // wait for new page
				}
			}
		}
	}

	/**
	 * Write the expected PBAC permissions for the role to Output Excel sheet
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User), path of Katalon Data File
	 */
	@Keyword
	def static void writePbacExpectedPermissionsForRole(String userRole, String dataFilePath) {
		def testData = TestDataFactory.findTestData(dataFilePath)
		int rowCount = testData.getRowNumbers()

		String outputPath = RunConfiguration.getProjectDir() + "/OutputFiles/PBAC_Defaults_Results.xlsx"
		File file = new File(outputPath)
		Workbook workbook = file.exists() ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook()
		Sheet sheet = workbook.getSheet(userRole) ?: workbook.createSheet(userRole)

		// Header
		Row header = sheet.createRow(0)
		CellStyle headerStyle = workbook.createCellStyle()
		Font headerFont = workbook.createFont()
		headerFont.setBold(true)
		headerStyle.setFont(headerFont)
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		String[] headers = [
			"Permission",
			"Expected",
			"Actual",
			"Result"
		]
		for (int i = 0; i < headers.length; i++) {
			Cell cell = header.createCell(i)
			cell.setCellValue(headers[i])
			cell.setCellStyle(headerStyle)
		}

		// Adjust column widths
		for (int columnIndex = 0; columnIndex <= 3; columnIndex++) {
			sheet.setColumnWidth(columnIndex, 8000)
		}

		for (int i = 1; i <= rowCount; i++) {
			String expected = testData.getValue(userRole, i)
			String internalName = testData.getValue('Permission Internal Name', i)

			if (!internalName) continue

				Row row = sheet.createRow(i)
			row.createCell(0).setCellValue(internalName)
			row.createCell(1).setCellValue(expected)
		}

		FileOutputStream fos = new FileOutputStream(file)
		workbook.write(fos)
		workbook.close()
		fos.close()

		KeywordUtil.logInfo("Expected permissions for ${userRole} written to ${outputPath}")
	}

	/**
	 * Get state of each PBAC permission, write to output sheet, and compare
	 * @param User role Fedlead, Dcp, Admin, Submitter, User
	 */
	@Keyword
	public static void verifyPbacActualPermissionsForRole(String userRole) {
		String outputPath = RunConfiguration.getProjectDir() + "/OutputFiles/PBAC_Defaults_Results.xlsx"
		FileInputStream fis = new FileInputStream(outputPath)
		Workbook workbook = new XSSFWorkbook(fis)
		Sheet sheet = workbook.getSheet(userRole)
		if (!sheet) {
			KeywordUtil.markFailed("Sheet '${userRole}' not found in output file.")
		}

		// Set up colors
		XSSFCellStyle greenStyle = (XSSFCellStyle) workbook.createCellStyle()
		greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index)
		greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		XSSFCellStyle redStyle = (XSSFCellStyle) workbook.createCellStyle()
		redStyle.setFillForegroundColor(IndexedColors.RED.index)
		redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

		// Locate checkboxes
		List<WebElement> checkboxes = WebUI.findWebElements(findTestObject('CRDC/ManageUsers/PbacOptions-Chkbx'), 10)

		// Default values
		boolean overallResult = true

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i)
			if (row == null) continue

				Cell actualCell = row.getCell(2) ?: row.createCell(2)
			Cell expectedCell = row.getCell(1)
			Cell passFailCell = row.getCell(3) ?: row.createCell(3)


			// Handle if fewer checkboxes in UI compared to expected
			if (i - 1 >= checkboxes.size()) {
				actualCell.setCellValue("MISSING_UI")
				passFailCell.setCellValue("Fail")
				passFailCell.setCellStyle(redStyle)
				println "Row ${i}: Expected=${expectedCell?.getStringCellValue()}, Actual=CHECKBOX_MISSING_UI -> Fail"
				overallResult = false
				continue
			}

			WebElement cb = checkboxes[i - 1]
			boolean isEnabled = cb.isEnabled()
			boolean isChecked = cb.isSelected()

			String actualStatus = ""
			if (!isEnabled && isChecked) actualStatus = "fixed_checked"
			else if (!isEnabled && !isChecked) actualStatus = "fixed_unchecked"
			else if (isEnabled && isChecked) actualStatus = "checked"
			else if (isEnabled && !isChecked) actualStatus = "unchecked"
			else actualStatus = "UNKNOWN"

			actualCell.setCellValue(actualStatus)

			Cell permissionNameCell = row.getCell(0)
			String permissionName = permissionNameCell?.getStringCellValue() ?: "Unknown"

			String expected = expectedCell?.getStringCellValue()
			if (expected && expected.equalsIgnoreCase(actualStatus)) {
				passFailCell.setCellValue("Pass")
				passFailCell.setCellStyle(greenStyle)
			} else {
				passFailCell.setCellValue("Fail")
				passFailCell.setCellStyle(redStyle)
				overallResult = false
			}

			// Console output
			KeywordUtil.logInfo("Row ${i} - ${permissionName} | Expected: ${expected} | Actual: ${actualStatus} | Result: ${passFailCell.getStringCellValue()}")
		}

		fis.close()
		FileOutputStream fos = new FileOutputStream(outputPath)
		workbook.write(fos)
		workbook.close()
		fos.close()

		if (overallResult == false) {
			KeywordUtil.markFailed("There is a FAILURE -- verify in output Excel: "+ outputPath)
		} else {
			KeywordUtil.logInfo("Actual permissions for '${userRole}' recorded and compared.")
		}
	}

	/**
	 * Resets the user's permissions to the default for the specified role by toggling away and back.
	 * @param role The target role to reset to (e.g., 'User', 'Submitter', 'Dcp', 'Fedlead', 'Admin')
	 */
	@Keyword
	public static void resetPermissions(String role) {
		KeywordUtil.logInfo("Resetting permissions for role: ${role}")
		// Map displayed roles in dropdown to internal names
		Map<String, String> roleDisplayMap = [
			'User'     : 'User',
			'Submitter': 'Submitter',
			'Dcp'      : 'Data Commons Personnel',
			'Fedlead'  : 'Federal Lead',
			'Admin'    : 'Admin'
		]

		String roleDisplay = roleDisplayMap[role]
		if (!roleDisplay) {
			KeywordUtil.markFailed("Invalid role passed to resetPermissions: ${role}")
			return
		}

		// Click role dropdown
		WebUI.click(findTestObject('CRDC/ManageUsers/Role-Ddn'))
		KeywordUtil.logInfo("Clicked role dropdown")

		// Find an alternate role to toggle to
		String alternateRole = roleDisplayMap.values().find { it != roleDisplay }
		if (!alternateRole) {
			KeywordUtil.markFailed("Could not find alternate role to switch to.")
			return
		}

		// Select alternate role
		WebUI.click(findTestObject('CRDC/ManageUsers/RoleDdn-Option', [('role') : alternateRole]))
		KeywordUtil.logInfo("Selected alternate role: ${alternateRole}")

		// Open dropdown again
		WebUI.click(findTestObject('CRDC/ManageUsers/Role-Ddn'))
		KeywordUtil.logInfo("Clicked role dropdown again")

		// Re-select target role
		WebUI.click(findTestObject('CRDC/ManageUsers/RoleDdn-Option', [('role') : roleDisplay]))
		KeywordUtil.logInfo("Selected target role: ${roleDisplay}")

		// Click Save
		WebUI.click(findTestObject('CRDC/ManageUsers/Save-Btn'))
		KeywordUtil.logInfo("Clicked Save")

		KeywordUtil.logInfo("Reset permissions by re-selecting role: ${roleDisplay}")
	}

	/**
	 * Verifies the PBAC Permissions for the user
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User)
	 */ 
	@Keyword
	public static void verifyPbacPermissionDefaults(String userRole) {
		String loggedInAs = WebUI.getText(findTestObject('CRDC/Login/UserProfile-Dd'))
		KeywordUtil.logInfo("Logged in as: '${loggedInAs}' and verifying permissions for: '${userRole}'")

		//As admin user only, find user in Manage Users table
		if (loggedInAs.toLowerCase().contains("admin") && !userRole.equalsIgnoreCase("admin-self")) {
			// if verifying self as admin, skip search
			findAndEditUserByName(userRole)
			resetPermissions(userRole)
			findAndEditUserByName(userRole)
		}

		//Expand the Permissions panel and Email Notifications panel
		clickTab('CRDC/ManageUsers/PermissionsPanel-Ddn')
		clickTab('CRDC/ManageUsers/NotificationsPanel-Ddn')

		//Write expected results to sheet
		writePbacExpectedPermissionsForRole(userRole,"CRDC/Pbac/Permissions")

		//Verify results
		verifyPbacActualPermissionsForRole(userRole)
	}

	/**
	 * Check all enabled permission checkboxes on the Edit User page
	 */
	@Keyword
	public static void checkAllEnabledPermissionCheckboxes() {
		List<WebElement> checkboxes = WebUI.findWebElements(findTestObject('CRDC/ManageUsers/PbacOptions-Chkbx'), 10)
		for (WebElement cb : checkboxes) {
			if (cb.isEnabled() && !cb.isSelected()) {
				cb.click()
				KeywordUtil.logInfo("Checked permission checkbox")
			}
		}
	}

	/**
	 * Uncheck all enabled permission checkboxes on the Edit User page
	 */
	@Keyword
	public static void uncheckAllEnabledPermissionCheckboxes() {
		List<WebElement> checkboxes = WebUI.findWebElements(findTestObject('CRDC/ManageUsers/PbacOptions-Chkbx'), 10)
		for (WebElement cb : checkboxes) {
			if (cb.isEnabled() && cb.isSelected()) {
				cb.click()
				KeywordUtil.logInfo("Unchecked permission checkbox")
			}
		}
	}

	/**
	 * Edits the PBAC Permissions for the user and writes expected permissions to output sheet (must be logged in as admin role)
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User), scenario (positive, negative)
	 */
	@Keyword
	public static void editPbacPermissions(String userRole, String scenario) {

		findAndEditUserByName(userRole)
		resetPermissions(userRole)
		findAndEditUserByName(userRole)

		//Expand the Permissions panel and Email Notifications panel
		clickTab('CRDC/ManageUsers/PermissionsPanel-Ddn')
		clickTab('CRDC/ManageUsers/NotificationsPanel-Ddn')

		if (scenario.equals("positive")) {
			checkAllEnabledPermissionCheckboxes()
			WebUI.delay(2)
			WebUI.click(findTestObject('CRDC/ManageUsers/Save-Btn'))
		}
		if (scenario.equals("negative")) {
			uncheckAllEnabledPermissionCheckboxes()
			WebUI.delay(2)
			WebUI.click(findTestObject('CRDC/ManageUsers/Save-Btn'))
		}

		//Write expected results to sheet
		writePbacExpectedPermissionsForRole(userRole + "-" + scenario,"CRDC/Pbac/Permissions")
	}


	/**
	 * Gets all the checked or unchecked PBAC Permissions for the user role from the output sheet
	 * @param User role sheet name, expected status (checked or unchecked)
	 */
	public static List<String> getPermissionsByStatus(String roleSheetName, String scenario) {
		List<String> permissions = new ArrayList<>();
		String outputPath = RunConfiguration.getProjectDir() + "/OutputFiles/PBAC_Defaults_Results.xlsx";

		FileInputStream fis = null
		Workbook workbook = null

		try {
			fis = new FileInputStream(outputPath)
			workbook = new XSSFWorkbook(fis)
			Sheet sheet = workbook.getSheet(roleSheetName + "-" + scenario)

			if (sheet == null) {
				KeywordUtil.markWarning("Sheet '${roleSheetName} + "-" + ${scenario}' not found.")
				return permissions
			}

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i)
				if (row == null) continue

					Cell permissionCell = row.getCell(0)  // Permission name
				Cell expectedCell = row.getCell(1)    // Expected status

				if (permissionCell == null || expectedCell == null) continue

					String status = expectedCell.getStringCellValue()
				if (scenario.equals("positive")) {
					//positive scenario
					if (status != null && (status.equalsIgnoreCase("checked") || status.equalsIgnoreCase("fixed_checked"))) {
						String permission = permissionCell.getStringCellValue()
						permissions.add(permission)
					}
				} else {
					//negative scenario
					if (status != null && (status.equalsIgnoreCase("unchecked") || status.equalsIgnoreCase("fixed_unchecked"))) {
						String permission = permissionCell.getStringCellValue()
						permissions.add(permission)
					}
				}
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Error reading permissions by status: " + e.getMessage())
		} finally {
			if (workbook != null) workbook.close()
			if (fis != null) fis.close()
		}
		return permissions
	}

	/**
	 * Create mappings between permissions and verification actions
	 */
	private static final Map<String, Closure> permissionToCheck = [:]
	static {
		permissionToCheck["submission_request:view"] = {
		}
		permissionToCheck["submission_request:create"] = {
		}
		permissionToCheck["submission_request:submit"] = {
		}
		permissionToCheck["submission_request:review"] = {
		}
		permissionToCheck["submission_request:cancel"] = {
		}
		permissionToCheck["data_submission:view"] = {
		}
		permissionToCheck["data_submission:create"] = {
		}
		permissionToCheck["data_submission:cancel"] = {
		}
		permissionToCheck["data_submission:review"] = {
			KeywordUtil.logInfo("[FUTURE DEVELOPMENT] Requires automating data submission flow post-creation")
		}
		permissionToCheck["data_submission:admin_submit"] = {
			KeywordUtil.logInfo("[FUTURE DEVELOPMENT] Requires automating data submission flow post-creation")
		}
		permissionToCheck["data_submission:confirm"] = {
			KeywordUtil.logInfo("[FUTURE DEVELOPMENT] Requires automating data submission flow post-creation")
		}
		permissionToCheck["program:manage"] = {
			KeywordUtil.logInfo("Verifying Manage Programs...")
			try {
				CrdcDH.clickHome()
				CrdcDH.clickAccountDropdown()
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/NavBar/ManagePrograms-Btn"), 5)) {
					KeywordUtil.markFailed("Manage Programs button is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - program:manage -> ${e.message}")
			}
		}
		permissionToCheck["study:manage"] = {
			KeywordUtil.logInfo("Verifying Manage Studies...")
			try {
				CrdcDH.clickHome()
				CrdcDH.clickAccountDropdown()
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/NavBar/ManageStudies-Btn"), 5)) {
					KeywordUtil.markFailed("Manage Studies button is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - study:manage -> ${e.message}")
			}
		}
		permissionToCheck["institution:manage"] = {
			KeywordUtil.logInfo("Verifying Manage Institutions...")
			try {
				CrdcDH.clickHome()
				CrdcDH.clickAccountDropdown()
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/NavBar/ManageInstitutions-Btn"), 5)) {
					KeywordUtil.markFailed("Manage Institutions button is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - institution:manage -> ${e.message}")
			}
		}
		permissionToCheck["user:manage"] = {
			KeywordUtil.logInfo("Verifying Manage Users...")
			try {
				CrdcDH.clickHome()
				CrdcDH.clickAccountDropdown()
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/NavBar/ManageUsers-Btn"), 5)) {
					KeywordUtil.markFailed("Manage Users button is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - user:manage -> ${e.message}")
			}
		}
		permissionToCheck["dashboard:view"] = {
			KeywordUtil.logInfo("Verifying Operation Dashboard...")
			try {
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/NavBar/OperationDashboard-Btn"), 5)) {
					KeywordUtil.markFailed("Operation Dashboard button is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - dashboard:view -> ${e.message}")
			}
		}
		permissionToCheck["access:request"] = {
			KeywordUtil.logInfo("Verifying Request Access...")
			try {
				CrdcDH.clickHome()
				CrdcDH.clickAccountDropdown()
				TestRunner.clickTab('CRDC/Login/UserProfile-Link')
				if (!WebUI.verifyElementPresent(findTestObject("CRDC/ManageUsers/RequestAccess-Btn"), 5)) {
					KeywordUtil.markFailed("Request Access link is not found")
				}
			} catch (Exception e) {
				KeywordUtil.markFailed("Error verifying permission - access:request -> ${e.message}")
			}
		}
	}

	/**
	 * Run all the verifications based on positive or negative scenario
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User), scenario (positive, negative)
	 */
	@Keyword
	public static void verifyPermissionsFunctional(String userRole, String scenario) {
		//For the scenario, get all the relevant permissions to verify for
		List<String> enabledPermissions = getPermissionsByStatus(userRole, scenario);

		for (String permission : enabledPermissions) {
			if (permissionToCheck.containsKey(permission)) {
				try {
					permissionToCheck.get(permission).run();
					KeywordUtil.markPassed("Verified functionality for permission: " + permission);
				} catch (Exception e) {
					KeywordUtil.markFailed("FAILED verification for permission: " + permission + " | " + e.getMessage());
				}
			} else {
				KeywordUtil.logInfo("[FUTURE DEVELOPMENT] No verification mapped for Email Notifications - " + permission);
			}
		}
	}
}