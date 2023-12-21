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

 
WebUI.closeBrowser()

//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.testSetup'('')

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC01_INS_Filter_Doc-CCG.xlsx')

//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')
//System.out.println ("Closed the popup window");

WebUI.waitForElementPresent(findTestObject('Object Repository/INS/Navbar/INS_Explore_Btn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/Navbar/INS_Explore_Btn')
 
 

//WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INS_FilterByProj_Facet'), 15)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INS_FilterByProj_Facet')
 
 
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/Filter/DOC/DOC_Ddn'), 15)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabINSStat'('Object Repository/INS/ProjectsPage/Filter/DOC/DOC_Ddn')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabINSStat'('Object Repository/INS/ProjectsPage/Filter/DOC/CCG_Chkbx')

Thread.sleep (5000)

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readINSStatBar'('Object Repository/INS/Statbar/INS_Statbar-Programs','Object Repository/INS/Statbar/INS_Statbar-Projects',
	'Object Repository/INS/Statbar/INS_Statbar-Grants','Object Repository/INS/Statbar/INS_Statbar-Publications', 'Object Repository/INS/Statbar/INS_Statbar-Datasets',
	'Object Repository/INS/Statbar/INS_Statbar-ClinTrials', 'Object Repository/INS/Statbar/INS_Statbar-Patents')
 
// clicking the Grants tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_Grants_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INSResults_Grants_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('INS', GlobalVariable.G_StatBar_Grants, 'Object Repository/INS/ProjectsPage/INS_Grants_Tbl', 
   'Object Repository/INS/ProjectsPage/INS_Grants_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnameGrants, 
    GlobalVariable.G_CypherTabnameGrants, GlobalVariable.G_QueryGrantsTab)

/*
// clicking the Publications tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_Publications_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INSResults_Publications_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('INS', GlobalVariable.G_StatBar_Publications, 'Object Repository/INS/ProjectsPage/INS_Publications_Tbl',
   'Object Repository/INS/ProjectsPage/INS_Publications_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnamePublications,
	GlobalVariable.G_CypherTabnamePublications, GlobalVariable.G_QueryPublicationsTab)
	*/


// clicking the Datasets tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_Datasets_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INSResults_Datasets_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('INS', GlobalVariable.G_StatBar_Datasets, 'Object Repository/INS/ProjectsPage/INS_Datasets_Tbl',
   'Object Repository/INS/ProjectsPage/INS_Datasets_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnameDatasets,
	GlobalVariable.G_CypherTabnameDatasets, GlobalVariable.G_QueryDatasetsTab)

// clicking the Clinical Trials tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_ClinTrials_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INSResults_ClinTrials_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('INS', GlobalVariable.G_StatBar_ClinTrials, 'Object Repository/INS/ProjectsPage/INS_ClinTrials_Tbl',
   'Object Repository/INS/ProjectsPage/INS_ClinTrials_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnameClinTrials,
	GlobalVariable.G_CypherTabnameClinTrials, GlobalVariable.G_QueryClinTrialsTab)

// clicking the Patents tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_Patents_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/INS/ProjectsPage/INSResults_Patents_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('INS', GlobalVariable.G_StatBar_Patents, 'Object Repository/INS/ProjectsPage/INS_Patents_Tbl',
   'Object Repository/INS/ProjectsPage/INS_Patents_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnamePatents,
	GlobalVariable.G_CypherTabnamePatents, GlobalVariable.G_QueryPatentsTab)

WebUI.closeBrowser()


