import com.kms.katalon.core.util.KeywordUtil
import utilities.MDB_Parity

KeywordUtil.logInfo("Running TC05: Property terms parity for models for latest version - /model/:modelHandle/version/:versionString/node/:nodeHandle/property/:propHandle/terms/ endpoint")
  
//Leave array empty for all models or add specific models to test: 'CCDI', 'CDS'
MDB_Parity.verifyAllModelsNodePropertyTermsParityLatest(['CDS'])
