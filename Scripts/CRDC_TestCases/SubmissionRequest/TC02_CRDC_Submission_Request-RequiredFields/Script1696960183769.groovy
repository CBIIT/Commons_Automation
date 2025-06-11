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

CustomKeywords.'utilities.CrdcDH.navigateToCrdc'()

CustomKeywords.'utilities.CrdcDH.loginToCrdcOtp'('Submitter')
WebUI.setViewPortSize(1920, 1080)


WebUI.delay(2)
WebUI.waitForElementVisible(findTestObject('CRDC/NavBar/SubmissionRequest-Tab'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/NavBar/SubmissionRequest-Tab')

WebUI.waitForElementPresent(findTestObject('CRDC/SubmissionRequest/Start_a_SubmissionRequest-Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/SubmissionRequest/Start_a_SubmissionRequest-Btn')

WebUI.delay(1)
WebUI.waitForElementPresent(findTestObject('CRDC/SubmissionRequest/readAndAcceptPopUp-Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/SubmissionRequest/readAndAcceptPopUp-Btn')

CustomKeywords.'utilities.CrdcDH.verifyStatusBar'('NEW')
CustomKeywords.'utilities.CrdcDH.enterPrincipalInvestigatorInfo'(2)
CustomKeywords.'utilities.CrdcDH.enterPrimaryContactInfo'(2)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))
WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'utilities.CrdcDH.verifyStatusBar'('IN PROGRESS')

CustomKeywords.'utilities.CrdcDH.enterProgramInfo'('Other', 6)
CustomKeywords.'utilities.CrdcDH.enterStudyInfo'(1)

CustomKeywords.'utilities.CrdcDH.enterFundingAgencyInfo'(1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))
WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'utilities.CrdcDH.enterDataAccessTypeAndDbGapRegInfo'('Controlled', 'Yes')

CustomKeywords.'utilities.CrdcDH.enterCancerTypeAndSubjectsInfo'('Cholangiocarcinoma', 'Homo', 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))
WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

CustomKeywords.'utilities.CrdcDH.verifyStatusBar'('IN PROGRESS')
CustomKeywords.'utilities.CrdcDH.enterTargetDeliveryAndExpectedPublicationDate'()

CustomKeywords.'utilities.CrdcDH.selectDataTypes'('genomics', 'proteomics')

CustomKeywords.'utilities.CrdcDH.selectFileTypes'(5, 6, 1, 1)

WebUI.click(findTestObject('CRDC/SubmissionRequest/Save-Btn'))
WebUI.click(findTestObject('CRDC/SubmissionRequest/Next-Btn'))

//Verification starts here
CustomKeywords.'utilities.CrdcDH.verifyPrincipalInvestigatorInfo'(2)

CustomKeywords.'utilities.CrdcDH.verifyPrimaryContactInfo'(2)


CustomKeywords.'utilities.CrdcDH.verifyProgramInfo'(6)

CustomKeywords.'utilities.CrdcDH.verifyStudyInfo'(1)

CustomKeywords.'utilities.CrdcDH.verifyFundingAgencyAndDbGaPInfo'(1)

CustomKeywords.'utilities.CrdcDH.verifyDataTypes'('genomics', 'proteomics')

CustomKeywords.'utilities.CrdcDH.verifyFileTypes'(5, 6, 1, 1)

CustomKeywords.'utilities.CrdcDH.clickSubmitButton'()

WebUI.closeBrowser()

