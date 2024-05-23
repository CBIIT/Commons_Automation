package ctdc.utilities
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class filecomparator {
	
	
	/*	
	@Keyword
		public void compareFiles(String file1Path, String file2Path, String outputFilePath) {
			List<String> differences = new ArrayList<>();
	
			try(BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
				BufferedReader reader2 = new BufferedReader(new FileReader(file2Path))){
	
				String line1, line2;
				int lineNumber = 1;
				while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
					if (!line1.equals(line2)) {
						differences.add("Difference found at line " + lineNumber + ":\n"
										+ "File 1: " + line1 + "\n"
										+ "File 2: " + line2 + "\n\n");
					}
					lineNumber++;
				}
	
				// Check for any remaining lines in either file
				if (line1 != null) {
					differences.add("File 1 has more lines than File 2.\n");
				} else if (line2 != null) {
					differences.add("File 2 has more lines than File 1.\n");
				} else {
					differences.add("Files have the same number of lines and content.\n");
				}
	
			}catch (IOException e) {
				e.printStackTrace();
			}
	
			// Print differences to console
			for (String difference : differences) {
				System.out.print(difference);
			}
	
			// Write differences to output file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
				for (String difference : differences) {
					writer.write(difference);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
	*/
	
}

