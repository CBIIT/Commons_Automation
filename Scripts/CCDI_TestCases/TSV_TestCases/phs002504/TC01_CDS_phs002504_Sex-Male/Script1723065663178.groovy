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

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC01_CDS_phs002504_Sex-Male.xlsx')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/Navbar/Explore_Menu'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/Navbar/Explore_Menu')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/Study_Facet'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/Study_Facet')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn')

WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002504_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002504_Chkbx')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/Demographics-Facet/Sex/Sex_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Filters/Demographics-Facet/Sex/Sex_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/Demographics-Facet/Sex/Male_Chkbx'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('CCDI/ExplorePage/Filters/Demographics-Facet/Sex/Male_Chkbx')

//Read Statbar
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCCDIhub'('CCDI/Statbar/Studies_Cnt', 'CCDI/Statbar/Participants_Cnt', 
    'CCDI/Statbar/Samples_Cnt', 'CCDI/Statbar/Files_Cnt')


//clicking the Participants tab   - for some reason the xpath does not get identified for the participants results tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Participants_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Participants_ResultsTab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'Object Repository/CCDI/ExplorePage/CCDI_Participants_Tbl',
	'Object Repository/CCDI/ExplorePage/CCDI_Participants_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Participants_TblNextBtn', GlobalVariable.G_WebTabnameParticipants,	
	'TsvDataParticipant', GlobalVariable.G_QueryParticipantsTab)


////clicking the Diagnosis tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Diagnosis_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Diagnosis_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'Object Repository/CCDI/ExplorePage/CCDI_Diagnosis_Tbl',
//	'Object Repository/CCDI/ExplorePage/CCDI_Diagnosis_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Diagnosis_TblNextBtn', GlobalVariable.G_WebTabnameDiagnosis,	GlobalVariable.G_CypherTabnameDiagnosis, GlobalVariable.G_QueryDiagnosisTab)
////when the stat for diagnosis gets added in the stat bar, then the above line should be updated for the global variable from participants to diagnosis.
// 
//
////clicking the Studies tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Studies_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Studies_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'Object Repository/CCDI/ExplorePage/CCDI_Studies_Tbl',
//	'Object Repository/CCDI/ExplorePage/CCDI_Studies_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Studies_TblNextBtn', GlobalVariable.G_WebTabnameStudies,	GlobalVariable.G_CypherTabnameStudies, GlobalVariable.G_QueryStudiesTab)
////when the stat for diagnosis gets added in the stat bar, then the above line should be updated for the global variable from participants to diagnosis.
//
//
////clicking the Samples tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Samples_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Samples_ResultsTab')
//Thread.sleep(2000)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Samples, 'Object Repository/CCDI/ExplorePage/CCDI_Samples_Tbl',
//	'Object Repository/CCDI/ExplorePage/CCDI_Samples_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Samples_TblNextBtn', GlobalVariable.G_WebTabnameSamples,	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)
// 
//
////clicking the Files tab
//WebUI.waitForElementPresent(findTestObject('Object Repository/CCDI/ExplorePage/Files_ResultsTab'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/CCDI/ExplorePage/Files_ResultsTab')
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('CCDI', GlobalVariable.G_StatBar_Files, 'Object Repository/CCDI/ExplorePage/CCDI_Files_Tbl',
//'Object Repository/CCDI/ExplorePage/CCDI_Files_TblHdr', 'Object Repository/CCDI/ExplorePage/CCDI_Files_TblNextBtn', GlobalVariable.G_WebTabnameFiles,	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()

