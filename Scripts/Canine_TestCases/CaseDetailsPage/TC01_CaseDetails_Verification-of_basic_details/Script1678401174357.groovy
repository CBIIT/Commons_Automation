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
/*This test script:

  - Opens the browser of choice: Chrome, Firefox or Edge
  - Driver opened by Katalon is used in Selenium.
  - Takes the Query from input excel and fetches data from Neo4j database.
	Saves the results from neo4j and application in the same name mentioned in the input excel.
  - Clicks on the Cases button in the Navbar of ICDC's homepage.
  - Clicks on the Filter 'Breed' from left pane
  - Selects the specific check box from 'Breed' filter.
  - Reads the results displayed for the selected filter (from all the pages in UI) and saves in the excel mentioned in Input file
  - Reads Neo4j DB using the query from Input file and saves the data in the excel mentioned in Input file
  - Reads Neo4j excel and Webdata excel as lists and compares the data.
  */
WebUI.closeBrowser()



//Step 1--------------------Opening the desired url ****************************************************************
GlobalVariable.G_caseID=ipCaseID                     //this global variable is required in the second test case being called for table data.  
                                //for comparing the cell data to find if it has the specific suffix url and then grab only the contents of that row
System.out.println ("This is the value of the case id for this iteration :"+GlobalVariable.G_caseID)


System.out.println('This is base url: ' + GlobalVariable.G_Urlname)

GlobalVariable.suffixUrl=sUrl   
System.out.println('This is the suffix url stored as global variable: ' + GlobalVariable.suffixUrl)
String Url = GlobalVariable.G_Urlname + GlobalVariable.suffixUrl
GlobalVariable.fullUrl = Url
System.out.println('This is the full url: ' + GlobalVariable.fullUrl)


CustomKeywords.'utilities.ICDCcaseDetails.readInput'('ICDC_CaseDetails.xlsx')
System.out.println ("Successfully read the input excel")


WebUI.waitForElementPresent(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'), 5)

WebUI.click(findTestObject('Object Repository/Canine/Canine_PopUp_Continue_Btn'))

System.out.println('Closed the popup window')

WebUI.maximizeWindow()

//CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_CaseDetails_Verification-of_basic_details.xlsx')
Thread.sleep(2000)

System.out.println('This is the url of the page after landing into the case detail page: ' + WebUI.getUrl())


CustomKeywords.'utilities.ICDCcaseDetails.readInfoPanel'('Demographics', GlobalVariable.G_WebTabnameCDDemographics,
	 GlobalVariable.G_CypherTabnameCDDemographics, GlobalVariable.G_QueryCDDemographics)

      
CustomKeywords.'utilities.ICDCcaseDetails.readInfoPanel'('Diagnosis', GlobalVariable.G_WebTabnameCDDiagnosis,
	GlobalVariable.G_CypherTabnameCDDiagnosis, GlobalVariable.G_QueryCDDiagnosis)

CustomKeywords.'utilities.ICDCcaseDetails.readInfoPanel'('Study', GlobalVariable.G_WebTabnameCDStudy,
	GlobalVariable.G_CypherTabnameCDStudy, GlobalVariable.G_QueryCDStudy)



CustomKeywords.'utilities.ICDCcaseDetails.readStatBarICDC'('Object Repository/Canine/StatBar/Canine_StatBar-Programs','Object Repository/Canine/StatBar/Canine_StatBar-Studies',
	'Object Repository/Canine/StatBar/Canine_StatBar-Cases', 'Object Repository/Canine/StatBar/Canine_StatBar-Samples',
	'Object Repository/Canine/StatBar/Canine_StatBar-CaseFiles', 'Object Repository/Canine/StatBar/Canine_StatBar-StudyFiles')


//collecting data from the Samples table

CustomKeywords.'utilities.ICDCcaseDetails.multiFunctionCD'('ICDC', 'Object Repository/Canine/CaseDetailsPage/CaseDetails_SamplesTable',
	'Object Repository/Canine/CaseDetailsPage/CaseDetails_SamplesHdr', 'Object Repository/Canine/CaseDetailsPage/CaseDetails_SamplesTable_NxtBtn', GlobalVariable.G_WebTabnameSamples,
	GlobalVariable.G_CypherTabnameSamples, GlobalVariable.G_QuerySamplesTab)
 
	
//clicking data from the case Files table

//WebUI.click(findTestObject('Object Repository/Canine/CaseDetailsPage/Size_col'))
CustomKeywords.'utilities.ICDCcaseDetails.multiFunctionCD'('ICDC', 'Object Repository/Canine/CaseDetailsPage/CaseDetails_FilesTable',
	'Object Repository/Canine/CaseDetailsPage/CaseDetails_FilesHdr', 'Object Repository/Canine/CaseDetailsPage/CaseDetails_FilesTable_NxtBtn', GlobalVariable.G_WebTabnameFiles,
	GlobalVariable.G_CypherTabnameFiles, GlobalVariable.G_QueryFilesTab)


 
WebUI.closeBrowser()

 


