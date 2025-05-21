package utilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.poi.xssf.usermodel.XSSFCell
import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.util.KeywordUtil;
import internal.GlobalVariable

public class Utils {

	// App key used in every profile
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
		}else if(appKey.equals("C3DC")) {
			nodePath = Paths.get(usrDir, "InputFiles", "C3DC")
		}else if(appKey.equals("INS")) {
			nodePath = Paths.get(usrDir, "InputFiles", "INS")
		}else if(appKey.equals("Bento")) {
			nodePath = Paths.get(usrDir, "InputFiles", "Bento")
		}else if(appKey.equals("CRDC")) {
			nodePath = Paths.get(usrDir, "InputFiles", "CRDC")
		}else {
			KeywordUtil.markFailed("Invalid App Key or Node Path: Check getNodeFilesPath function")
		}
		return nodePath
	}

	/**
	 * This function assigns Python files path of corresponding app
	 * @param PythonFileName
	 */
	public static Path getPythonScriptPath(String pyFileName) {
		String pyPath;
		if(appKey.equals("CDS")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "CDS", pyFileName)
		}else if(appKey.equals("ICDC")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "ICDC", pyFileName)
		}else if(appKey.equals("CCDI")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "CCDI", pyFileName)
		}else if(appKey.equals("C3DC")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "C3DC", pyFileName)
		}else if(appKey.equals("INS")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "INS", pyFileName)
		}else if(appKey.equals("Bento")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "Bento", pyFileName)
		}else if(appKey.equals("Bento")) {
			pyPath = Paths.get(usrDir, "PythonFiles", "CRDC", pyFileName)
		}else {
			KeywordUtil.markFailed("Invalid App Key: Check getPythonScriptPath function")
		}
	}



	/**
	 * This function returns the Python executable path based on the JENKINS_HOME environment variable.
	 * @return String - Path to the Python executable
	 */
	public static String getPythonExecutablePath() {

		String jenkinsHome = System.getenv("JENKINS_HOME");
		String pyExecutablePath;

		if (jenkinsHome != null) {
			KeywordUtil.logInfo("Python script executed in Jenkins ...");
			pyExecutablePath = "python3";
		} else {
			KeywordUtil.logInfo("Python script executed locally ...");
			pyExecutablePath = "/Library/Frameworks/Python.framework/Versions/3.12/bin/python3";
		}

		return pyExecutablePath;
	}
}
