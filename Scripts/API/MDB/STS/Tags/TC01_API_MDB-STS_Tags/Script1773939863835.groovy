import utilities.MDB_Parity

println "Running TC01: Tags parity - /tags/ endpoint"

// Single call does everything: API call, validation, Neo4j query, parity checks, logging
MDB_Parity.verifyTagsParity()
