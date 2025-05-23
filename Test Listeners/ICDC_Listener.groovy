import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

/*//commenting the listener to not interfere with other projects that pose a missing global variable issue when run in jenkins
class ICDC_Listener {
	String renameManifestFile = GlobalVariable.ManifestFlag
	
//	 * Executes before every test case starts.
//	 * @param testCaseContext related information of the executed test case.
//	 

	@BeforeTestCase
	def getTestScriptName(TestCaseContext testCaseContext) {
    String TestCaseId = testCaseContext.getTestCaseId()
	// The above results in -----   This is the test case name: Test Cases/Canine_TestCases/Manifest/TC02_Canine_MFST_SamplePatho-TCellLymphoma
	int start = TestCaseId.indexOf("Manifest/")+"Manifest/".length();
	GlobalVariable.G_currentTCName =TestCaseId.substring(start)
	System.out.println("This is the test case name: "+GlobalVariable.G_currentTCName)
	 
	 
  }
	
	// @param testCaseContext related information of the executed test case.
	 
	@AfterTestCase
	def renameManifestFile(TestCaseContext testCaseContext) {
		//if(renameManifestFile==Y){
			//rename manifest
		//} 
		
	}
	*/
