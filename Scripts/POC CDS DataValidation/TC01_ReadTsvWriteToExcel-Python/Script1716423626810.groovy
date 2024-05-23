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
 

import java.io.BufferedReader
import java.io.InputStreamReader

 
import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration



Path pyfilePath
Path ipfilePath 
String usrDir = System.getProperty("user.dir");
// Path to append to the project directory
pyfilePath = Paths.get(usrDir, "/InputFiles/PythonPOC/readtsv-usingpandas.py")
ipfilePath = Paths.get(usrDir, "/InputFiles/PythonPOC/POC-study.tsv")


String pythonFilePath = pyfilePath.toString()
println("This is the path of the input python file : "+pythonFilePath)

String inputFilePath = ipfilePath.toString()
println("This is the path of the input metadata file : "+inputFilePath)
 



try {
	// Create a process builder
	ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonFilePath)
	processBuilder.redirectErrorStream(true)

	// Start the process
	Process process = processBuilder.start()

	// Capture the output
	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
	StringBuilder output = new StringBuilder()
	String line
	while ((line = reader.readLine()) != null) {
		output.append(line).append("\n")
	}

	// Wait for the process to finish and get the exit code
	int exitCode = process.waitFor()

	// Print the output and handle the exit code
	if (exitCode == 0) {
		println("Python script executed successfully.")
		println("Script output:\n" + output.toString())
	} else {
		println("Script execution failed with exit code: " + exitCode)
		println("Script output:\n" + output.toString())
	}

} catch (Exception e) {
	e.printStackTrace()
	println("An error occurred while executing the Python script: " + e.getMessage())
}
