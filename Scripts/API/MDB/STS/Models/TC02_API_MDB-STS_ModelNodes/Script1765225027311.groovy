import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Running TC02: All-models nodes parity across all versions - /model/:modelHandle/version/:versionString/nodes/ endpoint")

//latestOnly = false and handlesFilter = [] will test all models across all versions 
MDB_Parity.verifyAllModelNodesParity(false, [])