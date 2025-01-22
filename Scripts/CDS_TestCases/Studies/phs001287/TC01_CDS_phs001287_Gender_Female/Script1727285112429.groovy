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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_CDS_phs001287_Gender_Female.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Clicking data tab
WebUI.waitForElementPresent(findTestObject('CDS/NavBar/CDS_Data-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/NavBar/CDS_Data-Btn')

//Clicking Phs Accession dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn')

//Clicking checkbox phs002504
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs001287_Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs001287_Chkbx')

//Clicking Phs Accession dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn')

//Clicking Gender dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/DemographicsFacet/Gender/Gender-Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/DemographicsFacet/Gender/Gender-Ddn')

//Clicking Male checkbox
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/DemographicsFacet/Gender/Female-Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/DemographicsFacet/Gender/Female-Chkbx')

//Read statbar
CustomKeywords.'utilities.TestRunner.readStatBarCDS'('CDS/StatBar/CDS_StatBar-Studies',
 'CDS/StatBar/CDS_StatBar-Participants','CDS/StatBar/CDS_StatBar-Samples', 'CDS/StatBar/CDS_StatBar-Files')


////Clicking participants tab
//WebUI.waitForElementPresent(findTestObject('CDS/Data_page/CDSResults_Participants_Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CDS/Data_page/CDSResults_Participants_Tab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Participants, 'CDS/Data_page/CDS_ParticipantsTable',
//	'CDS/Data_page/CDS_ParticipantsTableHeader', 'CDS/Data_page/CDS_ParticipantsTabNextBtn', GlobalVariable.G_WebTabnameParticipants,	'TsvDataParticipants', GlobalVariable.G_QueryParticipantsTab)

//clicking Samples tab
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/CDSResults_Samples_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('CDS/Data_page/CDSResults_Samples_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Samples, 'CDS/Data_page/CDS_SamplesTable',
	'CDS/Data_page/CDS_SamplesTableHeader', 'CDS/Data_page/CDS_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)

////clicking Files tab
//WebUI.waitForElementPresent(findTestObject('CDS/Data_page/CDSResults_Files_Tab'), 5)
//CustomKeywords.'utilities.TestRunner.clickTab'('CDS/Data_page/CDSResults_Files_Tab')
//CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Files, 'CDS/Data_page/CDS_FilesTable',
//	'CDS/Data_page/CDS_FilesTableHeader', 'CDS/Data_page/CDS_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
//	'TsvDataFiles', GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()