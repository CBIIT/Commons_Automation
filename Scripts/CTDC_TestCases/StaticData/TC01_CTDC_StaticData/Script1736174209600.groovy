import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//CTDC
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('CTDC_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Trials/Trials_WarningContinue_Btn')

//Verify static data text for Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to Data > Cloud Computing
WebUI.click(findTestObject('Trials/NavBar/Data-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Data-Cloud-Btn'))

//Verify static data text for Cloud Computing page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataCloudPage")

//Navigate to Data > Data Model
WebUI.click(findTestObject('Trials/NavBar/Data-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Data-Model-Btn'))

//This page has had the static data removed and is now a data model navigator
//Verify static data text for Data Model page
//CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataModelPage")

//Navigate to Data > Data Harmonization
WebUI.click(findTestObject('Trials/NavBar/Data-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Data-Harmonization-Btn'))

//Verify static data text for Data Harmonization page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataHarmonizationPage")

//Navigate to Data > Data Terms of Use
WebUI.click(findTestObject('Trials/NavBar/Data-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Data-Terms-Btn'))

//Verify static data text for Data Terms of Use page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataTermsPage")

//Navigate to Data > Data Submission
WebUI.click(findTestObject('Trials/NavBar/Data-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Data-Submission-Btn'))

//Verify static data text for Data Submission page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataSubmissionPage")

//Navigate to Resources > GraphQL
WebUI.click(findTestObject('Trials/NavBar/Resources-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Resources-GraphQL-Btn'))

//Verify static data text for GraphQL page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_ResourcesGraphQLPage")

//Navigate to Resources > Additional Information
WebUI.click(findTestObject('Trials/NavBar/Resources-Tab'))
WebUI.click(findTestObject('Trials/NavBar/Resources-AddtlInfo-Btn'))

//Verify static data text for Additional Information page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_ResourcesInfoPage")

//Navigate to About > Purpose
WebUI.click(findTestObject('Trials/NavBar/About-Tab'))
WebUI.click(findTestObject('Trials/NavBar/About-Purpose-Btn'))

//Verify static data text for Purpose page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutPurposePage")

//Navigate to About > Contact Us
WebUI.click(findTestObject('Trials/NavBar/About-Tab'))
WebUI.click(findTestObject('Trials/NavBar/About-ContactUs-Btn'))

//Verify static data text for Contact Us page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutContactPage")

//Navigate to Request Access
WebUI.click(findTestObject('Trials/NavBar/RequestAccess-Btn'))
//Expand informational dropdowns
CustomKeywords.'utilities.TestRunner.clickTab'('Trials/RequestAccess_page/ObtainCommonsDropdown')
CustomKeywords.'utilities.TestRunner.clickTab'('Trials/RequestAccess_page/ObtainDbgapDropdown')
CustomKeywords.'utilities.TestRunner.clickTab'('Trials/RequestAccess_page/RequestAccessDropdown')
WebUI.delay(1)
CustomKeywords.'utilities.TestRunner.clickTab'('Trials/RequestAccess_page/AccessDatasetsDropdown')

//Verify static data text for Request Access page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_RequestAccessPage")

WebUI.closeBrowser()