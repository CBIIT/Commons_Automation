import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


WebUI.closeBrowser()

//CustomKeywords.'utilities.FileCart.readInput'('TC02_ICDC_Cart.xlsx')


CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_PopUp_Continue_Btn')

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'),10)

CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Cases_Btn')

CustomKeywords.'utilities.FileCart.applyFacetFilters'(GlobalVariable.G_FacetFilters)

WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_Cases_Tab'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_Cases_Tab')

CustomKeywords.'utilities.FileCart.clickAddFilesButton'(
    GlobalVariable.G_inputTabName,
    GlobalVariable.G_ButtonType
)

WebUI.waitForElementPresent(findTestObject('Canine/Canine_GoToFiles'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_GoToFiles')

CustomKeywords.'utilities.FileCart.multiFunctionFileCart'(
    'Canine/fileCentricCart/Canine_myFiles_Tbl',
    'Canine/fileCentricCart/Canine_myFiles_TblHdr',
    'Canine/fileCentricCart/Canine_myFilesTable_Nxtbtn',
    GlobalVariable.G_OutputFileName,
    GlobalVariable.G_dbexcel,
    GlobalVariable.G_cartQuery,
    GlobalVariable.G_TotalFilesQuery
)

WebUI.closeBrowser()


 
