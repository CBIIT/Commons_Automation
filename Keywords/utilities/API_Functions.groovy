package utilities
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import groovy.json.JsonSlurper

import java.io.File
import com.kms.katalon.core.configuration.RunConfiguration
class API_Functions {

	// Function to send API request and return response


	static ResponseObject sendRequestAndCaptureResponse(String apiObjectPath) {
		// Retrieve the project directory dynamically
		String projectDir = RunConfiguration.getProjectDir()

		// Define the output directory relative to the project directory
		String outputDirectory = projectDir + "/OutputFiles/"

		// Get the test case name from Global Variable (set by the listener)
		String testCaseName = GlobalVariable.currentTestCaseName
		if (testCaseName == null || testCaseName.isEmpty()) {
			// Fallback to the execution source name if test case name is not set
			testCaseName = RunConfiguration.getExecutionSourceName()
		}
		println("This is the test case name: " + testCaseName)

		// Load the API request object
		RequestObject request = findTestObject(apiObjectPath)
		println("This is the request URL: " + request.getRestUrl())

		// Set timeouts using global configuration
		request.setHttpHeaderProperties([
			new TestObjectProperty('Connection-Timeout', com.kms.katalon.core.testobject.ConditionType.EQUALS, '10000'),
			new TestObjectProperty('Socket-Timeout', com.kms.katalon.core.testobject.ConditionType.EQUALS, '10000')
		])
		// Send the API request
		ResponseObject response = WS.sendRequest(findTestObject(apiObjectPath))
		println("Response Status: " + response.getStatusCode())
		println("Response Body: " + response.getResponseBodyContent())
		/*
		 // Extract the API name to use as part of the file name
		 String apiName = apiObjectPath.split('/').last().replaceAll(' ', '_')
		 // Combine test case name and API name for the file name
		 File responseFile = new File(outputDirectory + testCaseName + "_" + apiName + '.txt')
		 // Write the response to the file
		 responseFile.write("Response Status: " + response.getStatusCode() + "\n")
		 responseFile << "Response Body: " + response.getResponseBodyContent() + "\n"
		 */
		return response
	}

	//Overload sendRequestAndCaptureResponse
	static ResponseObject sendRequestAndCaptureResponse(String objectPath, Map variables) {
		def testObject = findTestObject(objectPath, variables)
		return WS.sendRequest(testObject)
	}


	//	// Function to parse JSON response
	//	static def parseResponse(ResponseObject response) {
	//		JsonSlurper jsonSlurper = new JsonSlurper()
	//		return jsonSlurper.parseText(response.getResponseBodyContent())
	//	}

	// Function to parse JSON response
	static def parseResponse(ResponseObject response) {
		if (response == null) {
			KeywordUtil.markWarning("Response object is null.")
			return null
		}

		String rawBody = response.getResponseBodyContent()

		try {
			JsonSlurper jsonSlurper = new JsonSlurper()
			return jsonSlurper.parseText(rawBody)
		} catch (Exception e) {
			KeywordUtil.markWarning("Failed to parse JSON response: ${e.message}")
			return null
		}
	}




	// Function to find the keyed entry in the response
	static def findEntry(def responseData, def key) {
		switch (key) {
			case 'KidsFirst':
				def kidsFirstEntry = responseData.find { it.source == 'KidsFirst' }
				if (kidsFirstEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: KidsFirst' found in the response.")
				}
				return kidsFirstEntry

			case 'StJude':
				def stJudeEntry = responseData.find { it.source == 'StJude' }
				if (stJudeEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: StJude' found in the response.")
				}
				return stJudeEntry

			case 'PCDC':
				def pcdcEntry = responseData.find { it.source == 'PCDC' }
				if (pcdcEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: PCDC' found in the response.")
				}
				return pcdcEntry

			case 'Treehouse':
				def treehouseEntry = responseData.find { it.source == 'Treehouse' }
				if (treehouseEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: Treehouse' found in the response.")
				}
				return treehouseEntry

			case 'ccdi-ecDNA':
				def ecdnaEntry = responseData.find { it.source == 'ccdi-ecDNA' }
				if (ecdnaEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: ccdi-ecDNA' found in the response.")
				}
				return ecdnaEntry

			case 'ccdi-IUSCCC':
				def iuEntry = responseData.find { it.source == 'ccdi-iusccc-pst' } // source name was changed after test cases were created
				if (iuEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: ccdi-IUSCCC' found in the response.")
				}
				return iuEntry

			case 'CCDI-DCC':
				def CcdiDccEntry = responseData.find { it.source == 'CCDI-DCC' }
				if (CcdiDccEntry == null) {
					KeywordUtil.markFailedAndStop("No entry with 'source: CCDI-DCC' found in the response.")
				}
				return CcdiDccEntry

			default:
				KeywordUtil.markFailedAndStop("ERROR - Check API_Functions.findEntry() -- No entry found for the provided key: ${key}")
		}
	}

