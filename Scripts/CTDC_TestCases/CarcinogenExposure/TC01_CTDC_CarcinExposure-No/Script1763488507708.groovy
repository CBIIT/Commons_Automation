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
  - Clicks on the Cases button in the Navbar of CTDC's homepage.
  - Clicks on the Filter 'Gender' from left pane
  - Selects the specific check box from 'Gender' filter.
  - Reads the results displayed for the selected filter (from all the pages in UI) and saves in the excel mentioned in Input file
  - Reads the stat bar - counts from UI
  - Reads Neo4j DB using the query from Input file and saves the data in the excel mentioned in Input file
  - Reads Neo4j excel and Webdata excel as lists and compares the data.
  - Compares the stat bar results read from UI, with that stored in the excel
  */
WebUI.closeBrowser()

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_CTDC_CarcinExposure-No.xlsx')

WebUI.waitForElementClickable(findTestObject('CTDC/Home/WarningBan_Continue_Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/Home/WarningBan_Continue_Btn')

WebUI.waitForElementClickable(findTestObject('CTDC/NavBar/Explore-Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/NavBar/Explore-Btn')

WebUI.waitForElementClickable(findTestObject('CTDC/Filters/CarcinExposure/CARCINOGENEXPLOSURE_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/Filters/CarcinExposure/CARCINOGENEXPLOSURE_Ddn')
 
WebUI.waitForElementClickable(findTestObject('CTDC/Filters/CarcinExposure/No_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/Filters/CarcinExposure/No_Chkbx')

CustomKeywords.'utilities.TestRunner.readStatBarCTDC'('CTDC/Statbar/Studies-Count',
	'CTDC/Statbar/Participants-Count', 'CTDC/Statbar/Diagnoses-Count','CTDC/Statbar/TargetedTherapies-Count','CTDC/Statbar/Biospecimens-Count','CTDC/Statbar/Files-Count')

//Participants tab
WebUI.waitForElementPresent(findTestObject('CTDC/ResultTabs/Participants-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/ResultTabs/Participants-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CTDC', GlobalVariable.G_StatBar_Participants, 'CTDC/ResultTabs/Participants-Tbl',
	'CTDC/ResultTabs/Participants-TblHdr', 'CTDC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameParticipants,
	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)
//
////biosppecimens tab
//WebUI.waitForElementPresent(findTestObject('CTDC/ResultTabs/Biospecimens-Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/ResultTabs/Biospecimens-Tab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CTDC', GlobalVariable.G_StatBar_Grants, 'CTDC/ResultTabs/Biospecimens-Tbl',
//	'CTDC/ResultTabs/Biospecimens-TblHdr', 'CTDC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameBiospecimens,
//	'TsvDataBiospecimens', GlobalVariable.G_QueryParticipantsTab)
//
////Files tab
//WebUI.waitForElementPresent(findTestObject('CTDC/ResultTabs/Files-Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CTDC/ResultTabs/Files-Tab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CTDC', GlobalVariable.G_StatBar_Files, 'CTDC/ResultTabs/Files-Tbl',
//	'CTDC/ResultTabs/Files-TblHdr', 'CTDC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameFiles,
//	'TsvDataFiles', GlobalVariable.G_QueryParticipantsTab)

WebUI.closeBrowser()


 



