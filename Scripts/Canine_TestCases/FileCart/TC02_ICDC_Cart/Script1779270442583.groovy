import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable as GlobalVariable
import utilities.TestRunner

WebUI.closeBrowser()

CustomKeywords.'utilities.FileCart.setInputExcelForPython'('TC02_ICDC_Cart.xlsx')

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.G_Urlname)

TestRunner.driver = DriverFactory.getWebDriver()

WebUI.delay(3)

// Close popup
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_PopUp_Continue_Btn')

// Go to Explore / Cases
WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Cases_Btn')

// Apply filters from current Excel row
CustomKeywords.'utilities.FileCart.applyFacetFilters'(tc_facetFilters)

WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_Cases_Tab'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_Cases_Tab')

// Click Add Files button from current Excel row
CustomKeywords.'utilities.FileCart.clickAddFilesButton'(
    tc_TabName,
    tc_buttonType
)

// Go to My Files
WebUI.waitForElementPresent(findTestObject('Canine/Canine_GoToFiles'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_GoToFiles')



// Validate File Cart table and count
CustomKeywords.'utilities.FileCart.multiFunctionFileCart'(
    'Canine/fileCentricCart/Canine_myFiles_Tbl',
    'Canine/fileCentricCart/Canine_myFiles_TblHdr',
    'Canine/fileCentricCart/Canine_myFilesTable_Nxtbtn',
    tc_WebExcel,
    tc_TsvExcel,
    tc_fileCartQuery,
    tc_totalFilesQuery
)

WebUI.closeBrowser()