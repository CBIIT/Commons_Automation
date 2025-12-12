import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

// Pick a model + version you know exists (from /v2/models)
//String handle  = "CDS"
//String version = "10.0.0" 
//
//KeywordUtil.logInfo("Running nodes parity test for model='${handle}', version='${version}'")
//
//MDB_Parity.verifyModelNodesParity(handle, version)



KeywordUtil.logInfo("Running TC02: All-models nodes parity across all versions")

// latestOnly = false and handlesFilter = [] will test all versions 
MDB_Parity.verifyAllModelNodesParity(false, [])