import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable as GlobalVariable

/*This test script:
Verifies the static data on the pages based on Test Object via Excel sheet
 */
WebUI.closeBrowser()

//C3DC
//Initiate and click Continue on warning popup
CustomKeywords.'utilities.TestRunner.RunKatalon'('C3DC_StaticData.xlsx')
CustomKeywords.'utilities.TestRunner.clickTab'('C3DC/HomePage/WarningBan_Continue_Btn')

//Verify static data text - Home page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_HomePage")

//Navigate to Data Model
WebUI.click(findTestObject('C3DC/Navbar/DataModel-Tab'))

//Verify static data text - Data Model page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_DataModelPage")

//Navigate to Resources > Resources
WebUI.click(findTestObject('C3DC/Navbar/Resources-Tab'))
WebUI.click(findTestObject('C3DC/NavBar/Resources-Resources-Btn'))

//Verify static data text - Resources page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_ResourcesPage")

//Navigate to About > About
WebUI.click(findTestObject('C3DC/Navbar/About-Tab'))
WebUI.click(findTestObject('C3DC/NavBar/About-About-Btn'))

//Verify static data text - About > About page
CustomKeywords.'utilities.TestRunner.verifyStaticData'("V_AboutAboutPage")


WebUI.closeBrowser()