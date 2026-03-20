import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

/*
 * TC14 — For each model version you select below, the keyword:
 *   1) GETs /nodes, then for each chosen node GETs /properties
 *   2) For each chosen property on that node, GETs .../property/{propHandle} and compares to Neo4j
 *
 * This exercises one property at a time (not the bulk /properties list). Full runs hit many HTTP calls;
 * use the caps to shorten smoke runs without editing MDB_Parity.groovy.
 */
// ---- Script constants ----

/** Same as TC13: latest-only rows from /models vs all historical versions. */
final boolean LATEST_ONLY = true

/**
 * Restrict which model handles run. Non-empty = only these handles (after LATEST_ONLY filter).
 * The list below is a common “main programs” subset; use [] to run all handles (takes longer time).
 */
final List<String> HANDLES_FILTER = ['CCDI', 'CCDI-DCC', 'CDS', 'C3DC', 'CTDC', 'ICDC', 'PSDC']

/**
 * Limit how many *nodes* per model version are walked (each node triggers a /properties call, then per-property GETs).
 * 0 or less = all nodes in that version.
 */
final int MAX_NODES_PER_VERSION = 5

/**
 * After /properties, at most this many *properties* are verified *per node* via GET .../property/{handle}.
 * 0 or less = every property returned by /properties for that node.
 *
 * Example smoke: MAX_NODES_PER_VERSION = 5 and MAX_PROPERTIES_PER_NODE = 10 checks at 5×10 = 50 property
 * GETs per version (plus list calls), instead of full cross-product.
 */
final int MAX_PROPERTIES_PER_NODE = 10

/**
 * When MAX_NODES_PER_VERSION > 0: false = first N nodes by sorted node handle; true = random N (see RANDOM_SEED).
 */
final boolean SAMPLE_NODES_RANDOMLY = true

/**
 * When MAX_PROPERTIES_PER_NODE > 0: false = first N properties by sorted property handle; true = random N.
 * Reuses RANDOM_SEED, mixed per node inside the keyword so different nodes don’t all pick the same property names.
 */
final boolean SAMPLE_PROPERTIES_RANDOMLY = true

/**
 * RANDOM_SEED — “starting number” for shuffles, NOT a “degree of randomness” setting.
 *
 *   • It does NOT make sampling more or less random — only *which* pseudo-random subset you get.
 *   • Same seed + same caps + same SAMPLE_* flags → same nodes/properties picked on every run (reproducible).
 *   • Different seed → different subset (still arbitrary), useful to occasionally vary smoke coverage.
 *   • Ignored unless at least one of SAMPLE_NODES_RANDOMLY / SAMPLE_PROPERTIES_RANDOMLY is true
 *     (and the matching cap is > 0).
 */
final Long RANDOM_SEED = 42L

KeywordUtil.logInfo(
		'Running TC14: Single-property GET .../property/{propHandle} vs Neo4j')

MDB_Parity.verifyAllModelSinglePropertyParity(
		LATEST_ONLY,
		HANDLES_FILTER,
		MAX_NODES_PER_VERSION,
		MAX_PROPERTIES_PER_NODE,
		SAMPLE_NODES_RANDOMLY,
		SAMPLE_PROPERTIES_RANDOMLY,
		RANDOM_SEED)
