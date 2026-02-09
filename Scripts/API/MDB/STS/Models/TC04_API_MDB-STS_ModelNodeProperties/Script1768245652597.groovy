import com.kms.katalon.core.util.KeywordUtil
import utilities.MDB_Parity

KeywordUtil.logInfo("Running TC04: Node properties parity for models for latest version - /model/:modelHandle/version/:versionString/node/:nodeHandle/properties/ endpoint")

//Leave array empty for all models or add specific models to test: 'CCDI', 'CDS'
MDB_Parity.verifyAllModelsNodePropertiesParityLatest([]) 
