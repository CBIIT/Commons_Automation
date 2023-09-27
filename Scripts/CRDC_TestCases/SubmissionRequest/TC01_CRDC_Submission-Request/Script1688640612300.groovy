import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary as FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver as FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions as FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths

/*This test script:
 - Opens the browser of choice: Chrome, Firefox or Edge
 - Driver opened by Katalon is used in Selenium.
 - Takes the Query from input excel and fetches data from Neo4j database.
   Saves the results from neo4j and application in the same name mentioned in the input excel.
 - Clicks on the Cases button in the Navbar of ICDC's homepage.
 - Clicks on the Filter 'Breed' from left pane
 - Selects the specific check box from 'Breed' filter.
 - Reads the results displayed for the selected filter (from all the pages in UI) and saves in the excel mentioned in Input file
 - Reads Neo4j DB using the query from Input file and saves the data in the excel mentioned in Input file
 - Reads Neo4j excel and Webdata excel as lists and compares the data.
 */
WebUI.closeBrowser()

CustomKeywords.'ctdc.utilities.functions.navigateToCrdc'()

CustomKeywords.'ctdc.utilities.functions.loginToCrdc'()

//WebUI.waitForElementPresent(findTestObject('CRDC/NavBar/WarningBanner_Continue-Btn'), 5)
//WebUI.click(findTestObject('CRDC/NavBar/WarningBanner_Continue-Btn'))
//WebUI.waitForElementPresent(findTestObject('CRDC/NavBar/SubmissionRequest-Tab'), 5)
//WebUI.click(findTestObject('CRDC/NavBar/SubmissionRequest-Tab'))
WebUI.waitForElementPresent(findTestObject('CRDC/NavBar/Start_a_SubmissionRequest-Btn'), 5)

WebUI.delay(1)

WebUI.click(findTestObject('CRDC/NavBar/Start_a_SubmissionRequest-Btn'), FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'ctdc.utilities.functions.verifyStatusBar'('NEW')

CustomKeywords.'ctdc.utilities.functions.enterPiInfo'(1, 1, 1, 1, 5, 1)

CustomKeywords.'ctdc.utilities.functions.enterPrimaryContactInfo'(2, 2, 2, 2, 10, 2)

CustomKeywords.'ctdc.utilities.functions.enterAdditionalContactInfo'(2, 1, 2, 2, 11, 2)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'ctdc.utilities.functions.verifyStatusBar'('IN PROGRESS')

CustomKeywords.'ctdc.utilities.functions.enterProgramInfo'('DCCPS', 6, 6, 6)

CustomKeywords.'ctdc.utilities.functions.enterStudyInfo'(1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.enterFundingAgencyAndDbGaPInfo'(1, 1, 1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.enterPublicationsInfo'(1, 1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.enterRepositoryInfo'('Clinical', 1, 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'ctdc.utilities.functions.enterDataAccessAndDiseaseInfo'('Cholangiocarcinomaetc', 'Breast', 'Mus', 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

//'immunology', 'epidemiologic', 'imaging'
CustomKeywords.'ctdc.utilities.functions.selectDataTypes'('other', 'diagnosis', 'outcome')

CustomKeywords.'ctdc.utilities.functions.selectFileTypes'(7, 8, 4, 3)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

//Verification starts here
CustomKeywords.'ctdc.utilities.functions.verifyPiInfo'(1, 1, 1, 1, 5, 1)

CustomKeywords.'ctdc.utilities.functions.verifyPrimaryContactInfo'(2, 2, 2, 2, 10, 2)

CustomKeywords.'ctdc.utilities.functions.verifyAdditionalContactInfo'(2, 1, 2, 2, 11, 2)

CustomKeywords.'ctdc.utilities.functions.verifyProgramInfo'(6, 6, 6)

CustomKeywords.'ctdc.utilities.functions.verifyStudyInfo'(1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.verifyFundingAgencyAndDbGaPInfo'(1, 1, 1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.verifyPublicationsInfo'(1, 1, 1, 1)

CustomKeywords.'ctdc.utilities.functions.verifyRepositoryInfo'('Clinical', 1, 1, 1)

WebUI.delay(5)



//WebUI.verifyEqual(null, null)

