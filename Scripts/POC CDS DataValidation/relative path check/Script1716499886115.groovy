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

import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.Path;
import java.nio.file.Paths;
/*
// Absolute path to the file
String absoluteFilePath = "/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC/readtsv-usingpandas.py"

// Get the absolute path to the project directory
String projectDir = RunConfiguration.getProjectDir()
println("This is the proj dir path : "+projectDir)
// Construct the relative path by removing the common part
String relativeFilePath = absoluteFilePath.replace(projectDir, "")
 
// Print the relative path
println("Relative path of the file: " + relativeFilePath)
*/

//=================================================

import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration
Path pyfilePath,ipfilePath ;
String usrDir = System.getProperty("user.dir");
// Path to append to the project directory
pyfilePath = Paths.get(usrDir, "/InputFiles/PythonPOC/readtsv-usingpandas.py")
ipfilePath = Paths.get(usrDir, "/InputFiles/PythonPOC/POC-study.tsv")


String pythonFilePath = pyfilePath.toString()
println("This is the path of the input python file : "+pythonFilePath)

String inputFilePath = ipfilePath.toString()
println("This is the path of the input metadata file : "+inputFilePath)
 

 

