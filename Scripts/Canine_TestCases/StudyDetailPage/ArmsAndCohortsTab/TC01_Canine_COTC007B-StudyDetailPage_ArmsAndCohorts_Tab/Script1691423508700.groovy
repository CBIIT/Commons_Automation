import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
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
//CustomKeywords.'ctdc.utilities.DataValidation.initDriver'()  use this when using datavalidation profile

*/

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC01_Canine_COTC007B-StudyDetailPage_ArmsAndCohorts_Tab.xlsx')

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'), 5)

WebUI.click(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'))

System.out.println('Closed the popup window')

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Studies_Btn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Canine/NavBar/Canine_Studies_Btn')

System.out.println('This is the url of the current page : ' + WebUI.getUrl())

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/StudiesPage/COTC007B_Hplink'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/StudiesPage/COTC007B_Hplink')
System.out.println('This is the url of the current page after clicking a particular study link : ' + WebUI.getUrl())
Thread.sleep(2000)

//reading the table inside the study ArmsCohortsTab tab of a particular study details page :

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/StudyDetailsPage/ArmsCohortsTab'), 5) 
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/StudyDetailsPage/ArmsCohortsTab')


//Read Statbar
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCanine'('Object Repository/Canine/StatBar/Canine_StatBar-Programs','Object Repository/Canine/StatBar/Canine_StatBar-Studies',
	'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples',
	'Object Repository/Canine/StatBar/Canine_StatBar-CaseFiles', 'Object Repository/Canine/StatBar/Canine_StatBar-StudyFiles')

//Read table
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Cases  , 'Object Repository/Canine/StudyDetailsPage/ArmsCohortsTab_Tbl',
	'Object Repository/Canine/StudyDetailsPage/ArmsCohortsTab_TblHdr', 'Object Repository/Canine/StudyDetailsPage/ArmsCohortsTab_NextBtn', GlobalVariable.G_WebTabnameArmsCohorts ,
	GlobalVariable.G_CypherTabnameArmsCohortsTab, GlobalVariable.G_QueryArmsCohortsTab )


@Keyword
public static void multiFunction2(String appName, String tbl, String tblHdr, String nxtBtn, String webdataSheetName, String dbdataSheetName, String tabQuery) throws IOException {
	System.out.println("This is the value of stat (string) obtained from multifunction: " + statVal);
	int statValue = convStringtoInt(statVal);
	System.out.println("This is the value of stat (integer) obtained from multifunction: " + statValue);

	if (statValue !=0) {
		ReadCasesTableKatalon(statVal, tbl,tblHdr,nxtBtn,webdataSheetName)
		System.out.println("control is after read table webdataxl creation and before readexcel neo4j function")
		ReadExcel.Neo4j(dbdataSheetName,tabQuery)
		System.out.println("control is before compare lists function from multifunction")
		compareLists(webdataSheetName, dbdataSheetName)
		System.out.println("control is before validate stat bar function from multifunction")
		validateStatBar(appName)
	}else {
		System.out.println("Skipping data collection from neo4j and compare lists of web and db as the stat value is 0")
	}
}
			
WebUI.closeBrowser()


