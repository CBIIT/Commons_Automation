import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//Bento
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('Bento_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Verify static data text for Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to About > Bento
WebUI.click(findTestObject('Bento/NavBar/Bento_About-Btn'))
WebUI.click(findTestObject('Bento/NavBar/Bento_About-Bento-Btn'))

//Verify static data text for About > Bento page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutBentoPage")

//Navigate to About > Resources
WebUI.click(findTestObject('Bento/NavBar/Bento_About-Btn'))
WebUI.click(findTestObject('Bento/NavBar/Bento_About-Resources-Btn'))

//Verify static data text for About > Resources page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutResourcesPage")


WebUI.closeBrowser()