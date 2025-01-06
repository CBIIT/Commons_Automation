import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//CCDC
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('CCDC_StaticData.xlsx')

//Verify static data text for Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to CCDI Studies > CCDI Resource & Datasets
WebUI.click(findTestObject('CCDC/Navbar/CCDIStudies-Tab'))
WebUI.click(findTestObject('CCDC/Navbar/CCDIStudies-Resources-Btn'))

//Verify static data text for CCDI Resource & Datasets page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_CCDIResourcePage")

//Navigate to About > About CCDI Data Catalog
WebUI.click(findTestObject('CCDC/Navbar/About-Tab'))
//WebUI.waitForElementVisible('CCDC/Navbar/About-AboutCCDI-Btn', 5)
WebUI.click(findTestObject('CCDC/Navbar/About-AboutCCDI-Btn'))

//Verify static data text for About CCDI Data Catalog page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutCCDIPage")

//Navigate to About > Contribute to the CCDC
WebUI.click(findTestObject('CCDC/Navbar/About-Tab'))
//WebUI.waitForElementVisible('CCDC/Navbar/About-Contribute-Btn', 5)
WebUI.click(findTestObject('CCDC/Navbar/About-Contribute-Btn'))

//Verify static data text for Contribute to the CCDC page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutContributePage")

WebUI.closeBrowser()