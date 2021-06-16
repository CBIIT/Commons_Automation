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

'Opens the browser of choice: Chrome, Firefox or Edge'
WebUI.closeBrowser()

WebUI.openBrowser('')

WebUI.maximizeWindow()

'Driver opened by Katalon is used in Selenium from this step.'
//UBC-- Belgian,Labrador,WestHIghland  -- bladderCanc--  bladderprostate/bladderUrethra/bladderUrethraProstate/urethraProstate--notdeter/partialResponse/progressiveDisease/stableDisease

//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.testSetup'('')
'This step takes the Query from input excel and fetches data from Neo4j database. \r\nSaves the results from neo4j and application in the same name mentioned in the input excel. '
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC11_Canine_StudyUBC-Breed_Diagnosis_PrimDiseaseSite_RespToTreatmt.xlsx')

'Clicks on the Cases button in the Navbar of ICDC\'s homepage.'
WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Canine/NavBar/Canine_Cases_Btn')

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/FilterByCases_Facet'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/FilterByCases_Facet')

'Clicks on the Filter \'Study\' from left pane'
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Study/Canine_Filter_Study'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Study/Canine_Filter_Study')
'Selects the specific check box from \'Study\' filter.'
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Study/Canine_Filter_Study-UBC_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Study/Canine_Filter_Study-UBC_Chkbx')
Thread.sleep(2000)
 
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Breed/BREED_Ddn'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Breed/BREED_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Breed/BelgMalin_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Breed/BelgMalin_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Breed/Labrador_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Breed/Labrador_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Breed/WestHland_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Breed/WestHland_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Breed/BREED_Ddn')
 

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Diagnosis/BladderCancer_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Diagnosis/BladderCancer_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn')
 

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/PrimDiseaseSite/PRIMARYDISEASESITE_Ddn'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/PRIMARYDISEASESITE_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/PrimDiseaseSite/BladderProstate_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/BladderProstate_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/PrimDiseaseSite/BladderUrethra_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/BladderUrethra_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/PrimDiseaseSite/BladderUrethraProstate_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/BladderUrethraProstate_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/PrimDiseaseSite/UrethraProstate_Chkbx'),15)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/UrethraProstate_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/PrimDiseaseSite/PRIMARYDISEASESITE_Ddn')
 
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/NotDetermined_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/NotDetermined_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/PartialResponse_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/PartialResponse_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/ProgrDisease_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/ProgrDisease_Chkbx')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/StableDisease_Chkbx'),10)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/StableDisease_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn')
 


CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCanine'('Object Repository/Canine/StatBar/Canine_StatBar-Studies',
	'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples',
	'Object Repository/Canine/StatBar/Canine_StatBar-Files', 'Object Repository/Canine/StatBar/Canine_StatBar-Aliquots')

////clicking the Cases tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Cases_Tab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Cases_Tab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Cases, 'Object Repository/Canine/Canine_CasesTable',
//	'Object Repository/Canine/Canine_TableHeader', 'Object Repository/Canine/Canine_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
//	GlobalVariable.G_CypherTabnameCases, GlobalVariable.G_QueryCasesTab)

//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Samples_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Samples_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Samples, 'Object Repository/Canine/Canine_Samples_Table',
	'Object Repository/Canine/Canine_Samples_TableHdr', 'Object Repository/Canine/Canine_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)

////clicking the Files tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Files_Tab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Files_Tab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Files, 'Object Repository/Canine/Canine_Files_Table',
//	'Object Repository/Canine/Canine_Files_TableHdr', 'Object Repository/Canine/Canine_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
//	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)

 

WebUI.closeBrowser()
