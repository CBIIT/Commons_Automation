import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint


'Opens the browser of choice: Chrome, Firefox or Edge'
WebUI.closeBrowser()

//WebUI.maximizeWindow()
//'Driver opened by Katalon is used in Selenium from this step.'
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.testSetup'('')


'This step takes the Query from input excel and fetches data from Neo4j database. \r\nSaves the results from neo4j and application in the same name mentioned in the input excel. '
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.RunKatalon'('TC08_Canine_StudyOSA03-Biobank_StudyParticipation_Sex.xlsx')

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')
System.out.println ("Closed the popup window");

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Canine/NavBar/Canine_Cases_Btn')


//WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/FilterByCases_Facet'), 5)
//CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/Filter/FilterByCases_Facet')


'Clicks on the Filter \'Study\' from left pane'
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Study/Canine_Filter_Study'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Study/Canine_Filter_Study')

'Selects the specific check box from \'Study\' filter.'
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Study/Canine_Filter_Study-OSA03_Chkbx')

//Biobank - Dropdwn - Checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Biobank/Biobank_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Biobank/Biobank_Ddn')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Canine/Filter/Biobank/NotApplicable_Chkbx')


//Diagnosis - Dropdwn - Checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Diagnosis/DIAGNOSIS_Ddn')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Diagnosis/OsteoSarcoma_Chkbx')


//Sex - Dropdwn - Checkbox
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/Sex/SEX_Ddn'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Sex/SEX_Ddn')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTabCanineStat'('Object Repository/Canine/Filter/Sex/Male_Chkbx')

CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.readStatBarCanine'('Object Repository/Canine/StatBar/Canine_StatBar-Programs','Object Repository/Canine/StatBar/Canine_StatBar-Studies',
	'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples',
	'Object Repository/Canine/StatBar/Canine_StatBar-CaseFiles', 'Object Repository/Canine/StatBar/Canine_StatBar-StudyFiles')


// clicking the Cases tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Cases_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Cases_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Cases, 'Object Repository/Canine/Canine_CasesTable',
	'Object Repository/Canine/Canine_TableHeader', 'Object Repository/Canine/Canine_CasesTabNextBtn', GlobalVariable.G_WebTabnameCases,
	GlobalVariable.G_CypherTabnameCases, GlobalVariable.G_QueryCasesTab)


//clicking the Samples tab
WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/CanineResults_Samples_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_Samples_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Samples, 'Object Repository/Canine/Canine_Samples_Table',
	'Object Repository/Canine/Canine_Samples_TableHdr', 'Object Repository/Canine/Canine_SamplesTabNextBtn', GlobalVariable.G_WebTabnameSamples,
	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)

	
//clicking the case Files tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_CaseFiles_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_CaseFiles_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_Files, 'Object Repository/Canine/Canine_Files_Table',
	'Object Repository/Canine/Canine_Files_TableHdr', 'Object Repository/Canine/Canine_FilesTabNextBtn', GlobalVariable.G_WebTabnameFiles,
	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)


//clicking the study Files tab
WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_StudyFiles_Tab'), 5)
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.clickTab'('Object Repository/Canine/CanineResults_StudyFiles_Tab')
CustomKeywords.'ctdc.utilities.runtestcaseforKatalon.multiFunction'('ICDC', GlobalVariable.G_StatBar_StudyFiles, 'Object Repository/Canine/Canine_StudyFiles_Table',
	'Object Repository/Canine/Canine_StudyFiles_TableHdr', 'Object Repository/Canine/Canine_StudyFilesTabNextBtn', GlobalVariable.G_WebTabnameStudyFiles,
	GlobalVariable.G_CypherTabnameStudyFiles, GlobalVariable.G_QueryStudyFilesTab)
 
  
WebUI.closeBrowser()