	//	// Function to compare API responses
	//	static void compareAPIResponses(def singleNodeData, def AlextractedEntry, def key) {
	//		boolean isContained = true
	//
	//		// Compare 'total' and 'missing' properties
	//		if (AlextractedEntry.total != singleNodeData.total) {
	//			isContained = false
	//			KeywordUtil.markWarning("Mismatch in 'total' property. Expected: ${singleNodeData.total}, Found: ${AlextractedEntry.total}")
	//		}
	//		if (AlextractedEntry.missing != singleNodeData.missing) {
	//			isContained = false
	//			KeywordUtil.markWarning("Mismatch in 'missing' property. Expected: ${singleNodeData.missing}, Found: ${AlextractedEntry.missing}")
	//		}
	//
	//		// Compare 'values' array
	//		singleNodeData.values.each { singleNodeValue ->
	//			def matchingValue = AlextractedEntry.values.find { it.value == singleNodeValue.value }
	//
	//			if (matchingValue == null) {
	//				isContained = false
	//				KeywordUtil.markWarning("Value '${singleNodeValue.value}' from single node data not found in ${key} entry.")
	//			} else if (matchingValue.count != singleNodeValue.count) {
	//				isContained = false
	//				KeywordUtil.markWarning("Count mismatch for value '${singleNodeValue.value}'. Expected: ${singleNodeValue.count}, Found: ${matchingValue.count}")
	//			}
	//		}
	//
	//		assert isContained : "The ${key} entry does not match the singleNode data."
	//	}

	// Function to compare API responses
	static void compareAPIResponses(def singleNodeData, def AlextractedEntry, def key) {

		KeywordUtil.logInfo("${key} response from individual node - ${singleNodeData}")
		KeywordUtil.logInfo("${key} entry in AL - ${AlextractedEntry}")

		if (singleNodeData == null) {
			assert false : "${key} - singleNodeData is null."
		}
		if (AlextractedEntry == null) {
			assert false : "${key} - AlextractedEntry is null."
		}

		boolean isContained = true

		// Check for mismatch in 'errors' field
		boolean singleHasErrors = singleNodeData.containsKey("errors")
		boolean aggHasErrors = AlextractedEntry.containsKey("errors")

		if (singleHasErrors != aggHasErrors) {
			KeywordUtil.markWarning("${key} - Mismatch in 'errors'. Single node has errors: ${singleHasErrors}, Aggregated has errors: ${aggHasErrors}")
			assert false : "Mismatch in errors for ${key}."
		}

		// If both contain 'errors', compare the messages
		if (singleHasErrors && aggHasErrors) {
			def singleErrors = singleNodeData.errors
			def aggErrors = AlextractedEntry.errors

			if (singleErrors.size() != aggErrors.size()) {
				isContained = false
				KeywordUtil.markWarning("${key} - Error size mismatch. Single: ${singleErrors.size()}, Aggregated: ${aggErrors.size()}")
			} else {
				singleErrors.eachWithIndex { err, i ->
					if (err.message != aggErrors[i].message) {
						isContained = false
						KeywordUtil.markWarning("${key} - Mismatch in error message at index ${i}. Expected: '${err.message}', Found: '${aggErrors[i].message}'")
					}
				}
			}
			// Skip the rest of the checks if error is present and compared
			assert isContained : "The ${key} entry has mismatched error content."
			KeywordUtil.markPassed("${key} - errors match between node and AL") // ADDED
			return
		}

		// Compare 'total' and 'missing' properties
		if (AlextractedEntry.total != singleNodeData.total) {
			isContained = false
			KeywordUtil.markWarning("Mismatch in 'total' property. Expected: ${singleNodeData.total}, Found: ${AlextractedEntry.total}")
		}

		if (AlextractedEntry.missing != singleNodeData.missing) {
			isContained = false
			KeywordUtil.markWarning("Mismatch in 'missing' property. Expected: ${singleNodeData.missing}, Found: ${AlextractedEntry.missing}")
		}

		// Compare 'values' arrays (even if they are empty)
		def nodeValues = singleNodeData.values ?: []
		def aggValues = AlextractedEntry.values ?: []

		def aggMap = [:]
		aggValues.each { entry -> aggMap[entry.value] = entry.count }

		nodeValues.each { nodeEntry ->
			def expectedValue = nodeEntry.value
			def expectedCount = nodeEntry.count

			if (!aggMap.containsKey(expectedValue)) {
				isContained = false
				KeywordUtil.markWarning("Value '${expectedValue}' from single node data not found in ${key} entry.")
			} else if (aggMap[expectedValue] != expectedCount) {
				isContained = false
				KeywordUtil.markWarning("Count mismatch for value '${expectedValue}'. Expected: ${expectedCount}, Found: ${aggMap[expectedValue]}")
			}
		}

		//assert isContained : "The ${key} entry does not match the single node data."

		// ADDED: flag extras that appear only in AL (symmetry check)
		def nodeKeys = (nodeValues.collect { it?.value } as Set) ?: [] as Set
		def aggKeys  = (aggValues.collect { it?.value } as Set) ?: [] as Set
		def extrasInAL = aggKeys - nodeKeys
		if (!extrasInAL.isEmpty()) {
			isContained = false
			KeywordUtil.markWarning("Extra values present only in AL for ${key}: ${extrasInAL}")
		}

		assert isContained : "The ${key} entry does not match the single node data."
		KeywordUtil.markPassed("${key} - AL matches node") // ADDED
	}







