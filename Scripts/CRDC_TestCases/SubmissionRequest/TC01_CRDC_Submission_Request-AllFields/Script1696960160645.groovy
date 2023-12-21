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

CustomKeywords.'ctdc.utilities.Crdc.navigateToCrdc'()

CustomKeywords.'ctdc.utilities.Crdc.loginToCrdc'()

WebUI.delay(1)

WebUI.click(findTestObject('CRDC/NavBar/Start_a_SubmissionRequest-Btn'))

CustomKeywords.'ctdc.utilities.Crdc.verifyStatusBar'('NEW')

CustomKeywords.'ctdc.utilities.Crdc.enterPiInfo'(1, 1, 1, 1, 5, 1)

CustomKeywords.'ctdc.utilities.Crdc.enterPrimaryContactInfo'(2, 2, 2, 2, 10, 2)

//CustomKeywords.'ctdc.utilities.Crdc.enterAdditionalContactInfo'(2, 1, 2, 2, 11, 2)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'ctdc.utilities.Crdc.verifyStatusBar'('IN PROGRESS')

CustomKeywords.'ctdc.utilities.Crdc.enterProgramInfo'('DCCPS')

CustomKeywords.'ctdc.utilities.Crdc.enterStudyInfo'(1, 1, 1)

CustomKeywords.'ctdc.utilities.Crdc.enterFundingAgencyAndDbGaPInfo'(1, 1, 1, 1, 1)

//CustomKeywords.'ctdc.utilities.Crdc.enterPublicationsInfo'(1, 1, 1, 1)

//CustomKeywords.'ctdc.utilities.Crdc.enterRepositoryInfo'('Clinical', 1, 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'ctdc.utilities.Crdc.enterDataAccessAndDiseaseInfo'('Cholangiocarcinoma', 'Lung', 'Mus', 1, 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

//'immunology', 'epidemiologic', 'imaging', , 'other', 'other-clinical'
CustomKeywords.'ctdc.utilities.Crdc.selectDataTypes'('immunology', 'epidemiologic', 'imaging', 'other')

CustomKeywords.'ctdc.utilities.Crdc.selectFileTypes'(5, 6, 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))

WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

//Verification starts here
CustomKeywords.'ctdc.utilities.Crdc.verifyPiInfo'(1, 1, 1, 1, 5, 1)

CustomKeywords.'ctdc.utilities.Crdc.verifyPrimaryContactInfo'(2, 2, 2, 2, 10, 2)

//CustomKeywords.'ctdc.utilities.Crdc.verifyAdditionalContactInfo'(2, 1, 2, 2, 11, 2)

CustomKeywords.'ctdc.utilities.Crdc.verifyProgramInfo'(4)

CustomKeywords.'ctdc.utilities.Crdc.verifyStudyInfo'(1, 1, 1)

CustomKeywords.'ctdc.utilities.Crdc.verifyFundingAgencyAndDbGaPInfo'(1, 1, 1, 1, 1)

//CustomKeywords.'ctdc.utilities.Crdc.verifyPublicationsInfo'(1, 1, 1, 1)

//CustomKeywords.'ctdc.utilities.Crdc.verifyRepositoryInfo'('Clinical', 1, 1, 1)

CustomKeywords.'ctdc.utilities.Crdc.verifyDataAccessAndDiseaseInfo'('Cholangiocarcinoma', 'Breast', 'Mus', 1, 1, 1)

CustomKeywords.'ctdc.utilities.Crdc.verifyDataTypes'('immunology', 'epidemiologic', 'imaging')

CustomKeywords.'ctdc.utilities.Crdc.verifyFileTypes'(5, 6, 1, 1)

CustomKeywords.'ctdc.utilities.Crdc.clickSubmitButton'()

WebUI.closeBrowser()



