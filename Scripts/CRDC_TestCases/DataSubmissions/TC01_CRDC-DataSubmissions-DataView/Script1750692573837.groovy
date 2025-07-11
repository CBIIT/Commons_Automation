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
import com.kms.katalon.core.testdata.TestDataFactory

/*This test script:
 - Opens the browser of choice: Chrome, Firefox or Edge
 - Driver opened by Katalon is used in Selenium.
 - Logs in as Submitter
 - Creates DS, uploads, and validates
 - Verifies data submission dashboard
 */
WebUI.closeBrowser()

//Login as Submitter
CustomKeywords.'utilities.CrdcDH.navigateToCrdc'()
WebUI.setViewPortSize(1920, 1080)
CustomKeywords.'utilities.CrdcDH.loginToCrdcOtp'('Submitter')
WebUI.delay(2)

//Create DS
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/NavBar/DataSubmissions-Tab')
CustomKeywords.'utilities.CrdcDHPbac.createDataSubmission'()
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/DataSubmissions/DataSubmissionName-Link')

//Upload data
CustomKeywords.'utilities.CrdcDHPbac.uploadMetadataUI'('InputFiles/CRDC/MetadataData/program.tsv')
CustomKeywords.'utilities.CrdcDHPbac.uploadMetadataUI'('InputFiles/CRDC/MetadataData/study.tsv')
CustomKeywords.'utilities.CrdcDHPbac.uploadMetadataUI'('InputFiles/CRDC/MetadataData/participant.tsv')

//Wait for uploading to finish
CustomKeywords.'utilities.Utils.waitForElementToDisappear'('CRDC/DataSubmissions/Validation/Uploading-Text', 15)

//Validate data
WebUI.waitForElementClickable(findTestObject('CRDC/DataSubmissions/Validate-Btn'), 30)
CustomKeywords.'utilities.TestRunner.clickTab'('CRDC/DataSubmissions/Validate-Btn')
WebUI.waitForElementClickable(findTestObject('CRDC/DataSubmissions/Validate-Btn'), 30)

//Verify table headers
CustomKeywords.'utilities.CrdcDH.verifyDataSubmissionTableHeaders'('UploadActivities')
CustomKeywords.'utilities.CrdcDH.verifyDataSubmissionTableHeaders'('ValidationResults')
CustomKeywords.'utilities.CrdcDH.verifyDataSubmissionTableHeaders'('DataView')

//Verify Data View table
CustomKeywords.'utilities.CrdcDH.verifyDataViewMatchesMetadata'('InputFiles/CRDC/MetadataData/program.tsv', 'program')
CustomKeywords.'utilities.CrdcDH.verifyDataViewMatchesMetadata'('InputFiles/CRDC/MetadataData/study.tsv', 'study')
CustomKeywords.'utilities.CrdcDH.verifyDataViewMatchesMetadata'('InputFiles/CRDC/MetadataData/participant.tsv', 'participant')

WebUI.closeBrowser()