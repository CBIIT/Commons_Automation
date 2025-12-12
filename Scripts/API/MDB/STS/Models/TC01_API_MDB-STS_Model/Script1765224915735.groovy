import utilities.MDB_Parity

println "Running test case: TC01_API_MDB-STS_GetModels in API folder"

// Single call does everything: API call, validation, Neo4j query, parity checks, logging
MDB_Parity.verifyModelsParity()
