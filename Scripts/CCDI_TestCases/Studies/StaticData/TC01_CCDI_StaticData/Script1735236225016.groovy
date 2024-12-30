import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//CCDI
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('CCDI_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Verify static data text - Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to About > About CCDI Hub
WebUI.click(findTestObject('CCDI/Navbar/About-Btn'))
WebUI.click(findTestObject('CCDI/Navbar/About-AboutCCDI-Btn'))

//Verify static data text - About CCDI Hub page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutCCDIPage")

//Navigate to About > CCDI Data Usage Policies & Terms
WebUI.click(findTestObject('CCDI/Navbar/About-Btn'))
WebUI.click(findTestObject('CCDI/Navbar/About-CCDITerms-Btn'))

//Verify static data text - About > CCDI Data Usage Policies & Terms page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutPoliciesPage")

WebUI.closeBrowser()