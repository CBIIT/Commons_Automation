import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable


WebUI.closeBrowser()

CustomKeywords.'utilities.FileCart.readInput'('TC01_ICDC_FileCart_COTC021_Breed.xlsx')

CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_PopUp_Continue_Btn')
System.out.println ("Closed the popup window");

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Cases_Btn')


WebUI.waitForElementPresent(findTestObject('Canine/Filter/Study/Canine_Filter_Study'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Filter/Study/Canine_Filter_Study')
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Filter/Study/Canine_Filter_Study-COTC021_Chkbx')

WebUI.waitForElementPresent(findTestObject('Canine/Filter/Breed/BREED_Ddn'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Filter/Breed/BREED_Ddn')

WebUI.waitForElementPresent(findTestObject('Canine/Filter/Breed/Boxer_Chkbx'), 5)
CustomKeywords.'utilities.TestRunner.clickTabCanineStat'('Canine/Filter/Breed/Boxer_Chkbx')

WebUI.waitForElementPresent(findTestObject('Canine/CanineResults_Cases_Tab'), 10)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/CanineResults_Cases_Tab')

CustomKeywords.'utilities.FileCart.clickAddFilesButton'("Cases", "All")

WebUI.waitForElementPresent(findTestObject('Canine/Canine_GoToFiles'), 5)
CustomKeywords.'utilities.TestRunner.clickTab'('Canine/Canine_GoToFiles')

CustomKeywords.'utilities.FileCart.TotalFilesInCart'()

CustomKeywords.'utilities.FileCart.multiFunctionFileCart'(
	'Canine/fileCentricCart/Canine_myFiles_Tbl',
	'Canine/fileCentricCart/Canine_myFiles_TblHdr',
	'Canine/fileCentricCart/Canine_myFilesTable_Nxtbtn',
	"CartWebData",
	"TsvDataCaseFiles",
	GlobalVariable.G_cartQuery,
	GlobalVariable.G_myCartTotal
)




WebUI.closeBrowser()


 
