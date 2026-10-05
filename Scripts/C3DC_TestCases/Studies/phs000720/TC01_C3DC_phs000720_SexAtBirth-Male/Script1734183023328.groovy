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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_C3DC_phs000720_SexAtBirth-Male.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/HomePage/WarningBan_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('C3DC/Navbar/Explore-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Navbar/Explore-Tab')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Facet'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/Study_Facet')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/phs000720-Chkbx'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/phs000720-Chkbx')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/Demographics_Facet'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Demographics/Demographics_Facet')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth-Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Demographics/SexAtBirth/SexAtBirth-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Demographics/SexAtBirth/Male-Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Demographics/SexAtBirth/Male-Chkbx')

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarC3DC'('C3DC/Statbar/Studies-Count', 'C3DC/Statbar/Participants-Count', 'C3DC/Statbar/Samples-Count', 'C3DC/Statbar/Files-Count')
























//This is a data problem, not an automation-query problem. The query is counting file nodes, and that count of 2,501 is correct.

//The stat bar counts files the way the backend index does: one file per dcf_indexd_guid. For Male participants in phs000720, 232 different Open FASTQ files were loaded with the same GUID
// dg.4DFC/a9c9e5ab-5e50-4a54-a972-1fc2bce0f054. The index collapses those 232 nodes into 1 file, so the page shows 2,270.
//Yes, this is a real issue, and the test is right to fail. Do not change the query to make it pass.

//The page shows 2,270 because the backend file index keeps one record per dcf_indexd_guid. In phs000720, 232 different Open FASTQ files were loaded with the same GUID, so the stat bar counts them as 1 file. The query counts the 2,501 file nodes, which is the real file count.

//Report that as a data defect: those 232 FASTQ files share 
//dg.4DFC/a9c9e5ab-5e50-4a54-a972-1fc2bce0f054. Leave TC01_C3DC_phs000720_SexAtBirth-Male failing until that GUID is corrected. After the reload, the stat bar should show 2,501 and this case should pass without a query change.
//dg.4DFC/a9c9e5ab-5e50-4a54-a972-1fc2bce0f054













//Participants tab
CustomKeywords.'utilities.TestRunner.selectTab'('Participants', 10)
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Participants-Tbl',
	'C3DC/ResultTabs/Participants-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameParticipants,
	'DbDataParticipants', GlobalVariable.G_QueryParticipantsTab)

//clicking the Studies tab
CustomKeywords.'utilities.TestRunner.selectTab'('Studies', 10)
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Studies, 'C3DC/ResultTabs/Studies-Tbl',
	'C3DC/ResultTabs/Studies-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameStudies,
	'DbDataStudies', GlobalVariable.G_QueryStudiesTab)

//clicking the Diagnosis tab
CustomKeywords.'utilities.TestRunner.selectTab'('Diagnosis', 10)
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Diagnosis-Tbl',
	'C3DC/ResultTabs/Diagnosis-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameDiagnosis,
	'DbDataDiagnosis', GlobalVariable.G_QueryDiagnosisTab)

////clicking the Genetic Analysis tab. do not have data
//CustomKeywords.'utilities.TestRunner.selectTab'('Genetic Analyses', 10)
//CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/GeneticAnalysis-Tbl',
//	'C3DC/ResultTabs/GeneticAnalysis-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameGeneticAnalysis,
//	'DbDataGeneticAnalysis', GlobalVariable.G_QueryGeneticAnalysisTab)

////clicking the Treatment tab - do not have data 
//CustomKeywords.'utilities.TestRunner.selectTab'('Treatments', 10)
//CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Treatment-Tbl',
//	'C3DC/ResultTabs/Treatment-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameTreatment,
//	'DbDataTreatment', GlobalVariable.G_QueryTreatmentTab)

////clicking the Treatment Response tab - do not have data
//CustomKeywords.'utilities.TestRunner.selectTab'('Treatment Responses', 10)
//CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/TreatmentResp-Tbl',
//	'C3DC/ResultTabs/TreatmentResp-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameTrtmntResp,
//	'DbDataTreatmntResp', GlobalVariable.G_QueryTrtmntRespTab)

//clicking the Survival tab
CustomKeywords.'utilities.TestRunner.selectTab'('Survival', 10)
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Survival-Tbl',
	'C3DC/ResultTabs/Survival-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameSurvival,
	'DbDataSurvival', GlobalVariable.G_QuerySurvivalTab)

//clicking the Samples tab
CustomKeywords.'utilities.TestRunner.selectTab'('Samples', 10)
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Samples-Tbl',
	'C3DC/ResultTabs/Samples-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameSamples,
	'DbDataSamples', GlobalVariable.G_QuerySamplesTab)

WebUI.closeBrowser()
