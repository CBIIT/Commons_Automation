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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC05_CCDI_phs002504_FileType-bam_LibStr-WXS_LibSel-PCR_DC-Sequencing.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('CCDI/Navbar/Explore_Menu'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/Navbar/Explore_Menu')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/Study_Facet/Study_Facet'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/Study_Facet/Study_Facet')

//phs accession drop down
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/PHS_Accession_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002504_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/Study_Facet/PHS_Accession/phs002504_Chkbx')


//Diagnosis drop down
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/DataCategory_Facet/FileType/FileType_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/DataCategory_Facet/FileType/FileType_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/DataCategory_Facet/FileType/bam_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/DataCategory_Facet/FileType/bam_Chkbx')


//Diag Anatomic site drop down
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory/DataCategory_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory/DataCategory_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory/Sequencing_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/DataCategory_Facet/DataCategory/Sequencing_Chkbx')


//Diag basis drop down

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibrarySelection/LibSelection_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibrarySelection/LibSelection_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibrarySelection/PCR_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibrarySelection/PCR_Chkbx')

//Survival drop down

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibraryStrategy/LibStrat_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibraryStrategy/LibStrat_Ddn')

WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibraryStrategy/WXS_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Filters/SequencingLibrary_Facet/LibraryStrategy/WXS_Chkbx')

//Read Statbar
CustomKeywords.'utilities.TestRunner.readStatBarCCDIhub'('CCDI/Statbar/Studies_Cnt', 'CCDI/Statbar/Participants_Cnt',
	'CCDI/Statbar/Samples_Cnt', 'CCDI/Statbar/Files_Cnt')

//Participants tab
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Participants_ResultsTab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Participants_ResultsTab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Participants, 'CCDI/ExplorePage/CCDI_Participants_Tbl',
	'CCDI/ExplorePage/CCDI_Participants_TblHdr', 'CCDI/ExplorePage/CCDI_Participants_TblNextBtn', GlobalVariable.G_WebTabnameParticipants,
	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)



/*//clicking the Studies tab 
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Studies_ResultsTab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Studies_ResultsTab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Studies, 'CCDI/ExplorePage/CCDI_Studies_Tbl',
	'CCDI/ExplorePage/CCDI_Studies_TblHdr', 'CCDI/ExplorePage/CCDI_Studies_TblNextBtn', GlobalVariable.G_WebTabnameStudies,
	'TsvDataStudies', GlobalVariable.G_QueryStudiesTab)
*/


//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Samples_ResultsTab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Samples_ResultsTab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Samples, 'CCDI/ExplorePage/CCDI_Samples_Tbl',
	'CCDI/ExplorePage/CCDI_Samples_TblHdr', 'CCDI/ExplorePage/CCDI_Samples_TblNextBtn', GlobalVariable.G_WebTabnameSamples,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)
 

//clicking the Files tab
WebUI.waitForElementPresent(findTestObject('CCDI/ExplorePage/Files_ResultsTab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CCDI/ExplorePage/Files_ResultsTab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CCDI', GlobalVariable.G_StatBar_Files, 'CCDI/ExplorePage/CCDI_Files_Tbl',
'CCDI/ExplorePage/CCDI_Files_TblHdr', 'CCDI/ExplorePage/CCDI_Files_TblNextBtn', GlobalVariable.G_WebTabnameFiles,
'TsvDataFiles', GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()