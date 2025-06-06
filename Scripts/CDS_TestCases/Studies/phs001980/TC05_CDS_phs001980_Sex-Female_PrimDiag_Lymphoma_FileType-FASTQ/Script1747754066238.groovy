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

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC05_CDS_phs001980_Sex-Female_PrimDiag_Lymphoma_FileType-FASTQ.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Clicking data tab
WebUI.waitForElementPresent(findTestObject('CDS/NavBar/CDS_Data-Btn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/NavBar/CDS_Data-Btn')

//Clicking Phs Accession dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn')

//Clicking checkbox phs001819
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs001980_Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/phs001980_Chkbx')

/*//Clicking Phs Accession dropdown
WebUI.waitForElementPresent(findTestObject('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('CDS/Data_page/Filter/StudyFacet/PHS_Accession/PHS_Accession_Ddn')*/

//Clicking Gender dropdown
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/DemographicsFacet/Sex/Sex-Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/DemographicsFacet/Sex/Sex-Ddn')

//Clicking Male checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/DemographicsFacet/Sex/Female-Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/DemographicsFacet/Sex/Female-Chkbx')

//Clicking Primary Diagnosis dropdown
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/DiagnosisFacet/PrimaryDiagnosis/PrimaryDiagnosis_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/DiagnosisFacet/PrimaryDiagnosis/PrimaryDiagnosis_Ddn')

//Clicking Multiple Myeloma checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/DiagnosisFacet/PrimaryDiagnosis/LymphomaNOS_Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/DiagnosisFacet/PrimaryDiagnosis/LymphomaNOS_Chkbx')

//Clicking File dropdown
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/FilesFacet/FileType/FileType_Ddn'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/FilesFacet/FileType/FileType_Ddn')

//Clicking BAM checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/Filter/FilesFacet/FileType/fastq-Chkbx'),5)
CustomKeywords.'utilities.TestRunner.clickTabCDSStat'('Object Repository/CDS/Data_page/Filter/FilesFacet/FileType/fastq-Chkbx')

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
	'Object Repository/CDS/Data_page/CDS_SamplesTableHeader', 'Object Repository/CDS/Data_page/CDS_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	'TsvDataSamples', GlobalVariable.G_QuerySamplesTab)

//clicking Files tab
WebUI.waitForElementPresent(findTestObject('Object Repository/CDS/Data_page/CDSResults_Files_Tab'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/CDS/Data_page/CDSResults_Files_Tab')
CustomKeywords.'utilities.TestRunner.multiFunction'('CDS',GlobalVariable.G_StatBar_Files, 'Object Repository/CDS/Data_page/CDS_FilesTable',
	'Object Repository/CDS/Data_page/CDS_FilesTableHeader', 'Object Repository/CDS/Data_page/CDS_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	'TsvDataFiles', GlobalVariable.G_QueryFilesTab)
 
WebUI.closeBrowser()