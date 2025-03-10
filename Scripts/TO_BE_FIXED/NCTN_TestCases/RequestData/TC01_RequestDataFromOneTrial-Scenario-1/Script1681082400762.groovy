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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.callTestCase(findTestCase('TO_BE_FIXED/NCTN_TestCases/Login/TC01_Login-Administrator'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementPresent(findTestObject('null'), 3)
WebUI.click(findTestObject('null'))

WebUI.waitForElementPresent(findTestObject('null'), 3)
WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue(
        'ResearchPlanTitle', 1))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue(
        'ResearchPlanDescription', 1))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue(
        'NctNumberPubMedID', 1))

WebUI.click(findTestObject('null'))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue('NameOfAor', 
        1))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue('AorTitle', 
        1))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue('AorEmail', 
        1))

WebUI.setText(findTestObject('null'), findTestData('NCTN/RequestData').getValue('AorEntity', 
        1))

WebUI.click(findTestObject('null'))

WebUI.click(findTestObject('null'))


WebUI.verifyElementText(findTestObject('null'), 'Step 2: Summary of Requested Data ')

WebUI.verifyElementText(findTestObject('null'), findTestData('NCTN/RequestData').getValue(
        'NctNumAndTitle', 1))

WebUI.verifyLinksAccessible(['https://pubmed.ncbi.nlm.nih.gov/26503200/', 'https://pubmed.ncbi.nlm.nih.gov/12123/'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('null'))

WebUI.waitForElementPresent(findTestObject('null'), 3)

WebUI.click(findTestObject('null'))

WebUI.verifyLinksAccessible(['https://nctn-data-archive.nci.nih.gov/sites/default/files/DUA/NCTN_NCORP_Data_Archive_DUA_No_CA.pdf'
        , 'https://nctn-data-archive.nci.nih.gov/sites/default/files/DUA/NCTN_NCORP_Data_Archive_DUA_CA.pdf'], FailureHandling.STOP_ON_FAILURE)



