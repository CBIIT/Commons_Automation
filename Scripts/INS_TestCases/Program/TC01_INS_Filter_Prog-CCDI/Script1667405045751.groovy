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

//CustomKeywords.'utilities.TestRunner.testSetup'('')

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_INS_Filter_Prog-CCDI.xlsx')

//CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')
//System.out.println ("Closed the popup window");

WebUI.waitForElementPresent(findTestObject('Object Repository/INS/Navbar/INS_Explore_Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/INS/Navbar/INS_Explore_Btn')
 
 

//WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INS_FilterByProj_Facet'), 15)
//CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/INS/ProjectsPage/INS_FilterByProj_Facet')
 
 
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/Filter/Program/Program_Ddn'), 15)
CustomKeywords.'utilities.TestRunner.clickTabINSStat'('Object Repository/INS/ProjectsPage/Filter/Program/Program_Ddn')
CustomKeywords.'utilities.TestRunner.clickTabINSStat'('Object Repository/INS/ProjectsPage/Filter/Program/CCDI_Chkbx')


Thread.sleep (5000)

CustomKeywords.'utilities.TestRunner.readINSStatBar'('Object Repository/INS/Statbar/INS_Statbar-Programs','Object Repository/INS/Statbar/INS_Statbar-Projects',
	'Object Repository/INS/Statbar/INS_Statbar-Publications', 'Object Repository/INS/Statbar/INS_Statbar-Datasets',
	'Object Repository/INS/Statbar/INS_Statbar-ClinTrials', 'Object Repository/INS/Statbar/INS_Statbar-Patents')
 


// clicking the Projects tab
WebUI.waitForElementPresent(findTestObject('Object Repository/INS/ProjectsPage/INSResults_Projects_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/INS/ProjectsPage/INSResults_Projects_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('INS', GlobalVariable.G_StatBar_Projects, 'Object Repository/INS/ProjectsPage/INS_Projects_Tbl',
   'Object Repository/INS/ProjectsPage/INS_Projects_TblHdr', 'Object Repository/INS/ProjectsPage/INS_TabNextBtn', GlobalVariable.G_WebTabnameProjects,
	GlobalVariable.G_CypherTabnameProjects, GlobalVariable.G_QueryProjectsTab)


WebUI.closeBrowser()