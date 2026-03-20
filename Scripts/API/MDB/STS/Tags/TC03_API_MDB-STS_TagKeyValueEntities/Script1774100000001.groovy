import utilities.MDB_Parity
import com.kms.katalon.core.util.KeywordUtil

KeywordUtil.logInfo('Running TC03: GET /tag/{key}/{value}/entities vs Neo4j (all entity types via partition)')

// Smoke pairs:

//Test for "type": "Property"
MDB_Parity.verifyTagKeyValueEntitiesParity('Labeled', 'Collection')

//Test for "type": "Node"
MDB_Parity.verifyTagKeyValueEntitiesParity('Category', 'administrative')

//Test for "type": "Term" -- failing currently because of bug DATATEAM-496
MDB_Parity.verifyTagKeyValueEntitiesParity('Added', '01-20-26')
MDB_Parity.verifyTagKeyValueEntitiesParity('Added', '12-23-25')

//Test for "type": "Concept"
MDB_Parity.verifyTagKeyValueEntitiesParity('mapping_source', 'alternate_name')

// Optional capped sweep (uncomment; keep maxPairs small — tag cardinality is huge):
MDB_Parity.verifyFirstNDistinctTagKeyValuePairsParity(25)
