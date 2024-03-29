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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

//Study IN (COTC022), Breed IN (AnatolianShepherdDog, SaintBernard), DIAGNOSIS IN (Osteosarcoma), Primary Disease Site IN (Bone (Appendicular)
WebUI.closeBrowser()

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC11_Canine_StudyCOTC022-RespToTrtmt_Sex_NeutStatus.xlsx')

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')
System.out.println ("Closed the popup window");
//s.clinical_study_designation IN ['COTC022'] and  diag.best_response in ['Not Determined']
//Sex-Male, Neut status - Yes 

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 20)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Canine/NavBar/Canine_Cases_Btn')
//WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/FilterByCases_Facet'), 20)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/FilterByCases_Facet')

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Study/Canine_Filter_Study'), 30)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Study/Canine_Filter_Study')
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Study/Canine_Filter_Study-COTC022_Chkbx'), 30)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Study/Canine_Filter_Study-COTC022_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/Study/Canine_Filter_Study')
 
 
//resp to treatment
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn'), 30)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn')
WebUI.waitForElementPresent(findTestObject('Canine/Filter/ResponseToTreatment/NotDetermined_Chkbx'), 30)
WebUI.verifyElementPresent(findTestObject('Canine/Filter/ResponseToTreatment/NotDetermined_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/ResponseToTreatment/NotDetermined_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/ResponseToTreatment/RESPONSETOTREATMENT_Ddn')
 
//sex
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Sex/SEX_Ddn'), 30)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/Filter/Sex/SEX_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Sex/SEX_Ddn')
WebUI.waitForElementPresent(findTestObject('Canine/Filter/Sex/Male_Chkbx'), 30)
WebUI.verifyElementPresent(findTestObject('Canine/Filter/Sex/Male_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/Sex/Male_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/Sex/SEX_Ddn')
 
//neut status
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/NeuteredStatus/NEUTEREDSTATUS_Ddn'), 30)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/Filter/NeuteredStatus/NEUTEREDSTATUS_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/NeuteredStatus/NEUTEREDSTATUS_Ddn')
WebUI.waitForElementPresent(findTestObject('Canine/Filter/NeuteredStatus/Yes_Chkbx'), 30)
WebUI.verifyElementPresent(findTestObject('Canine/Filter/NeuteredStatus/Yes_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/NeuteredStatus/Yes_Chkbx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/NeuteredStatus/NEUTEREDSTATUS_Ddn')


CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCanine'('Object Repository/Canine/StatBar/Canine_StatBar-Studies', 
    'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples', 
    'Object Repository/Canine/StatBar/Canine_StatBar-CaseFiles', 'Object Repository/Canine/StatBar/Canine_StatBar-StudyFiles')
 

////clicking the Cases tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Cases_Tab'), 30)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/CanineResults_Cases_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Cases_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Publications, 'Object Repository/Canine/Canine_CasesTable',
	'Object Repository/Canine/Canine_TableHeader', 'Object Repository/Canine/Canine_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
	GlobalVariable.G_CypherTabnameCases, GlobalVariable.G_QueryCasesTab)

//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Samples_Tab'), 30)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/CanineResults_Samples_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Samples_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Datasets, 'Object Repository/Canine/Canine_Samples_Table',
	'Object Repository/Canine/Canine_Samples_TableHdr', 'Object Repository/Canine/Canine_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)
 
////clicking the Case Files tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_CaseFiles_Tab'), 30)
WebUI.verifyElementPresent(findTestObject('Canine/CanineResults_CaseFiles_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_CaseFiles_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_ClinTrials, 'Object Repository/Canine/Canine_Files_Table',
	'Object Repository/Canine/Canine_Files_TableHdr', 'Object Repository/Canine/Canine_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)

////clicking the Study Files tab
//WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_StudyFiles_Tab'), 30)
//WebUI.verifyElementPresent(findTestObject('Canine/CanineResults_StudyFiles_Tab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_StudyFiles_Tab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Patents, 'Object Repository/Canine/Canine_StudyFiles_Table',
//	'Object Repository/Canine/Canine_StudyFiles_TableHdr', 'Object Repository/Canine/Canine_StudyFilesTabNextBtn', GlobalVariable.G_WebTabnameStudyFiles,
//	GlobalVariable.G_CypherTabnameStudyFiles, GlobalVariable.G_QueryStudyFilesTab)
 



