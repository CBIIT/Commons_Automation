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

import java.lang.Runtime
import java.lang.Process
import java.lang.ProcessBuilder
import java.lang.String
import java.lang.System
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException
import java.util.stream.Collectors
import org.python.util.PythonInterpreter

 
import java.io.FileReader
 
//WebUI.openBrowser("www.google.com")
// Define the path to your Python script
String scriptPath = "/Users/radhakrishnang2/Desktop/automationpoc/2023-03-CDS-released-2024-3-12/read_tsv.py"

// Read the Python script into a string
StringBuilder scriptContent = new StringBuilder()
BufferedReader reader = new BufferedReader(new FileReader(scriptPath))
String line
while ((line = reader.readLine()) != null) {
	scriptContent.append(line).append("\n")
}
reader.close()

// Initialize the Python interpreter
PythonInterpreter interpreter = new PythonInterpreter()

// Execute the Python script
try {
	interpreter.exec(scriptContent.toString())
	println("Python script executed successfully.")
} catch (Exception e) {
	e.printStackTrace()
	println("An error occurred while executing the Python script: " + e.getMessage())
}
println("Test case executed")
