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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC04_C3DC_phs002430_AnatoSite-C119NasopharynxNOS.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/HomePage/WarningBan_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('C3DC/Navbar/Explore-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Navbar/Explore-Tab')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/Study_Facet'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/Study_Facet')

WebUI.delay(2)
WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn')

CustomKeywords.'utilities.TestRunner.scrollFacetDownUntilVisible'('C3DC/Filters/Study/dbGaP_Accession/dbGaP_Accession-Ddn',
	'C3DC/Filters/Study/dbGaP_Accession/phs002430-Chkbx', 10)
WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Study/dbGaP_Accession/phs002430-Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Study/dbGaP_Accession/phs002430-Chkbx')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Diagnosis/Diagnosis-Facet'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Diagnosis/Diagnosis-Facet')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Diagnosis/AnatomicSite/AnatomicSite-Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Diagnosis/AnatomicSite/AnatomicSite-Ddn')

WebUI.waitForElementPresent(findTestObject('C3DC/Filters/Diagnosis/AnatomicSite/C11.9.Nasopharynx,NOS-Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/Filters/Diagnosis/AnatomicSite/C11.9.Nasopharynx,NOS-Chkbx')

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarC3DC'('C3DC/Statbar/Diagnosis-Count', 'C3DC/Statbar/Participants-Count', 
    'C3DC/Statbar/Studies-Count')

//clicking the Studies tab
WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/Studies-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/Studies-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Studies, 'C3DC/ResultTabs/Studies-Tbl',
	'C3DC/ResultTabs/Studies-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameStudies,
	'TsvDataStudies', GlobalVariable.G_QueryStudiesTab)

//Participants tab
WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/Participants-Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/Participants-Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Participants-Tbl',
	'C3DC/ResultTabs/Participants-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameParticipants,	
	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)

//clicking the Diagnosis tab
CustomKeywords.'utilities.TestRunner.openTab'('C3DC/ResultTabs/Diagnosis-Tab', 'Diagnosis')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Diagnosis-Tbl',
	'C3DC/ResultTabs/Diagnosis-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameDiagnosis,
	'TsvDataDiagnosis', GlobalVariable.G_QueryDiagnosisTab)

////clicking the Genetic Analysis tab
//CustomKeywords.'utilities.TestRunner.openTab'('C3DC/ResultTabs/GeneticAnalysis-Tab','Genetic Analysis')
//CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/SortByGeneticAnalysisID-Colm')
//CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/GeneticAnalysis-Tbl',
//	'C3DC/ResultTabs/GeneticAnalysis-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameGeneticAnalysis,
//	'TsvDataGeneticAnalysis', GlobalVariable.G_QueryGeneticAnalysisTab)

////clicking the Treatment tab - This study does not have treatment data
//WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/Treatment-Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/Treatment-Tab')
CustomKeywords.'utilities.TestRunner.openTab'('C3DC/ResultTabs/Treatment-Tab', 'Treatment')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/SortByTreatmentID-Colm')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Treatment-Tbl',
	'C3DC/ResultTabs/Treatment-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameTreatment,
	'TsvDataTreatment', GlobalVariable.G_QueryTreatmentTab)

//////clicking the Treatment Response tab -This study does not have treatment response data
//////WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/TreatmentResp-Tab'), 5)
//////CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/TreatmentResp-Tab')
//CustomKeywords.'utilities.TestRunner.openTab'('C3DC/ResultTabs/TreatmentResp-Tab', 'Treatment Response')
//CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/SortBySurvivalID-Colm')
//CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/TreatmentResp-Tbl',
//	'C3DC/ResultTabs/TreatmentResp-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameTrtmntResp,
//	'TsvDataTreatmntResp', GlobalVariable.G_QueryTrtmntRespTab)

//clicking the Survival tab
//WebUI.waitForElementPresent(findTestObject('C3DC/ResultTabs/Survival-Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/Survival-Tab')
CustomKeywords.'utilities.TestRunner.openTab'('C3DC/ResultTabs/Survival-Tab', 'Survival')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/ResultTabs/SortBySurvivalID-Colm')
CustomKeywords.'utilities.TestRunner.multiFunction'('C3DC', GlobalVariable.G_StatBar_Participants, 'C3DC/ResultTabs/Survival-Tbl',
	'C3DC/ResultTabs/Survival-TblHdr', 'C3DC/ResultTabs/All_Tabs_Next-Btn', GlobalVariable.G_WebTabnameSurvival,
	'TsvDataSurvival', GlobalVariable.G_QuerySurvivalTab)
 
WebUI.closeBrowser()

