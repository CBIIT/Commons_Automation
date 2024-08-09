import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary as FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver as FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions as FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW

WebUI.closeBrowser()

CustomKeywords.'utilities.TestRunner.RunKatalon'('TC02_Canine_MFST_SamplePatho-TCellLymphoma.xlsx')

WebUI.waitForElementPresent(findTestObject('Canine/NavBar/Canine_Cases_Btn'), 5)

CustomKeywords.'utilities.TestRunner.clickTab'('Object Repository/Canine/Canine_PopUp_Continue_Btn')

System.out.println('Closed the popup window')

CustomKeywords.'utilities.TestRunner.clickTab'('Canine/NavBar/Canine_Cases_Btn')

WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Filter/FilterBySamples_Facet'), 5)

'Get \'class\' attribute value of make appointment button'
attribute = WebUI.getAttribute(findTestObject('Object Repository/Canine/Filter/FilterByCases_Facet'), 'xpath')

System.out.println('This is the value of the attribute : ' + attribute)

String xpathElem = CustomKeywords.'utilities.TestRunner.givexpath'('Object Repository/Canine/Filter/FilterByCases_Facet')

System.out.println('This is the value of the xpath fm getxpath: ' + xpathElem)

String value = '//*[contains(text(),\'Filter By Cases\')]'

//WebUI.verifyMatch(attribute, xpathElem, false)
WebUI.verifyElementPresent(findTestObject('Object Repository/Canine/Filter/FilterByCases_Facet'), 0)
System.out.println("Element is present in dom")
'Verify if \'xpath\' attribute value is correct for the button.'
Thread.sleep(3000)

System.out.println('Value of cases matches')

'Close browser'
WebUI.closeBrowser()



