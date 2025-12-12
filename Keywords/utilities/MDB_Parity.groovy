package utilities

import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonOutput

/**
 * Generic parity utilities for MDB / STS API vs Neo4j.
 *
 * Goal: keep test cases very thin and reuse these methods
 * across multiple endpoints (models, nodes, edges, etc.).
 */
class MDB_Parity {

	// ========= Generic API helpers =========

	/**
	 * Fetch an API endpoint using a Test Object path and parse JSON.
	 * Relies on API_Functions.sendRequestAndCaptureResponse + parseResponse.
	 */
	static Map fetchAndParse(String objectPath) {
		ResponseObject response = API_Functions.sendRequestAndCaptureResponse(objectPath)
		def data = API_Functions.parseResponse(response)
		return [ response: response, data: data ]
	}

	/**
	 * Basic "list-shaped" API validation.
	 */
	static void validateListResponse(ResponseObject response,
			def data,
			String entityName,
			List<String> requiredFieldsOnFirst = []) {
		assert response.getStatusCode() == 200 :
		"Expected 200 for ${entityName}, got " + response.getStatusCode()

		assert data instanceof List :
		"Expected a List of ${entityName}, got: " + data?.getClass()

		assert data.size() > 0 :
		"Expected at least one ${entityName}, but list was empty"

		if (!requiredFieldsOnFirst.isEmpty()) {
			def first = data[0]
			requiredFieldsOnFirst.each { field ->
				assert first[field] != null :
				"First ${entityName} is missing '${field}'"
			}
		}
	}

	/**
	 * Optional helper to log raw + parsed responses.
	 */
	static void logResponse(String label, ResponseObject response, def data) {
		KeywordUtil.logInfo("Raw ${label} response:\n" + response.getResponseBodyContent())
		KeywordUtil.logInfo("Parsed ${label} response:\n" +
				JsonOutput.prettyPrint(JsonOutput.toJson(data)))
	}

	// ========= Domain rules / helpers =========

	/**
	 * Ensure at most one "latest" per handle.
	 * Can be reused for any entity that has a handle + latest flag.
	 */
	static void validateLatestVersionUniqueness(List data,
			String handleField = 'handle',
			String latestFlagField = 'is_latest_version') {
		def grouped = data.groupBy { it[handleField] }
		grouped.each { handle, items ->
			def latest = items.findAll { it[latestFlagField] == true }
			assert latest.size() <= 1 :
			"More than one latest version found for handle '${handle}': " +
			(items*.version)
		}
	}

	// ========= Normalization helper =========

	/**
	 * Produce a normalized List<Map> for parity comparison.
	 * You pass in a mapper that picks the fields you care about.
	 */
	static List<Map> normalize(List data, Closure<Map> mapper, String labelForLog = null) {
		List<Map> normalized = data.collect { mapper(it) }

		// Best-effort stable sort for nicer logs; doesn't affect parity logic
		normalized.sort { a, b ->
			a.toString() <=> b.toString()
		}

		if (labelForLog != null) {
			KeywordUtil.logInfo("[${labelForLog}] Normalized item count: ${normalized.size()}")
		}
		return normalized
	}

	// ========= Neo4j helper =========

	static List<Map> fetchFromNeo4j(String cypher, Map params = [:]) {
		return Neo4j_Functions.runQuery(cypher, params)
	}

	// ========= Generic parity comparison =========

	/**
	 * Compare two List<Map> collections by a set of key fields.
	 *
	 * keyFields is the list of fields that make up the "identity" for parity,
	 * e.g. ['handle', 'version', 'nanoid', 'is_latest_version'].
	 */
//	static void compareLists(List<Map> apiList,
//			List<Map> neo4jList,
//			List<String> keyFields,
//			String contextLabel = "Parity") {
//
//		if (neo4jList.isEmpty()) {
//			KeywordUtil.logInfo("[${contextLabel}] Neo4j returned empty list – " +
//					"skipping parity assertions (connection not wired yet).")
//			return
//		}
//
//		KeywordUtil.logInfo("[${contextLabel}] Comparing API to Neo4j...")
//		KeywordUtil.logInfo("[${contextLabel}] API count:   ${apiList.size()}")
//		KeywordUtil.logInfo("[${contextLabel}] Neo4j count: ${neo4jList.size()}")
//
////		KeywordUtil.logInfo("[${contextLabel}] Sample API entries (first 5):\n" +
////				JsonOutput.prettyPrint(JsonOutput.toJson(apiList.take(5))))
////		KeywordUtil.logInfo("[${contextLabel}] Sample Neo4j entries (first 5):\n" +
////				JsonOutput.prettyPrint(JsonOutput.toJson(neo4jList.take(5))))
//
//		assert apiList.size() == neo4jList.size() :
//		"${contextLabel}: count mismatch API=${apiList.size()} Neo4j=${neo4jList.size()}"
//
//		// Build tuple sets based on keyFields
//		Closure<String> toKey = { Map m ->
//			keyFields.collect { k -> m[k] }.join('|')
//		}
//
//		Set<String> apiSet   = apiList.collect(toKey).toSet()
//		Set<String> neo4jSet = neo4jList.collect(toKey).toSet()
//
//		KeywordUtil.logInfo("[${contextLabel}] API tuple set size:   ${apiSet.size()}")
//		KeywordUtil.logInfo("[${contextLabel}] Neo4j tuple set size: ${neo4jSet.size()}")
//
//		def onlyInApi   = apiSet - neo4jSet
//		def onlyInNeo4j = neo4jSet - apiSet
//
//		if (!onlyInApi.isEmpty()) {
//			KeywordUtil.logInfo("[${contextLabel}] Only in API:\n" + onlyInApi.join("\n"))
//		}
//		if (!onlyInNeo4j.isEmpty()) {
//			KeywordUtil.logInfo("[${contextLabel}] Only in Neo4j:\n" + onlyInNeo4j.join("\n"))
//		}
//
//		assert apiSet == neo4jSet :
//		"${contextLabel}: API and Neo4j tuple sets differ — see logs above."
//	}
	
