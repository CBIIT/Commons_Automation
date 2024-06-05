import com.kms.katalon.core.util.KeywordUtil
import java.nio.file.Paths

// Define paths
String inputDirectory = "/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/InputFiles/PythonPOC"
String outputDirectory = "/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/OutputFiles"
String outputExcelPath = Paths.get(outputDirectory, "multi-file-outputxl-usingpandas.xlsx").toString()
String pythonScriptPath = Paths.get("/Users/radhakrishnang2/Desktop/Automation/AprilBranch/Commons_Automation/AllPythonFiles/process_multiple_tsv.py").toString()

// Define SQL query for filtering
String sqlQuery = "SELECT * FROM df_stdy WHERE phs_accession='phs002504'"

// Define paths to TSV files as arraylist
List<String> tsvFiles = [
	Paths.get(inputDirectory, "POC-study.tsv").toString(),
	Paths.get(inputDirectory, "POC-program.tsv").toString(),
	Paths.get(inputDirectory, "POC-participant.tsv").toString(),
	Paths.get(inputDirectory, "POC-diagnosis.tsv").toString(),
	Paths.get(inputDirectory, "POC-sample.tsv").toString(),
	Paths.get(inputDirectory, "POC-genomic_info.tsv").toString(),
	Paths.get(inputDirectory, "POC-file.tsv").toString()
]

println("Input Directory: " + inputDirectory)
println("Output Directory: " + outputDirectory)
println("Output Excel Path: " + outputExcelPath)
println("Python Script Path: " + pythonScriptPath)
println("SQL Query: " + sqlQuery)

tsvFiles.each { println("TSV File Path: " + it) }

// Building the command and executing it using process builder
try {
	List<String> command = new ArrayList<>()
	command.add("python3")
	command.add(pythonScriptPath)
	command.add(outputExcelPath)
	command.add(sqlQuery)
	command.addAll(tsvFiles)

	ProcessBuilder processBuilder = new ProcessBuilder(command)
	processBuilder.redirectErrorStream(true)

	Process process = processBuilder.start()

	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
	StringBuilder output = new StringBuilder()
	String line
	while ((line = reader.readLine()) != null) {
		output.append(line).append("\n")
	}

	int exitCode = process.waitFor()

	if (exitCode == 0) {
		KeywordUtil.logInfo("Python script executed successfully.")
		KeywordUtil.logInfo("Script output: " + output.toString().trim())
	} else {
		KeywordUtil.markFailed("Script execution failed with exit code: " + exitCode)
		KeywordUtil.markFailed("Script output: " + output.toString().trim())
	}

} catch (Exception e) {
	KeywordUtil.markFailed("Failed to execute Python script: " + e.getMessage())
}
