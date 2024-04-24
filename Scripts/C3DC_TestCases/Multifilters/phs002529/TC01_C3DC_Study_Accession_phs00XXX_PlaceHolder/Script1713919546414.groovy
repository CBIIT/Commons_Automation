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
/* Study- phs002529, 
Breed - Beagle, Bulldog, Dalmatian
Sex - Female
*/
WebUI.closeBrowser()

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC01_C3DC_Study_Accession_phs003111_SexAtBirth-Male.xlsx')

WebUI.waitForElementPresent(findTestObject('C3DC/Navbar/Explore-Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Navbar/Explore-Tab')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Facet'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Filters/Study/Study_Facet')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Accession/Study_Accession-Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Filters/Study/Study_Accession/Study_Accession-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Accession/phs003111-Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Filters/Study/Study_Accession/phs003111-Chkbx')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth_Ddn'),5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth_Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/Male_Chkbx'),5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('C3DC/Filters/Demographics/SexAtBirth/Male_Chkbx')

//Read Statbar
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarC3DC'('C3DC/Statbar/Diagnosis-Count', 'C3DC/Statbar/Participants-Count', 
    'C3DC/Statbar/Studies-Count')

//Clicking the Participants tab - By default Participants tab is clicked
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Participants-Tbl', 
    'C3DC/ResultTabs/Participants-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', 
    GlobalVariable.G_WebTabnameParticipants, GlobalVariable.G_CypherTabnameParticipants, GlobalVariable.G_QueryParticipantsTab)


////clicking the Diagnosis tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Diagnosis_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Diagnosis_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'CCDI/ExplorePage/CCDI_Diagnosis_Tbl', 
//    'CCDI/ExplorePage/CCDI_Diagnosis_TblHdr', 'CCDI/ExplorePage/CCDI_Diagnosis_TblNextBtn', 
//    GlobalVariable.G_WebTabnameDiagnosis, GlobalVariable.G_CypherTabnameDiagnosis, GlobalVariable.G_QueryDiagnosisTab)
//
////when the stat for diagnosis gets added in the stat bar, then the above line should be updated for the global variable from participants to diagnosis.
////clicking the Studies tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Studies_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Studies_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'CCDI/ExplorePage/CCDI_Studies_Tbl', 
//    'CCDI/ExplorePage/CCDI_Studies_TblHdr', 'CCDI/ExplorePage/CCDI_Studies_TblNextBtn', 
//    GlobalVariable.G_WebTabnameStudies, GlobalVariable.G_CypherTabnameStudies, GlobalVariable.G_QueryStudiesTab)
//
////when the stat for diagnosis gets added in the stat bar, then the above line should be updated for the global variable from participants to diagnosis.
////clicking the Samples tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Samples_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Samples_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Samples, 'CCDI/ExplorePage/CCDI_Samples_Tbl', 
//    'CCDI/ExplorePage/CCDI_Samples_TblHdr', 'CCDI/ExplorePage/CCDI_Samples_TblNextBtn', 
//    GlobalVariable.G_WebTabnameSamples, GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)
//
////clicking the Files tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Files_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Files_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Files, 'CCDI/ExplorePage/CCDI_Files_Tbl', 
//    'CCDI/ExplorePage/CCDI_Files_TblHdr', 'CCDI/ExplorePage/CCDI_Files_TblNextBtn', 
//    GlobalVariable.G_WebTabnameFiles, GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)
WebUI.closeBrowser()

