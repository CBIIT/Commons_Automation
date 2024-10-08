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

 

// Step 1: Send Request to the First API and Get Response
ResponseObject response1 = WS.sendRequest(findTestObject('Object Repository/API/FederationNOde- Treehouse'))
println("Response 1 Status: " + response1.getStatusCode())
println("Response 1 Body: " + response1.getResponseBodyContent())

// Step 2: Send Request to the Second API and Get Response
ResponseObject response2 = WS.sendRequest(findTestObject('Object Repository/API/AL-KidsFirst - Subject counts by ethnicity'))
println("Response 2 Status: " + response2.getStatusCode())
println("Response 2 Body: " + response2.getResponseBodyContent())

// Step 3: Parse Both Responses Using JsonSlurper
JsonSlurper jsonSlurper = new JsonSlurper()
def response1Data = jsonSlurper.parseText(response1.getResponseBodyContent())
def response2Data = jsonSlurper.parseText(response2.getResponseBodyContent())

// Step 4: Extract the "KidsFirst" Entry from the Second API Response
def kidsFirstEntry = response2Data.find { it.source == 'Kids First' }

if (kidsFirstEntry == null) {
	KeywordUtil.markFailedAndStop("No entry with 'source: KidsFirst' found in the second API response.")
}

// Step 5: Compare the Properties of the "KidsFirst" Entry Against the First API Response
boolean isContained = true

// Compare 'total' and 'missing' properties
if (kidsFirstEntry.total != response1Data.total) {
	isContained = false
	KeywordUtil.markWarning("Mismatch in 'total' property. Expected: ${response1Data.total}, Found: ${kidsFirstEntry.total}")
}
if (kidsFirstEntry.missing != response1Data.missing) {
	isContained = false
	KeywordUtil.markWarning("Mismatch in 'missing' property. Expected: ${response1Data.missing}, Found: ${kidsFirstEntry.missing}")
}

// Compare 'values' array
response1Data.values.each { valueObj1 ->
	def matchingValueObj2 = kidsFirstEntry.values.find { it.value == valueObj1.value }

	if (matchingValueObj2 == null) {
		isContained = false
		KeywordUtil.markWarning("Value '${valueObj1.value}' from the first API response is not found in the 'KidsFirst' entry of the second API response.")
	} else if (matchingValueObj2.count != valueObj1.count) {
		isContained = false
		KeywordUtil.markWarning("Count mismatch for value '${valueObj1.value}'. Expected: ${valueObj1.count}, Found: ${matchingValueObj2.count}")
	}
}

// Step 6: Use Assertion to Validate the Condition
assert isContained : "The 'KidsFirst' entry in the second API response does not match the first API response."

// Step 7: Print Results
if (isContained) {
	KeywordUtil.markPassed("The 'KidsFirst' entry in the second API response matches the first API response.")
} else {
	KeywordUtil.markWarning("The 'KidsFirst' entry in the second API response is missing some data or has mismatched values compared to the first API response.")
}
