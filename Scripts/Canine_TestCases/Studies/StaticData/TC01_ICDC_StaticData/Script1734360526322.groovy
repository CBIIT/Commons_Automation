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



WebUI.closeBrowser()

