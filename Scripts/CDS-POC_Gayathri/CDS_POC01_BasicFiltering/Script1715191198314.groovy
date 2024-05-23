//'CDS_POC_Participant'

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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.firefox.FirefoxBinary as FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver as FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions as FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testdata.TestDataFactory
 

import com.kms.katalon.core.testdata.TestDataFactory

 


// Specify your filter criteria
def filterColumn = phs_accession
def filterValue = 'phs002504'

// Read data from the source table
def sourceTable = TestDataFactory.findTestData('Data Files/CDS-POC_Gayathri/CDS_POC_Participant')
def sourceData = sourceTable.getAllData()

// Initialize an empty list to store filtered records
def filteredRecords = []

// Iterate over each row in sourceData
sourceData.each { row ->
	// Extract the value of the specified column
	def columnValue = row.getValue(filterColumn)
	
	// Check if the column value matches the filter value
	if (columnValue == filterValue) {
		// If matched, add the row to filteredRecords
		filteredRecords.add(row)
	}
}

// Write filtered records to the filteredResults data table
def filteredResults = TestDataFactory.findTestData('filteredResults')

// Clear existing data in filteredResults
filteredResults.clearData()

// Write filtered records to filteredResults
for (def record : filteredRecords) {
	def newRow = [:]
	record.each { key, value ->
		newRow[key] = value
	}
	filteredResults.addRow(newRow)
}
