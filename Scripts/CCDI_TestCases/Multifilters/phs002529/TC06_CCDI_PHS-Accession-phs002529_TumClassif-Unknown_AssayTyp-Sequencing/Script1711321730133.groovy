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
/* Study- Glioma, 
Breed - Beagle, Bulldog, Dalmatian
Sex - Female
*/
WebUI.closeBrowser()
 
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC06_CCDI_PHS-Accession-phs002529_TumClassif-Unknown_AssayTyp-Sequencing.xlsx')

//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')
//System.out.println ("Closed the popup window");
 

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/Navbar/Explore_Menu'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/Navbar/Explore_Menu')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/Study_Facet'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/Study_Facet')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002529_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002529_Chkbx')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/Samples_Facet'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/Samples_Facet')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/SampleTumorClassification/SampleTumorClassif_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/SampleTumorClassification/SampleTumorClassif_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/SampleTumorClassification/Unknown_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Samples_Facet/SampleTumorClassification/Unknown_Chkbx')
 

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory_Facet'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory_Facet')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/AssayType/AssayType_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/AssayType/AssayType_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/AssayType/Sequencing_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/AssayType/Sequencing_Chkbx')
 
 
//Read findTestObject('Object Repository/CCDI/ExplorePage/Filters/DataCategory_Facet/AssayType/AssayType_Ddn')Statbar
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCCDIhub'('Object Repository/CCDI/Statbar/Studies_Cnt','Object Repository/CCDI/Statbar/Participants_Cnt', 'Object Repository/CCDI/Statbar/Samples_Cnt',
	'Object Repository/CCDI/Statbar/Files_Cnt')

//clicking the Participants tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/Participants_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/Participants_ResultsTab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'Object Repository/CCDI/ExplorePage/CCDI_Participants_Tbl',
	'Object Repository/CCDI/ExplorePage/CCDI_Participants_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Participants_TblNextBtn', GlobalVariable.G_WebTabnameParticipants,	GlobalVariable.G_CypherTabnameParticipants, GlobalVariable.G_QueryParticipantsTab)

WebUI.closeBrowser()

 