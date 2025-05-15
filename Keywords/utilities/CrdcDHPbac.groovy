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
	 * Verifies the PBAC Permissions for the user
	 * @param User role (Fedlead, Dcp, Admin, Submitter, User)
	 */ 
	@Keyword
	public static void verifyPbacPermissionDefaults(String userRole) {
		//Find user in Manage Users table
		findAndEditUserByName(userRole)

		//Expand the Permissions panel and Email Notifications panel
		clickTab('CRDC/ManageUsers/PermissionsPanel-Ddn')
		clickTab('CRDC/ManageUsers/NotificationsPanel-Ddn')

		//Write expected results to sheet
		ReadExcel.writePbacExpectedPermissionsForRole(userRole,"CRDC/Pbac/Permissions")
	}
}//class ends