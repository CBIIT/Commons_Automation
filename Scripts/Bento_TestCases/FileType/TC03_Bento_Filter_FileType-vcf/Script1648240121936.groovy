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
 - Initializes the Driver by Katalon and used in Selenium
 - Takes the query from input Excel file and stores for usage
 - Navigates the application and applies filters based on test case
 - Chooses the tab(s) for verification
 - Reads the results displayed in the UI for the selected filter (for 10 pages) and saves results in the web data sheet mentioned in input file
 - Runs Python scripts for each tab: merges disparate TSV files into one, creates dataframes tables in runtime, runs queries, and writes to db data sheet mentioned in input file
 - Compares web data sheet to db data sheet
 - Verifies stat bar numbers are as expected 
 */
WebUI.closeBrowser()
 
//Initiate
CustomKeywords.'utilities.TestRunner.RunKatalon'('TC03_Bento_Filter_FileType-vcf.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Banner/Bento_Warning_Continue_Btn') 
WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/NavBar/Bento_Cases-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/NavBar/Bento_Cases-Btn')

//Filter
WebUI.waitForElementClickable(findTestObject('Bento/Cases_page/Filter/FileType/FILETYPE_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Cases_page/Filter/FileType/FILETYPE_Ddn')
WebUI.waitForElementClickable(findTestObject('Bento/Cases_page/Filter/FileType/Vcf_Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Cases_page/Filter/FileType/Vcf_Chkbx')

//Statbar
CustomKeywords.'utilities.TestRunner.readStatBarBento'('Object Repository/Bento/StatBar/Bento_StatBar-Programs',
	'Object Repository/Bento/StatBar/Bento_StatBar-Arms', 'Object Repository/Bento/StatBar/Bento_StatBar-Cases', 'Object Repository/Bento/StatBar/Bento_StatBar-Samples',
	'Object Repository/Bento/StatBar/Bento_StatBar-Assays', 'Object Repository/Bento/StatBar/Bento_StatBar-Files')

//Cases tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Cases_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Cases_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_Cases, 'Object Repository/Bento/Cases_page/Bento_CasesTable',
	'Object Repository/Bento/Cases_page/Bento_CasesTableHeader', 'Object Repository/Bento/Cases_page/Bento_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
	'TsvDataCases', GlobalVariable.G_QueryCasesTab)

//Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Samples_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Samples_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_Samples, 'Object Repository/Bento/Cases_page/Bento_SamplesTable',
	'Object Repository/Bento/Cases_page/Bento_SamplesTableHeader', 'Object Repository/Bento/Cases_page/Bento_SamplesTabNextBtn', GlobalVariable.G_WebTabnameBiospecimens,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)
	
//Files tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Files_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Files_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_Files, 'Object Repository/Bento/Cases_page/Bento_FilesTable',
	'Object Repository/Bento/Cases_page/Bento_FilesTableHeader', 'Object Repository/Bento/Cases_page/Bento_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	'TsvDataFiles', GlobalVariable.G_QueryFilesTab)

WebUI.closeBrowser()