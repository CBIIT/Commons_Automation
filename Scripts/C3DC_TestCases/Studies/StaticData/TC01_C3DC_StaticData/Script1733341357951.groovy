import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the Home page based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//C3DC
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('C3DC_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/HomePage/WarningBan_Continue_Btn')


//Verify static data text - Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("HomePage")



WebUI.closeBrowser()

