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
import com.kms.katalon.core.util.KeywordUtil
import java.nio.file.Paths


CustomKeywords.'utilities.TestRunner.RunKatalon'('TC01_C3DC_phs002599_SexAtBirth-Female.xlsx')

//CustomKeywords.'utilities.PythonReader.readFile'('StudiesTab.py')
//CustomKeywords.'utilities.PythonReader.readFile'('ParticipantsTab.py')
//CustomKeywords.'utilities.PythonReader.readFile'('DiagnosisTab.py')
CustomKeywords.'utilities.PythonReader.readFile'('TreatmentTab.py')
//CustomKeywords.'utilities.PythonReader.readFile'('TreatmentRespTab.py')
//CustomKeywords.'utilities.PythonReader.readFile'('SurvivalTab.py')
//CustomKeywords.'utilities.PythonReader.readFile'('Statbar.py')