	static void compareLists(List<Map> apiList,
		List<Map> neo4jList,
		List<String> keyFields,
		String contextLabel = "Parity") {

		KeywordUtil.logInfo("[${contextLabel}] Starting comparison of API vs Neo4j.")
		
		KeywordUtil.logInfo("[${contextLabel}] API count:   ${apiList?.size() ?: 0}")
		KeywordUtil.logInfo("[${contextLabel}] Neo4j count: ${neo4jList?.size() ?: 0}")
		
		// Sample entries – helpful for debugging mismatches
		KeywordUtil.logInfo("[${contextLabel}] Sample API entries (first 3):\n" +
		JsonOutput.prettyPrint(JsonOutput.toJson(apiList.take(3))))
		KeywordUtil.logInfo("[${contextLabel}] Sample Neo4j entries (first 3):\n" +
		JsonOutput.prettyPrint(JsonOutput.toJson(neo4jList.take(3))))
		
		// 1) Count parity
		assert apiList.size() == neo4jList.size() :
		"[${contextLabel}] Count mismatch: API=${apiList.size()} Neo4j=${neo4jList.size()}"
		
		// 2) Build tuple sets based on keyFields
		Closure<String> toKey = { Map m ->
		keyFields.collect { k -> m[k] }.join('|')
		}
		
		Set<String> apiSet   = apiList.collect(toKey).toSet()
		Set<String> neo4jSet = neo4jList.collect(toKey).toSet()
		
		KeywordUtil.logInfo("[${contextLabel}] API tuple set size:   ${apiSet.size()}")
		KeywordUtil.logInfo("[${contextLabel}] Neo4j tuple set size: ${neo4jSet.size()}")
		
		def onlyInApi   = apiSet - neo4jSet
		def onlyInNeo4j = neo4jSet - apiSet
		
		if (!onlyInApi.isEmpty()) {
		KeywordUtil.logInfo("[${contextLabel}] Only in API (${onlyInApi.size()} entries):\n" +
				   onlyInApi.join("\n"))
		}
		if (!onlyInNeo4j.isEmpty()) {
		KeywordUtil.logInfo("[${contextLabel}] Only in Neo4j (${onlyInNeo4j.size()} entries):\n" +
				   onlyInNeo4j.join("\n"))
		}
	
		// 3) Final parity assertion
		assert apiSet == neo4jSet :
		"[${contextLabel}] API and Neo4j tuple sets differ — see above for details."
		
		KeywordUtil.logInfo("[${contextLabel}] Parity check PASSED.")
		}


	// ========= Concrete wrapper: /v2/models =========

	/**
	 * Full end-to-end parity check for /v2/models.
	 * Uses the existing GetModels Test Object.
	 */
	static void verifyModelsParity() {
		// 1) Fetch & parse
		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/Model')
		ResponseObject response = result.response
		def data = result.data

		// 2) Validate basic API shape
		validateListResponse(response, data, "models", ['handle', 'version'])

		// 3) Business rule: at most one latest version per handle
		validateLatestVersionUniqueness((List) data, 'handle', 'is_latest_version')

		// 4) Optional: log full response
		logResponse("/models", response, data)

		// 5) Normalize API models for parity
		List<Map> apiModels = normalize((List) data, { model ->
			[
				handle           : model.handle,
				version          : model.version,
				nanoid           : model.nanoid,
				is_latest_version: model.is_latest_version
			]
		}, "API")

		// 6) Fetch Neo4j models
		String cypher = """
		MATCH (m:model)
		RETURN m.handle AS handle,
		       m.version AS version,
		       m.nanoid AS nanoid,
		       m.is_latest_version AS is_latest_version
		ORDER BY handle, version
		"""
		List<Map> neo4jModels = fetchFromNeo4j(cypher, [:])

		// 7) Generic comparison on key fields
		compareLists(
				apiModels,
				neo4jModels,
				[
					'handle',
					'version',
					'nanoid',
					'is_latest_version'
				],
				"ModelCatalog"
				)
	}

	 // ========= Wrapper: /v2/model/{handle}/version/{versionString}/nodes =========

