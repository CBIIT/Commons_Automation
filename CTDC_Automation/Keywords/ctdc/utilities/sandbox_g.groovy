package ctdc.utilities

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class sandbox_g {
	@Keyword
	public stringOperations() {
		String DelimitedStr = "1-10 OF 70";
		String[] indivStr = DelimitedStr.split("-");
		System.out.println ("The individual array of strings is :"+indivStr)
		System.out.println("Second element in the array is :"+indivStr[1])
		String[] nums = indivStr[1].split();
		System.out.println ("The individual second array of strings is :"+nums)
		int iter = Integer.parseInt(nums[0]);   //Integer result = Integer.valueOf(number);  In summary, parseInt(String) returns a primitive int, whereas valueOf(String) returns a new Integer() object.
		int totalRec = Integer.parseInt(nums[2])
		System.out.println("Value of iter and totalrec are :"+iter + " and "+ totalRec)  //iter is the individual page's record count, totalRec is the sum of record count in all pages
	}
}
