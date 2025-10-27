import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

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
WebUI.closeBrowser()

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_ICDC_COTC021_StudyType-ClinicalTrial.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_PopUp_Continue_Btn')
System.out.println ("Closed the popup window");

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Cases_Btn')


WebUI.waitForElementPresent(findTestObject('Canine/Filter/Study/Canine_Filter_Study'), 5)
CustomKeywords.'utilities.TestRunner.clickTabCanineStat'('Canine/Filter/Study/Canine_Filter_Study')

CustomKeywords.'utilities.TestRunner.clickTabCanineStat'('Canine/Filter/Study/Canine_Filter_Study-COTC021_Chkbx')

WebUI.waitForElementPresent(findTestObject('Canine/Filter/StudyType/Canine_Filter_StudyType'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Filter/StudyType/Canine_Filter_StudyType')

WebUI.waitForElementPresent(findTestObject('Canine/Filter/StudyType/Canine_Filter_StudyType-ClinTrials_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Filter/StudyType/Canine_Filter_StudyType-ClinTrials_Chkbx')

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarCanine'('Canine/StatBar/Canine_StatBar-Programs','Canine/StatBar/Canine_StatBar-Studies',
	'Canine/StatBar/Canine_StatBar-Cases', 'Canine/StatBar/Canine_StatBar-Samples',
	'Canine/StatBar/Canine_StatBar-CaseFiles', 'Canine/StatBar/Canine_StatBar-StudyFiles')

//clicking the Cases tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_Cases_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_Cases_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('ICDC', GlobalVariable.G_StatBar_Cases, 'Canine/Canine_CasesTable',
	'Object Repository/Canine/Canine_TableHeader', 'Canine/Canine_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
	'TsvDataCases', GlobalVariable.G_QueryCasesTab)
	

//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_Samples_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_Samples_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('ICDC', GlobalVariable.G_StatBar_Samples, 'Canine/Canine_Samples_Table',
	'Canine/Canine_Samples_TableHdr', 'Canine/Canine_SamplesTabNextBtn', GlobalVariable.G_WebTabnameBiospecimens,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)

	
//clicking the case Files tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_CaseFiles_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_CaseFiles_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('ICDC', GlobalVariable.G_StatBar_Files, 'Canine/Canine_Files_Table',
	'Canine/Canine_Files_TableHdr', 'Canine/Canine_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	'TsvDataCaseFiles', GlobalVariable.G_QueryFilesTab)


//clicking the study Files tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_StudyFiles_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_StudyFiles_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('ICDC', GlobalVariable.G_StatBar_StudyFiles, 'Canine/Canine_StudyFiles_Table',
	'Canine/Canine_StudyFiles_TableHdr', 'Canine/Canine_StudyFilesTabNextBtn', GlobalVariable.G_WebTabnameStudyFiles,
	'TsvDataStudyFiles', GlobalVariable.G_QueryStudyFilesTab)
 
WebUI.closeBrowser()


 
