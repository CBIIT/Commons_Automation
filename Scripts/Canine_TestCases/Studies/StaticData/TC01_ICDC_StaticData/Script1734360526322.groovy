import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//ICDC
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('ICDC_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_WarningMsgBtn')

//Verify static data text - Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to Programs
WebUI.click(findTestObject('Canine/NavBar/Canine_Programs_Btn'))

//Verify static data text - Programs page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_ProgramsPage")

//Navigate to Data > Data Use
WebUI.click(findTestObject('Canine/NavBar/Canine_Data_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_DataUse_Btn'))

//Verify static data text - Data Use page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataUsePage")

//Navigate to Data > Data Submission Guidelines
WebUI.click(findTestObject('Canine/NavBar/Canine_Data_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_DataSubmission_Btn'))

//Verify static data text - Data Submission Guidelines page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataSubmissionPage")

//Navigate to Resources > Developers
WebUI.click(findTestObject('Canine/NavBar/Canine_Resources_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_ResourcesDevelopers_Btn'))

//Verify static data text - Developers page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_ResourcesDeveloperPage")

//Navigate to About > Purpose
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_About_Btn'), 5)
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutPurpose_Btn'))

//Verify static data text - Purpose page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutPurposePage")

//Navigate to About > Steering Committee
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutSteering_Btn'))

//Verify static data text - Steering Committee page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutSteeringPage")

//Navigate to About > Data Governance Advisory Board (DGAB)
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutDgab_Btn'))

//Verify static data text - Data Governance Advisory Board (DGAB) page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutDGABPage")

//Navigate to About > Best Practices Sub-Committee (BPSC)
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutBpsc_Btn'))

//Verify static data text - Best Practices Sub-Committee (BPSC) page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutBPSCPage")

//Navigate to About > Working Groups
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutWorkGroups_Btn'))

//Verify static data text - Working Groups page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutWorkGroupsPage")

//Navigate to About > CRDC & Analysis
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutCrdc_Btn'))

//Verify static data text - CRDC & Analysis page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutCRDCPage")

//Navigate to About > Support
WebUI.click(findTestObject('Canine/NavBar/Canine_About_Btn'))
WebUI.click(findTestObject('Canine/NavBar/Canine_AboutSupport_Btn'))

//Verify static data text - Support page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutSupportPage")


WebUI.closeBrowser()