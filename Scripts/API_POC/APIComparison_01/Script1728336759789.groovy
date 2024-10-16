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

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import java.nio.file.Files
import java.nio.file.Paths

// Step 1: Send requests to both APIs
def responseApi1 = WS.sendRequest(findTestObject('null'))  
def responseApi2 = WS.sendRequest(findTestObject('Object Repository/API/AL- Subject counts by ethnicity'))  

// Parse the JSON responses
def api1Response = new JsonSlurper().parseText(responseApi1.getResponseBodyContent())
def api2Response = new JsonSlurper().parseText(responseApi2.getResponseBodyContent())

// Step 2: Save API 1 response to a file
def api1FilePath = "/Users/radhakrishnang2/Desktop/Automation/Sep2024/Commons_Automation/OutputFiles/api1response.txt"
Files.write(Paths.get(api1FilePath), responseApi1.getResponseBodyContent().bytes)

// Step 3: Save API 2 response to a file
def api2FilePath = "/Users/radhakrishnang2/Desktop/Automation/Sep2024/Commons_Automation/OutputFiles/api2response.txt"
Files.write(Paths.get(api2FilePath), responseApi2.getResponseBodyContent().bytes)

// Step 4: Extract the count from API 1
def api1CountMap = [:]
api1Response.values.each { item ->
	api1CountMap[item.value] = item.count
}

// Step 5: Extract the "Treehouse" source from API 2
def treehouseValues = api2Response.find { it.source == "Treehouse" }?.values

if (!treehouseValues) {
	KeywordUtil.markWarning("Treehouse source not found in API 2 response.")
	return
}

// Step 6: Validate counts from API 1 against counts from Treehouse in API 2
def comparisonResults = []
treehouseValues.each { item ->
	def valueCountInApi1 = api1CountMap[item.value]
	
	// Validation
	if (valueCountInApi1 != null) {
		if (item.count == valueCountInApi1) {
			comparisonResults << "Match for value '${item.value}': API 1 Count = ${valueCountInApi1}, API 2 (Treehouse) Count = ${item.count}"
		} else {
			comparisonResults << "Mismatch for value '${item.value}': API 1 Count = ${valueCountInApi1}, API 2 (Treehouse) Count = ${item.count}"
		}
	} else {
		comparisonResults << "Value '${item.value}' not found in API 1 response."
	}
}

// Step 7: Write comparison results to a file
def resultFilePath = "/Users/radhakrishnang2/Desktop/Automation/Sep2024/Commons_Automation/OutputFiles/comparison.txt"
def resultContent = comparisonResults.join("\n")
Files.write(Paths.get(resultFilePath), resultContent.bytes)

KeywordUtil.logInfo("API responses and comparison results have been written to the specified files.")

