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
	 * Verifies the PBAC Permissions for the user
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User)
	 */ 
	@Keyword
	public static void verifyPbacPermissionDefaults(String userRole) {
		String loggedInAs = WebUI.getText(findTestObject('CRDC/Login/UserProfile-Dd'))
		
		//As admin user, find user in Manage Users table
		if (loggedInAs.toLowerCase().contains("admin") && !userRole.equalsIgnoreCase("admin-self")) { // if verifying self as admin, skip search
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
}//class ends