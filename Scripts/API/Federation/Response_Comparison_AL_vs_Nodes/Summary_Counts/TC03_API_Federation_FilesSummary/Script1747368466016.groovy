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

// Define test object paths in the Object Repository
String objectPathKidsFirst = 'Object Repository/API/Federation/FederationNodes/KidsFirst/FederationNode-KidsFirst_FileSummary'
String objectPathStJude = 'Object Repository/API/Federation/FederationNodes/StJude/FederationNode-StJude_FileSummary'
String objectPathPcdc = 'Object Repository/API/Federation/FederationNodes/PCDC_UChicago/FederationNode-PCDC_UChicago_FileSummary'
String objectPathTreehouse = 'Object Repository/API/Federation/FederationNodes/Treehouse_UCSC/FederationNode-Treehouse_FileSummary'
String objectPathEcdna = 'Object Repository/API/Federation/FederationNodes/ecDNA/FederationNode-ecDNA_FileSummary'
String objectPathIu = 'Object Repository/API/Federation/FederationNodes/IUSCCC/FederationNode-IU_FileSummary'
String objectPathAggLayer = 'Object Repository/API/Federation/AggregationLayer/AL_FileSummary'
 
// Step 1: Send requests to the individual API Federation member nodes and get responses
ResponseObject responseKidsFirst = API_Functions.sendRequestAndCaptureResponse(objectPathKidsFirst) // for Kids First
ResponseObject responseStJude = API_Functions.sendRequestAndCaptureResponse(objectPathStJude) //for St Jude
ResponseObject responsePcdc = API_Functions.sendRequestAndCaptureResponse(objectPathPcdc) //for PCDC UChicago
ResponseObject responseTreehouse = API_Functions.sendRequestAndCaptureResponse(objectPathTreehouse) //for Treehouse
ResponseObject responseEcdna = API_Functions.sendRequestAndCaptureResponse(objectPathEcdna) //for ecDNA
ResponseObject responseIu = API_Functions.sendRequestAndCaptureResponse(objectPathIu) //for IU
ResponseObject responseAggLayer = API_Functions.sendRequestAndCaptureResponse(objectPathAggLayer)  // for Aggregated Layer response

// Step 2: Parse all the responses
def responseDataKidsFirst = API_Functions.parseResponse(responseKidsFirst) //kidsfirst
def responseDataStJude = API_Functions.parseResponse(responseStJude) //stjude
def responseDataPcdc = API_Functions.parseResponse(responsePcdc) //pcdc
def responseDataTreehouse = API_Functions.parseResponse(responseTreehouse) //treehouse
def responseDataEcdna = API_Functions.parseResponse(responseEcdna) //ecdna
def responseDataIu = API_Functions.parseResponse(responseIu) //iu
def responseDataAggLayer = API_Functions.parseResponse(responseAggLayer) //AL

// Step 3: Extract the individual node entries from the Aggregated Layer API response
def aggLayerEntryKidsFirst = API_Functions.findEntry(responseDataAggLayer, 'KidsFirst')
def aggLayerEntryStJude = API_Functions.findEntry(responseDataAggLayer, 'StJude')
def aggLayerEntryPcdc = API_Functions.findEntry(responseDataAggLayer, 'PCDC')
def aggLayerEntryTreehouse = API_Functions.findEntry(responseDataAggLayer, 'Treehouse')
def aggLayerEntryEcdna = API_Functions.findEntry(responseDataAggLayer, 'ccdi-ecDNA')
def aggLayerEntryIu = API_Functions.findEntry(responseDataAggLayer, 'ccdi-IUSCCC')

// Step 4: Compare the properties of the single node API response (step 2) against the AL extracted entry (step 3)
API_Functions.compareAPIResponses(responseDataKidsFirst, aggLayerEntryKidsFirst, 'KidsFirst')
API_Functions.compareAPIResponses(responseDataStJude, aggLayerEntryStJude, 'StJude')
API_Functions.compareAPIResponses(responseDataPcdc, aggLayerEntryPcdc, 'PCDC')
API_Functions.compareAPIResponses(responseDataTreehouse, aggLayerEntryTreehouse, 'Treehouse')
API_Functions.compareAPIResponses(responseDataEcdna, aggLayerEntryEcdna, 'ccdi-ecDNA')
API_Functions.compareAPIResponses(responseDataIu, aggLayerEntryIu, 'ccdi-IUSCCC')