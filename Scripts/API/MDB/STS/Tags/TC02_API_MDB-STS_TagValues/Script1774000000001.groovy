import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo('Running TC02: GET /tag/{key}/values vs Neo4j for each distinct key from /tags')

MDB_Parity.verifyAllDistinctTagKeysValuesParity()
