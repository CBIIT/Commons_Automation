import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Running TC03: Edps by origin parity - /edps/caDSR (catalog only)")

MDB_Parity.verifyEdpsByOriginParity('caDSR')
