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

public class APIValidationFunctions {

//	/**
//	 * Validates that the response data contains only the allowed values.
//	 *
//	 * @param responseData  The response data to validate (a List of Maps or JSON).
//	 * @param allowedValues The allowed enum values (a List of Strings).
//	 */
//	static void validateAllowedEnums(List<Map> responseData, List<String> allowedValues) {
//		boolean hasErrors = false
//
//		responseData.each { sourceData ->
//			String sourceName = sourceData.source
//			List<Map> valuesList = sourceData.values
//
//			valuesList.each { valueData ->
//				String value = valueData.value
//
//				if (!allowedValues.contains(value)) {
//					// Log and mark unexpected values as failures
//					KeywordUtil.markFailed("❌ Unexpected value '${value}' found in response for source '${sourceName}'")
//					hasErrors = true
//				}
//				else {
//					KeywordUtil.logInfo("Source '${sourceName}' - Allowed value '${value}' found in response")
//				}
//			}
//		}
//
//		// Log success if no errors
//		if (!hasErrors) {
//			KeywordUtil.logInfo("✓ All 'value' fields contain only allowed enums.")
//		}
//	}
	
	/**
	 * Validates that the response data contains only the allowed values.
	 *
	 * @param responseData  The response data to validate (a List of Maps or JSON).
	 * @param allowedValues The allowed enum values (a List of Strings).
	 */
	static void validateAllowedEnums(List<Map> responseData, List<String> allowedValues) {
    boolean hasErrors = false

    responseData.each { sourceData ->
        String sourceName = sourceData.source
        List<Map> valuesList = sourceData.values

        valuesList.each { valueData ->
            String rawValue = valueData.value
            List<String> valuesToCheck = rawValue.split(',').collect { it.trim() }

            List<String> invalidValues = []
            List<String> validValues = []

            valuesToCheck.each { value ->
                if (!allowedValues.contains(value)) {
                    invalidValues << value
                    hasErrors = true
                } else {
                    validValues << value
                }
            }

            if (!invalidValues.isEmpty()) {
                KeywordUtil.markFailed("Unexpected value '${invalidValues.join(',')}' found in response for source '${sourceName}'")
            }

            if (!validValues.isEmpty()) {
                KeywordUtil.logInfo("✓ Source '${sourceName}' - Allowed value found in response: '${validValues.join(',')}'")
            }
	        }
	    }
	
	    if (!hasErrors) {
	        KeywordUtil.logInfo("✓ All 'value' fields contain only allowed enums.")
	    }
	}

	
	
	// Overload with Set<String> (semicolon-separated values for Sample anatomical_sites)
	static void validateAllowedEnums(List<Map> responseData, Set<String> allowedValuesSet) {
	    boolean hasErrors = false
	
	    responseData.each { sourceData ->
	        String sourceName = sourceData.source
	        List<Map> valuesList = sourceData.values
	
	        valuesList.each { valueData ->
	            String rawValue = valueData.value
	            List<String> valuesToCheck = rawValue.split(';').collect { it.trim() }
	
	            List<String> invalidValues = []
	            List<String> validValues = []
	
	            valuesToCheck.each { value ->
	                if (!allowedValuesSet.contains(value)) {
	                    invalidValues << value
	                    hasErrors = true
	                } else {
	                    validValues << value
	                }
	            }
	
	            if (!invalidValues.isEmpty()) {
	                KeywordUtil.markFailed("❌ Unexpected value(s) '${invalidValues.join(';')}' found in response for source '${sourceName}'")
	            }
	
	            if (!validValues.isEmpty()) {
	                KeywordUtil.logInfo("✓ Source '${sourceName}' - Allowed value(s) found in response: '${validValues.join(';')}'")
	            }
	        }
	    }
	
	    if (!hasErrors) {
	        KeywordUtil.logInfo("✓ All 'value' fields contain only allowed enums.")
	    }
	}

}