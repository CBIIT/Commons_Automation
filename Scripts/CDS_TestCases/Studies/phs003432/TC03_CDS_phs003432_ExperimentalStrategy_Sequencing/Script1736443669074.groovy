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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC03_CDS_phs003432_ExperimentalStrategy_Sequencing.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Clicking data tab
WebUI.waitForElementPresent(findTestObject('CDS/NavBar/CDS_Data-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/NavBar/CDS_Data-Btn')

//Clicking Phs Accession dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn')

//Clicking checkbox phs002431
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs003155_Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs003155_Chkbx')

//Clicking Experimental Strategy dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/ExperimentalStrategy/ExperimentalStrategy_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/ExperimentalStrategy/ExperimentalStrategy_Ddn')

//Clicking WXS checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/StudyFacet/ExperimentalStrategy/WGS-Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/StudyFacet/ExperimentalStrategy/WGS-Chkbx')

//Read statbar
CustomKeywords.'utilities.TestRunner.readStatBarCDS'('Object Repository/CDS/StatBar/CDS_StatBar-Studies',
 'Object Repository/CDS/StatBar/CDS_StatBar-Participants','Object Repository/CDS/StatBar/CDS_StatBar-Samples', 'Object Repository/CDS/StatBar/CDS_StatBar-Files')


//Clicking participants tab
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/CDSResults_Participants_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/CDS/Data_page/CDSResults_Participants_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Participants, 'Object Repository/CDS/Data_page/CDS_ParticipantsTable',
	'Object Repository/CDS/Data_page/CDS_ParticipantsTableHeader', 'Object Repository/CDS/Data_page/CDS_ParticipantsTabNextBtn', GlobalVariable.G_WebTabnameParticipants,	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)

//clicking Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/CDSResults_Samples_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/CDS/Data_page/CDSResults_Samples_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Samples, 'Object Repository/CDS/Data_page/CDS_SamplesTable',
	'Object Repository/CDS/Data_page/CDS_SamplesTableHeader', 'Object Repository/CDS/Data_page/CDS_SamplesTabNextBtn', GlobalVariable.G_WebTabnameBiospecimens,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)

//clicking Files tab
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/CDSResults_Files_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/CDS/Data_page/CDSResults_Files_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Files, 'Object Repository/CDS/Data_page/CDS_FilesTable',
	'Object Repository/CDS/Data_page/CDS_FilesTableHeader', 'Object Repository/CDS/Data_page/CDS_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	'TsvDataFiles', GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()