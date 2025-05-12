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
import java.nio.file.Files
import java.nio.file.Paths

// Send API request
String API_Object_Path ='Object Repository/API/Federation/AggregationLayer/AL_SampleCounts_by_AnatomicalSites'
ResponseObject responseAL = API_Functions.sendRequestAndCaptureResponse(API_Object_Path)

def responseALData = API_Functions.parseResponse(responseAL) //AL response
System.out.println("This is the content of responseALData :" + responseALData)

// Get allowed values from the data file
String csvPath = "InputFiles/API/Federation/anatomical_sites.csv"
List<String> allowedValues = Files.readAllLines(Paths.get(csvPath)).findAll { it?.trim() }

Set<String> allowedValuesSet = new HashSet<>(allowedValues)

// Validate the API response
APIValidationFunctions.validateAllowedEnums(responseALData, allowedValuesSet)
System.out.println("This is the value of responseALData: " + responseALData)
