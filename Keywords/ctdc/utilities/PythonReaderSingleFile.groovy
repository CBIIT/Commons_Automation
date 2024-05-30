package ctdc.utilities;

import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.util.KeywordUtil;

public class PythonReaderSingleFile {
	/**
	 * This function reads and executes a Python file.
	 * @param pythonFileName The name of the Python file to execute.
	 */
	@Keyword
	public static void readPythonSingleFile(String pythonFileName) {
		String filePath = Paths.get(RunConfiguration.getProjectDir(), "AllPythonFiles", pythonFileName).toString();
		System.out.println("This is the path till the py filename: " + filePath);
		KeywordUtil.logInfo("Executing Python file: " + pythonFileName);

		try {
			// Create a process builder
			ProcessBuilder processBuilder = new ProcessBuilder("python3", filePath);
			processBuilder.redirectErrorStream(true);

			// Start the process
			Process process = processBuilder.start();

			// Capture the output
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			// Wait for the process to finish and get the exit code
			int exitCode = process.waitFor();

			// Print the output and handle the exit code
			if (exitCode == 0) {
				KeywordUtil.logInfo("Python script executed successfully.");
				KeywordUtil.logInfo("Script output: " + output.toString());
			} else {
				KeywordUtil.markFailed("Script execution failed with exit code: " + exitCode);
				KeywordUtil.markFailed("Script output: " + output.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			KeywordUtil.markFailed("An error occurred while executing the Python script: " + e.getMessage());
		}
	}
}
