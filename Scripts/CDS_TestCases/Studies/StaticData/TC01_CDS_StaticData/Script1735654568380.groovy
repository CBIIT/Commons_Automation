import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//CDS
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('CDS_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('Bento/Banner/Bento_Warning_Continue_Btn')

//Verify static data text for Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to About > About CDS
WebUI.click(findTestObject('CDS/NavBar/About_DropDown'))
WebUI.click(findTestObject('CDS/NavBar/About_AboutCDS-Btn'))

//Verify static data text for About > About CDS page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutCDSPage")

//Navigate to About > Data Model
WebUI.click(findTestObject('CDS/NavBar/About_DropDown'))
WebUI.click(findTestObject('CDS/NavBar/About_DataModel-Btn'))

//Verify static data text for About > Data Model page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutDataModelPage")


WebUI.closeBrowser()