	//********************************************************************************
	/**
	 * Send request and verify status code
	 * @param request request object, must be an instance of RequestObject
	 * @param expectedStatusCode
	 * @return a boolean to indicate whether the response status code equals the expected one
	 */
	@Keyword
	def verifyStatusCode(TestObject request, int expectedStatusCode) {
		if (request instanceof RequestObject) {
			RequestObject requestObject = (RequestObject) request
			ResponseObject response = WSBuiltInKeywords.sendRequest(requestObject)
			if (response.getStatusCode() == expectedStatusCode) {
				KeywordUtil.markPassed("Response status codes match")
			} else {
				KeywordUtil.markFailed("Response status code not match. Expected: " +
						expectedStatusCode + " - Actual: " + response.getStatusCode() )
			}
		} else {
			KeywordUtil.markFailed(request.getObjectId() + " is not a RequestObject")
		}
	}

	/**
	 * Add Header basic authorization field,
	 * this field value is Base64 encoded token from user name and password
	 * @param request object, must be an instance of RequestObject
	 * @param username username
	 * @param password password
	 * @return the original request object with basic authorization header field added
	 */
	@Keyword
	def addBasicAuthorizationProperty(TestObject request, String username, String password) {
		if (request instanceof RequestObject) {
			String authorizationValue = username + ":" + password
			authorizationValue = "Basic " + authorizationValue.bytes.encodeBase64().toString()

			// Find available basic authorization field and change its value to the new one, if any
			List<TestObjectProperty> headerProperties = request.getHttpHeaderProperties()
			boolean fieldExist = false
			for (int i = 0; i < headerProperties.size(); i++) {
				TestObjectProperty headerField = headerProperties.get(i)
				if (headerField.getName().equals('Authorization')) {
					KeywordUtil.logInfo("Found existent basic authorization field. Replacing its value.")
					headerField.setValue(authorizationValue)
					fieldExist = true
					break
				}
			}

			if (!fieldExist) {
				TestObjectProperty authorizationProperty = new TestObjectProperty("Authorization",
						ConditionType.EQUALS, authorizationValue, true)
				headerProperties.add(authorizationProperty)
			}
			KeywordUtil.markPassed("Basic authorization field has been added to request header")
		} else {
			KeywordUtil.markFailed(request.getObjectId() + "is not a RequestObject")
		}
		return request
	}
	
	// Map for Federation nodes - Add new Federation nodes here
    // Key = Folder Name in Object Repository, Value = Key in the AL JSON
    public static final Map<String, String> FEDERATION_NODES = [
        'KidsFirst'      : 'KidsFirst',
        'StJude'         : 'StJude',
        'PCDC_UChicago'  : 'PCDC',
        'Treehouse_UCSC' : 'Treehouse',
        'ecDNA'          : 'ccdi-ecDNA',
        'IUSCCC'         : 'ccdi-IUSCCC',
        'CCDI-DCC'       : 'CCDI-DCC'
    ]

    /**
     * Federation nodes vs Aggregation Layer comparison for counts - Master Method
     * @param nodeEndpoint e.g., 'Samples-PreservationMethod' (TestObject name for the Federation node endpoint being tested)
     * @param aggLayerEndpoint e.g., 'AL_SampleCounts_by_PreservationMethod' (TestObject name for the AL endpoint being tested)
     */
    public static void verifyFederationNodesVersusAggLayerCounts(String nodeEndpoint, String aggLayerEndpoint) {
        
		// Get AL response
        String aggLayerPath = "Object Repository/API/Federation/AggregationLayer/${aggLayerEndpoint}"
        ResponseObject responseAggLayer = sendRequestAndCaptureResponse(aggLayerPath)
        def dataAggLayer = parseResponse(responseAggLayer)

        // Iterate through list of nodes
        FEDERATION_NODES.each { folderName, jsonKey ->
            
            // Dynamically build the path based on the folder and the specific endpoint
            String nodePath = "Object Repository/API/Federation/FederationNodes/${folderName}/${nodeEndpoint}"
            
            KeywordUtil.logInfo("Testing Node: ${jsonKey} at path: ${nodePath}")

            try {
				// Get node response and also extract the individual node entry from the AL response
                ResponseObject resp = sendRequestAndCaptureResponse(nodePath)
                def dataNode = parseResponse(resp)
                def aggEntry = findEntry(dataAggLayer, jsonKey)

                compareAPIResponses(dataNode, aggEntry, jsonKey)
                KeywordUtil.markPassed("Verified ${jsonKey}")
            } catch (Exception e) {
                KeywordUtil.markFailed("Failed validation for ${jsonKey}: " + e.message)
            }
        }
    }
	
}