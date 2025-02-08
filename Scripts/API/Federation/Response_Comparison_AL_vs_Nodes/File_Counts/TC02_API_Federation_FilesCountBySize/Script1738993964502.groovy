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

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import com.kms.katalon.core.util.KeywordUtil

import utilities.API_Functions

// Define the directory where you want to save the text files
String outputDirectory = GlobalVariable.outputDirectory

// Define test object paths in the Object Repository
String api1ObjectPath = 'Object Repository/API/Federation/FederationNodes/KidsFirst/Files-Size'
String api2ObjectPath = 'Object Repository/API/Federation/FederationNodes/StJude/Files-Size'
String api3ObjectPath = 'Object Repository/API/Federation/FederationNodes/PCDC_UChicago/Files-Size'
String api4ObjectPath = 'Object Repository/API/Federation/FederationNodes/Treehouse_UCSC/Files-Size'
String api5ObjectPath = 'Object Repository/API/Federation/AggregationLayer/AL_FileCounts_by_Size'
 
// Step 1: Send Request to the API and Get Response
ResponseObject response1 = API_Functions.sendRequestAndCaptureResponse(api1ObjectPath) // for kidsfirst
ResponseObject response2 = API_Functions.sendRequestAndCaptureResponse(api2ObjectPath) //for st jude
ResponseObject response3 = API_Functions.sendRequestAndCaptureResponse(api3ObjectPath) //for pcdc uchicago
ResponseObject response4 = API_Functions.sendRequestAndCaptureResponse(api4ObjectPath) //for treehouse
ResponseObject response5 = API_Functions.sendRequestAndCaptureResponse(api5ObjectPath)  // for AL response

// Step 3: Parse all the Responses
def response1Data = API_Functions.parseResponse(response1) //kidsfirst
def response2Data = API_Functions.parseResponse(response2) //stjude
def response3Data = API_Functions.parseResponse(response3) //pcdc
def response4Data = API_Functions.parseResponse(response4) //treehouse
def response5Data = API_Functions.parseResponse(response5) //AL response

// Step 4: Extract the Entry from the AL API Response based on the specified key/source value
def kidsFirstEntry = API_Functions.findEntry(response5Data, 'KidsFirst')
def stJudeEntry = API_Functions.findEntry(response5Data, 'StJude')
def pcdcEntry = API_Functions.findEntry(response5Data, 'PCDC')
def treehouseEntry = API_Functions.findEntry(response5Data, 'Treehouse')

// Step 5: Compare the Properties of the AL extracted Entry Against the single node API Response
API_Functions.compareAPIResponses(response1Data, kidsFirstEntry, 'KidsFirst')
API_Functions.compareAPIResponses(response2Data, stJudeEntry, 'StJude')
API_Functions.compareAPIResponses(response3Data, pcdcEntry, 'PCDC')
API_Functions.compareAPIResponses(response4Data, treehouseEntry, 'Treehouse')

// Step 6: If comparison succeeds
KeywordUtil.markPassed("The entry in the AL API response matches the node level API response.")
