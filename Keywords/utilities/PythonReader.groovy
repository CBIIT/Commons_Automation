package utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.xssf.usermodel.XSSFCell

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.util.KeywordUtil;

import internal.GlobalVariable

public class PythonReader {

	public static String appKey = GlobalVariable.AppKey;
	public static String usrDir = RunConfiguration.getProjectDir();


	/**
	 * This assigns the node files path based on the app key
	 */
	@Keyword
	public static Path getNodeFilesPath() {

		Path nodePath

		if(appKey.equals("CDS")) {
			nodePath = Paths.get(usrDir, "InputFiles", "CDS")
		}else if(appKey.equals("ICDC")) {
			nodePath = Paths.get(usrDir, "InputFiles", "ICDC")
		}else if(appKey.equals("CCDI")) {
			nodePath = Paths.get(usrDir, "InputFiles", "CCDI")
		}else {
			KeywordUtil.markFailed("Invalid Node Path: Check getNodeFilesPath function")
		}
		return nodePath
	}

	/**
	 * This function assigns Python files path of corresponding app
	 * @param PythonFileName
	 */
	public static Path getPythonPath(String pyFileName) {
		String pyPath;
		if(appKey.equals("CDS")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "CDS", pyFileName)
		}else if(appKey.equals("ICDC")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "ICDC", pyFileName)
		}else if(appKey.equals("CCDI")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "CCDI", pyFileName)
		}else {
			KeywordUtil.markFailed("Invalid App Key: Check getPythonPath function")
		}
	}


	/**
	 * This function reads and executes a Python file.
	 * @param pythonFileName The name of the Python file to execute.
	 */
	@Keyword
	public static void readFile(String pythonFileName) {

		//String binPath = "/Library/Frameworks/Python.framework/Versions/3.12/bin/python3";
		String binPath = "python3";
		String inputExcelPath = GlobalVariable.InputExcel;
		String outputFilePath = Paths.get(usrDir, "OutputFiles")
		String nodeFilespath = getNodeFilesPath()
		String pyPath = getPythonPath(pythonFileName);

		try {
			// Create a process builder
			ProcessBuilder processBuilder = new ProcessBuilder(binPath, pyPath, inputExcelPath, outputFilePath, nodeFilespath);
			processBuilder.redirectErrorStream(true);
			KeywordUtil.logInfo("Executing Python file: " + pythonFileName);

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