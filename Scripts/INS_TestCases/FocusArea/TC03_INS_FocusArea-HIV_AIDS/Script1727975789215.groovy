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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC03_INS_FocusArea-HIV_AIDS.xlsx')

WebUI.waitForElementPresent(findTestObject('INS/Navbar/Explore-Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('INS/Navbar/Explore-Btn')

WebUI.waitForElementPresent(findTestObject('INS/Filters/FocusArea/HIVAIDS-Chkbx'), 15)
CustomKeywords.'utilities.TestRunner.clickTabINSStat'('INS/Filters/FocusArea/HIVAIDS-Chkbx')
 
//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarINS'('INS/Statbar/Statbar-Programs', 'INS/Statbar/Statbar-Projects', 
	'INS/Statbar/Statbar-Grants', 'INS/Statbar/Statbar-Publications')

//Clicking Programs tab
WebUI.waitForElementPresent(findTestObject('INS/ResultTabs/Programs-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('INS/ResultTabs/Programs-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('INS', GlobalVariable.G_StatBar_Programs, 'INS/ResultTabs/Programs-Tbl', 
    'INS/ResultTabs/Programs-TblHdr', 'INS/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnamePrograms, 'TsvDataPrograms', 
    GlobalVariable.G_QueryProgramsTab)

//Clicking Projects tab
WebUI.waitForElementPresent(findTestObject('INS/ResultTabs/Projects-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('INS/ResultTabs/Projects-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('INS', GlobalVariable.G_StatBar_Projects, 'INS/ResultTabs/Projects-Tbl',
	'INS/ResultTabs/Projects-TblHdr', 'INS/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameProjects, 'TsvDataProjects',
	GlobalVariable.G_QueryProjectsTab)

//Clicking Grants tab
WebUI.waitForElementPresent(findTestObject('INS/ResultTabs/Grants-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('INS/ResultTabs/Grants-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('INS', GlobalVariable.G_StatBar_Grants, 'INS/ResultTabs/Grants-Tbl',
	'INS/ResultTabs/Grants-TblHdr', 'INS/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameGrants, 'TsvDataGrants',
	GlobalVariable.G_QueryProjectsTab)

//Clicking Publication tab
WebUI.waitForElementPresent(findTestObject('INS/ResultTabs/Publications-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('INS/ResultTabs/Publications-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('INS', GlobalVariable.G_StatBar_Publications, 'INS/ResultTabs/Publications-Tbl',
	'INS/ResultTabs/Publications-TblHdr', 'INS/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnamePublications, 'TsvDataPublications',
	GlobalVariable.G_QueryProjectsTab)

WebUI.closeBrowser()

