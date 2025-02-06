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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import com.kms.katalon.core.testdata.TestDataFactory
import utilities.API_Functions
import utilities.APIValidationFunctions

// Send API request
String API_Object_Path ='Object Repository/API/Federation/AL_SubjectCountsByRace'
ResponseObject responseAL = API_Functions.sendRequestAndCaptureResponse(API_Object_Path)
//def response = WS.sendRequest(findTestObject('API_Object_Path'))  
def responseALData = API_Functions.parseResponse(responseAL) //AL response
System.out.println("This is the content of responseALData :"+responseALData)
//System.out.println("This is the content of responsebodycontent inbuilt func :"+responseALData.getResponseBodyContent())

// Get allowed values from the data file
def allowedValuesData = TestDataFactory.findTestData('Data Files/API/Enum-Subject_Race')
List<String> allowedValues = []
for (int i = 1; i <= allowedValuesData.getRowNumbers(); i++) {
	allowedValues.add(allowedValuesData.getValue(1, i))
}

// Validate the API response
APIValidationFunctions.validateAllowedEnums(responseALData, allowedValues)
System.out.println("This is the value of responsealdata :"+responseALData)
System.out.println("This is the value of allowedValues :"+allowedValues)