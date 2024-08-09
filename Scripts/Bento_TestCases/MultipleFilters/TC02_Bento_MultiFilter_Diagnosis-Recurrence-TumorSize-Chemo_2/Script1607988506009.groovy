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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW

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

//WebUI.openBrowser('')
//WebUI.maximizeWindow()


CustomKeywords.'utilities.TestRunner.RunKatalon'('TC02_Bento_MultiFilter_Diagnosis-Recurrence-TumorSize-Chemo_2.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Banner/Bento_Warning_Continue_Btn')
System.out.println ("Closed the warning window");

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/NavBar/Bento_Cases-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/NavBar/Bento_Cases-Btn')


//WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/FilterByCases_Facet'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/FilterByCases_Facet')


WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/MedullaryCarcinoma_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/Recurrence Score/RecurrScore_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Recurrence Score/RecurrScore_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Recurrence Score/16_to_20_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Recurrence Score/RecurrScore_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/2_to_3_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/Chemotherapy/Chemotherapy_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Chemotherapy/Chemotherapy_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Chemotherapy/IVCMF-3WkCycle_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Chemotherapy/Chemotherapy_Ddn')

Thread.sleep(3000)


CustomKeywords.'utilities.TestRunner.readStatBarBento'('Object Repository/Bento/StatBar/Bento_StatBar-Programs',
	'Object Repository/Bento/StatBar/Bento_StatBar-Arms', 'Object Repository/Bento/StatBar/Bento_StatBar-Cases', 'Object Repository/Bento/StatBar/Bento_StatBar-Samples',
	'Object Repository/Bento/StatBar/Bento_StatBar-Assays', 'Object Repository/Bento/StatBar/Bento_StatBar-Files')

//clicking the Cases tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Cases_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Cases_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_Publications, 'Object Repository/Bento/Cases_page/Bento_CasesTable',
	'Object Repository/Bento/Cases_page/Bento_CasesTableHeader', 'Object Repository/Bento/Cases_page/Bento_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
	GlobalVariable.G_CypherTabnameCases, GlobalVariable.G_QueryCasesTab)

//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Samples_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Samples_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_Datasets, 'Object Repository/Bento/Cases_page/Bento_SamplesTable',
	'Object Repository/Bento/Cases_page/Bento_SamplesTableHeader', 'Object Repository/Bento/Cases_page/Bento_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)

//clicking the Files tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Bento/Cases_page/BentoResults_Files_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/BentoResults_Files_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('Bento',GlobalVariable.G_StatBar_ClinTrials, 'Object Repository/Bento/Cases_page/Bento_FilesTable',
	'Object Repository/Bento/Cases_page/Bento_FilesTableHeader', 'Object Repository/Bento/Cases_page/Bento_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)

WebUI.closeBrowser()

