import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

/*
 * TC13 — For each selected model version: GET /nodes, then for every node GET /node/{nodeHandle}
 * and compare to Neo4j (full walk, no caps). For capped / random smoke sampling on property GETs, see TC14.
 */
// ---- Script constants ----

/**
 * If true: only GET /models rows with is_latest_version == true (usually one per handle).
 * If false: all versions from /models (slower, broader coverage).
 */
final boolean LATEST_ONLY = true

/**
 * After /models: [] = all handles; non-empty = only these handles (e.g. focus one program).
 */
final List<String> HANDLES_FILTER = ['CCDI', 'CCDI-DCC', 'CDS', 'C3DC', 'CTDC', 'ICDC', 'PSDC']

KeywordUtil.logInfo(
		'Running TC13: Single-node GET /model/.../node/{nodeHandle} vs Neo4j for each node')

MDB_Parity.verifyAllModelSingleNodeParity(LATEST_ONLY, HANDLES_FILTER)