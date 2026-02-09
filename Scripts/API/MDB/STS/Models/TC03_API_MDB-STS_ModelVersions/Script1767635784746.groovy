import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Running TC03: model versions parity across all model handles - /model/:modelHandle/versions/ endpoint")

MDB_Parity.verifyModelVersionsParityAllHandles()