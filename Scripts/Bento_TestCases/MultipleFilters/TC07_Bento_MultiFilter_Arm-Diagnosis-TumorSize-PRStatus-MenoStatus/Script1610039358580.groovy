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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC07_Bento_MultiFilter_Arm-Diagnosis-TumorSize-PRStatus-MenoStatus.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Banner/Bento_Warning_Continue_Btn')
System.out.println ("Closed the warning window");

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/NavBar/Bento_Cases-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/NavBar/Bento_Cases-Btn')


//WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/FilterByCases_Facet'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/FilterByCases_Facet')



WebUI.waitForElementVisible(findTestObject('Bento/Cases_page/Filter/Arm/ARM_Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Arm/ARM_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Arm/C_Chkbx')

Thread.sleep(5000) //only if a wait is added, this step passes in headless browsers

WebUI.waitForElementVisible(findTestObject('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/PapillaryCarcinoma_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/Diagnosis/DIAGNOSIS_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/Below_1_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/TumorSize/TumorSize_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/PRStatus/PRStatus_Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/PRStatus/PRStatus_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/PRStatus/NotReported_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/PRStatus/Positive_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/PRStatus/PRStatus_Ddn')

WebUI.waitForElementClickable(findTestObject('Object Repository/Bento/Cases_page/Filter/MenopauseStatus/MenopauseStatus_Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/MenopauseStatus/MenopauseStatus_Ddn')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/MenopauseStatus/Pre_Chkbx')
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Bento/Cases_page/Filter/MenopauseStatus/MenopauseStatus_Ddn')
 

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

 

WebUI.closeBrowser()



