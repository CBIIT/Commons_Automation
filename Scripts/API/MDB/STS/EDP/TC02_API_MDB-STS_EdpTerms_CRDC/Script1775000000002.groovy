import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo("Running TC02: CRDC EDP terms parity - /edp/CRDC/{id}/{ver}/terms")

MDB_Parity.verifyCrdcEdpTermsParity()
