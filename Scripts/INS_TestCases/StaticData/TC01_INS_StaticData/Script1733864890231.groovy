import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//INS
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('INS_StaticData.xlsx')

//Verify static data text for Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to About > About INS
WebUI.click(findTestObject('INS/Navbar/About-Btn'))
WebUI.click(findTestObject('INS/Navbar/About-INS-Btn'))

//Verify static data text for About > About INS page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutInsPage")


WebUI.closeBrowser()