import com.kms.katalon.core.util.KeywordUtil
import utilities.MDB_Parity

KeywordUtil.logInfo("Running TC05: Property terms parity for models for latest version - /model/:modelHandle/version/:versionString/node/:nodeHandle/property/:propHandle/terms/ endpoint")

//MDB_Parity.verifyNodePropertyTermsParity(
//	"CDS",
//	"11.0.2",
//	"file",
//	"experimental_strategy_and_data_subtypes"
//  )
  

MDB_Parity.verifyAllModelsNodePropertyTermsParityLatest([])