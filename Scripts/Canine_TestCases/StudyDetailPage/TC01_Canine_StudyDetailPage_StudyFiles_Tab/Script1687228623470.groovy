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

//Step 1--------------------Opening the desired url ****************************************************************
/*
 * WebUI.openBrowser(GlobalVariable.G_Urlname)
 

WebUI.maximizeWindow()
//CustomKeywords.'utilities.DataValidation.initDriver'()  use this when using datavalidation profile
//CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_Canine_Filter_Breed-Akita.xlsx')
Thread.sleep(2000)

*/

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_Canine_StudyDetailPage_StudyFiles_Tab.xlsx')

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'), 5)

WebUI.click(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'))

System.out.println('Closed the popup window')

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Studies_Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Studies_Btn')

System.out.println('This is the url of the current page : ' + WebUI.getUrl())


//reading the table inside the study files tab of a particular study details page :

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/StudiesPage/NCATS_Hplink'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Canine/StudiesPage/NCATS_Hplink')
System.out.println('This is the url of the current page after clicking a particular study link : ' + WebUI.getUrl())
Thread.sleep(2000)

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarCanine'('Object Repository/Canine/StatBar/Canine_StatBar-Programs','Object Repository/Canine/StatBar/Canine_StatBar-Studies',
	'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples',
	'Object Repository/Canine/StatBar/Canine_StatBar-CaseFiles', 'Object Repository/Canine/StatBar/Canine_StatBar-StudyFiles')
 
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/StudyDetailsPage/StudyFilesTab'), 5) 

boolean studyfilestab = WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/StudyDetailsPage/StudyFilesTab'),
				5, FailureHandling.OPTIONAL)
			System.out.println('This is the value of element presence for studyfiles tab : ' + studyfilestab)
			//System.out.println('This is the value of the label in the button : ' + (findTestObject('Object Repository/Canine/StudyDetailsPage/StudyFilesTab')).getText())
			
			if (studyfilestab == true) {
			 
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Canine/StudyDetailsPage/StudyFilesTab')
System.out.println('This is the url of the current page after clicking a particular study link : ' + WebUI.getUrl())
 Thread.sleep(2000)
CustomKeywords.'utilities.TestRunner.multiFunction'('ICDC', GlobalVariable.G_StatBar_StudyFiles, 'Object Repository/Canine/StudyDetailsPage/StudyFilesTab_Tbl',
	'Object Repository/Canine/StudyDetailsPage/StudyFilesTab_TblHdr', 'Object Repository/Canine/StudyDetailsPage/StudyFilesTab_NextBtn', GlobalVariable.G_WebTabnameSDStudyFilesTab,
	GlobalVariable.G_CypherTabnameSDStudyFilesTab, GlobalVariable.G_QueryStudyFilesTab)



			}
WebUI.closeBrowser()
