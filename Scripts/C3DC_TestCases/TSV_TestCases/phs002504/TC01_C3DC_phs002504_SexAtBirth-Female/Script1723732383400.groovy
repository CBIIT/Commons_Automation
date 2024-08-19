import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_C3DC_phs002504_SexAtBirth-Female.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/HomePage/WarningBan_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('C3DC/Navbar/Explore-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Navbar/Explore-Tab')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Facet'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/Study_Facet')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/phs002504-Chkbx'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/phs002504-Chkbx')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth_Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/Female_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Demographics/SexAtBirth/Female_Chkbx')

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarC3DC'('C3DC/Statbar/Diagnosis-Count', 'C3DC/Statbar/Participants-Count', 
    'C3DC/Statbar/Studies-Count')

//Participants tab
WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/Participants-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/Participants-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Participants-Tbl',
	'C3DC/ResultTabs/Participants-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameParticipants,	
	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)


////clicking the Diagnosis tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Diagnosis_ResultsTab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Diagnosis_ResultsTab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'CCDI/ExplorePage/CCDI_Diagnosis_Tbl',
//	'CCDI/ExplorePage/CCDI_Diagnosis_TblHdr', 'CCDI/ExplorePage/CCDI_Diagnosis_TblNextBtn', GlobalVariable.G_WebTabnameDiagnosis,
//	'TsvDataDiagnosis', GlobalVariable.G_QueryDiagnosisTab)


////clicking the Studies tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Studies_ResultsTab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Studies_ResultsTab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'CCDI/ExplorePage/CCDI_Studies_Tbl',
//	'CCDI/ExplorePage/CCDI_Studies_TblHdr', 'CCDI/ExplorePage/CCDI_Studies_TblNextBtn', GlobalVariable.G_WebTabnameStudies,	
//	'TsvDataStudies', GlobalVariable.G_QueryStudiesTab)


//clicking the Samples tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Samples_ResultsTab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Samples_ResultsTab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Samples, 'CCDI/ExplorePage/CCDI_Samples_Tbl',
//	'CCDI/ExplorePage/CCDI_Samples_TblHdr', 'CCDI/ExplorePage/CCDI_Samples_TblNextBtn', GlobalVariable.G_WebTabnameSamples,	
//	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)
 

////clicking the Files tab
//WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Files_ResultsTab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Files_ResultsTab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Files, 'CCDI/ExplorePage/CCDI_Files_Tbl',
//'CCDI/ExplorePage/CCDI_Files_TblHdr', 'CCDI/ExplorePage/CCDI_Files_TblNextBtn', GlobalVariable.G_WebTabnameFiles,
//'TsvDataFiles', GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()