    /**
     * Parity check for Node objects in a specific model version.
     *
     * Assumptions:
     *  - You create a Test Object: Object Repository/API/MDB/STS/Models/GetModelNodes
     *  - Its URL is something like:
     *      ${GlobalVariable.baseUrl}/model/${GlobalVariable.modelHandle}/version/${GlobalVariable.versionString}/nodes
     *  - The test case sets GlobalVariable.modelHandle & GlobalVariable.versionString
     *    before calling this method.
     *
     * Node schema from STS:
     *  {
     *    type: "Node",
     *    handle: string|null,
     *    version: string|null,
     *    nanoid: string,
     *    model: string   // model handle
     *  }
     */
    static void verifyModelNodesParity(String handle, String version) {
        KeywordUtil.logInfo("[ModelNodes] Verifying nodes parity for handle='${handle}', version='${version}'")

        // --- 0) Ensure environment is aligned (optional but handy) ---
        try {
            internal.GlobalVariable.modelHandle    = handle
            internal.GlobalVariable.versionString  = version
        } catch (Throwable t) {
            KeywordUtil.logInfo("[ModelNodes] Could not set GlobalVariables.modelHandle/versionString – ensure your Test Object is parameterized accordingly if needed.")
        }

        // --- 1) Fetch nodes from API ---
        def result = fetchAndParse('Object Repository/API/MDB/STS/Models/ModelNodes')
        ResponseObject response = result.response
        def data = result.data

        // 2) Basic validation
        // Required fields on first: model & nanoid (per Node schema)
        validateListResponse(response, data, "nodes", ['model', 'nanoid'])

        logResponse("/model/${handle}/version/${version}/nodes", response, data)

        // 3) Normalize API nodes
        // Using Node schema: model, handle, version, nanoid
        List<Map> apiNodes = normalize((List) data, { node ->
            [
                model  : node.model,     // model handle
                handle : node.handle,
                version: node.version,
                nanoid : node.nanoid
            ]
        }, "API-Nodes")

        // 4) Fetch Neo4j nodes
		String cypher = """
		MATCH (n:node { model: \$handle, version: \$version })
		RETURN n.model   AS model,
		       n.handle  AS handle,
		       n.version AS version,
		       n.nanoid  AS nanoid
		ORDER BY model, handle, version, nanoid
		"""
		List<Map> neo4jNodes = Neo4j_Functions.runQuery(cypher, [handle: handle, version: version])


        // 5) Compare by Node identity fields
        compareLists(
                apiNodes,
                neo4jNodes,
                ['model', 'handle', 'version', 'nanoid'],
                "ModelNodes(${handle}:${version})"
        )
    }
	
	/**
	 * For every model/version returned by /v2/models,
	 * run nodes parity via verifyModelNodesParity(handle, version).
	 *
	 * You can control behavior via:
	 *  - latestOnly: if true, only tests entries where is_latest_version == true
	 *  - handlesFilter: if non-empty, only test models with handles in this list
	 */
	static void verifyAllModelNodesParity(boolean latestOnly = true, List<String> handlesFilter = []) {
		KeywordUtil.logInfo("[ModelNodes-All] Starting all-models nodes parity check. latestOnly=${latestOnly}, handlesFilter=${handlesFilter}")

		// 1) Fetch all models from /v2/models
		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/Model')
		ResponseObject response = result.response
		def data = result.data

		// Basic sanity
		validateListResponse(response, data, "models", ['handle', 'version'])
		logResponse("/models (for nodes parity)", response, data)

		List<Map> models = (List) data

		// 2) Optionally filter by handle
		if (!handlesFilter.isEmpty()) {
			models = models.findAll { m -> m.handle in handlesFilter }
			KeywordUtil.logInfo("[ModelNodes-All] After handlesFilter, model entries: ${models.size()}")
		}

		// 3) Optionally restrict to latest versions only
		if (latestOnly) {
			models = models.findAll { m -> m.is_latest_version == true }
			KeywordUtil.logInfo("[ModelNodes-All] After latestOnly filter, model entries: ${models.size()}")
		}

		if (models.isEmpty()) {
        KeywordUtil.markFailedAndStop(
            "[ModelNodes-All] FAILURE: No model entries found after filtering. " +
            "This indicates an unexpected condition (wrong filters? empty /models?)."
	        )
	    }

		// 4) Log which (handle,version) pairs we’re about to test
		String summary = models.collect { m -> "${m.handle}:${m.version}" }.join(", ")
		KeywordUtil.logInfo("[ModelNodes-All] Will run nodes parity for these model versions:\n${summary}")

		// 5) Loop over each model/version and call the existing nodes parity helper
		models.each { m ->
			String handle  = m.handle
			String version = m.version

			KeywordUtil.logInfo("[ModelNodes-All] >>> Running nodes parity for ${handle}:${version}")
			verifyModelNodesParity(handle, version)
			KeywordUtil.logInfo("[ModelNodes-All] <<< Completed nodes parity for ${handle}:${version}")
		}

		KeywordUtil.logInfo("[ModelNodes-All] Completed nodes parity for all selected model versions.")
	}

}