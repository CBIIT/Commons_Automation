import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Running TC01: Edps by origin parity - /edps/CRDC")

MDB_Parity.verifyEdpsByOriginParity('CRDC')
