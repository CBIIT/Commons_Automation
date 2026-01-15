package utilities

import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonOutput
import com.kms.katalon.core.testdata.TestDataFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Parity utilities for MDB / STS API vs Neo4j.
 *
 * Goal: keep test cases very thin and reuse these methods across multiple endpoints (models, nodes, etc.).
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
	 * Overload fetchAndParse
	 */
	static Map fetchAndParse(String objectPath, Map variables) {
		ResponseObject response = API_Functions.sendRequestAndCaptureResponse(objectPath, variables)
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
	 * Helper to log raw + parsed responses.
	 */
	static void logResponse(String label, ResponseObject response, def data) {
		KeywordUtil.logInfo("Raw ${label} response:\n" + response.getResponseBodyContent())
		KeywordUtil.logInfo("Parsed ${label} response:\n" +
				JsonOutput.prettyPrint(JsonOutput.toJson(data)))
	}

	/**
	 * Get Cypher query from Data Files
	 */
	static String getCypherQuery(String testDataId, String key) {
		def td = TestDataFactory.findTestData(testDataId)
		int rows = td.getRowNumbers()
		for (int r = 1; r <= rows; r++) {
			def k = td.getValue(1, r)  // col 1 = key
			if (k == key) return td.getValue(2, r)  // col 2 = cypher
		}
		KeywordUtil.markFailedAndStop("Cypher key not found: ${key} in ${testDataId}")
		return null
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

	// ========= Normalization helpers =========

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

	/** 
	 * URLEncoder encodes space as '+', which is for query strings.
	 * Convert '+' to '%20' for URL paths.
	 */
	static String encodePath(String s) {
		return URLEncoder.encode(s, StandardCharsets.UTF_8.toString()).replace("+", "%20")
	}

	/**
	 * Normalize fields for /properties from API response
	 */
	static List<Map> normalizeNodePropertiesFromApi(List<Map> apiPropsRaw, String model, String version, String nodeHandle) {
		return (apiPropsRaw ?: []).collect { p ->
			[
				model       : model,
				version     : version,
				node        : nodeHandle,
				handle      : p.handle?.toString(),
				nanoid      : p.nanoid?.toString(),
				is_key      : toBoolOrNull(p.is_key),
				is_strict   : toBoolOrNull(p.is_strict),
				is_nullable : toBoolOrNull(p.is_nullable),
				is_required : toBoolOrNull(p.is_required),
				value_domain: p.value_domain?.toString(),
				desc        : normalizeDesc(p.desc),
			]
		}
	}

	/**
	 * Normalize fields for /properties from Neo4j response
	 */
	static List<Map> normalizeNodePropertiesFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { r ->
			[
				model       : r.model?.toString(),
				version     : r.version?.toString(),
				node        : r.node?.toString(),
				handle      : r.handle?.toString(),
				nanoid      : r.nanoid?.toString(),
				is_key      : toBoolOrNull(r.is_key),
				is_strict   : toBoolOrNull(r.is_strict),
				is_nullable : toBoolOrNull(r.is_nullable),
				is_required : toBoolOrNull(r.is_required),
				value_domain: r.value_domain?.toString(),
				desc        : normalizeDesc(r.desc),
			]
		}
	}

	/**
	 * Normalize boolean fields for /properties
	 */
	static Boolean toBoolOrNull(def v) {
		if (v == null) return null
		if (v instanceof Boolean) return (Boolean)v
		def s = v.toString().trim().toLowerCase()
		if (s in ["true", "t", "1", "yes", "y"]) return true
		if (s in ["false", "f", "0", "no", "n"]) return false
		return null
	}

	/**
	 * Normalize desc field for /properties
	 */
	static String normalizeDesc(def v) {
		if (v == null) return null
		def s = v.toString().trim()
		// optional: make "-" and "\-" equivalent
		if (s in ["\\-", "-"]) return "-"
		return s
	}



	// ========= Neo4j helper =========

	static List<Map> fetchFromNeo4j(String cypher, Map params = [:]) {
		return Neo4j_Functions.runQuery(cypher, params)
	}

	// ========= Parity comparison =========

	/**
	 * Compare two List<Map> collections by a set of key fields.
	 *
	 * keyFields is the list of fields that make up the "identity" for parity,
	 * e.g. ['handle', 'version', 'nanoid', 'is_latest_version'].
	 */

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


	// ========= Verification methods =========

	/**
	 * Parity check for /models endpoint
	 * Atomic verification method and also method to run for test case
	 */
	static void verifyModelsParity() {
		// 1) Fetch & parse
		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
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
		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModels')

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


	/**
	 * Parity check for Node objects in a specific model version - /nodes endpoint
	 * Atomic verification method
	 */
	static void verifyModelNodesParity(String handle, String version) {
		KeywordUtil.logInfo("[ModelNodes] Verifying nodes parity for handle='${handle}', version='${version}'")

		// --- 1) Fetch nodes from API ---
		def result = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetModelNodes',
				[modelHandle: handle, versionString: version]
				)

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
		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodes')

		List<Map> neo4jNodes = Neo4j_Functions.runQuery(cypher, [handle: handle, version: version])


		// 5) Compare by Node identity fields
		compareLists(
				apiNodes,
				neo4jNodes,
				[
					'model',
					'handle',
					'version',
					'nanoid'
				],
				"ModelNodes(${handle}:${version})"
				)
	}

	/**
	 * For every model/version returned by /v2/models, run nodes parity via verifyModelNodesParity(handle, version) - /nodes endpoint
	 * Wrapper method to run for test case
	 *
	 * You can control behavior via:
	 *  - latestOnly: if true, only tests entries where is_latest_version == true
	 *  - handlesFilter: if non-empty, only test models with handles in this list, otherwise tests all models
	 */
	static void verifyAllModelNodesParity(boolean latestOnly = true, List<String> handlesFilter = []) {
		KeywordUtil.logInfo("[ModelNodes-All] Starting all-models nodes parity check. latestOnly=${latestOnly}, handlesFilter=${handlesFilter}")

		// 1) Fetch all models from /v2/models
		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
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


	/**
	 * For every model returned by /models, verify version parity - /versions endpoint
	 * Atomic verification method and also method to run for test case
	 * */
	static void verifyModelVersionsParityAllHandles() {
		KeywordUtil.logInfo("[ModelVersions-All] Starting model versions parity across all handles.")

		// 1) Call /v2/models and extract unique handles
		def resultModels = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		ResponseObject respModels = resultModels.response
		def dataModels = resultModels.data

		validateListResponse(respModels, dataModels, "models", ['handle'])
		List<Map> models = (List) dataModels

		// Extract unique handles (remove duplicates)
		Set<String> handles = models.collect { it.handle }
		.findAll { it != null && it.toString().trim() != "" }
		.toSet()

		KeywordUtil.logInfo("[ModelVersions-All] Unique model handles found: ${handles.size()}")
		KeywordUtil.logInfo("[ModelVersions-All] Handles: " + handles.sort().join(", "))

		if (handles.isEmpty()) {
			KeywordUtil.markFailedAndStop("[ModelVersions-All] FAILURE: No model handles found from /models")
		}

		// 2) For each handle, call /model/{handle}/versions and compare vs Neo4j
		handles.sort().each { String handle ->
			KeywordUtil.logInfo("[ModelVersions-All] >>> Testing versions for model handle: ${handle}")

			// --- API versions call ---
			ResponseObject respVersions =
					API_Functions.sendRequestAndCaptureResponse(
					'Object Repository/API/MDB/STS/Models/GetModelVersions',
					[modelHandle: handle]
					)

			def dataVersions = API_Functions.parseResponse(respVersions)


			// Expect list of versions (strings or objects depending on API)
			assert respVersions.getStatusCode() == 200 :
			"[ModelVersions(${handle})] Expected 200, got ${respVersions.getStatusCode()}"

			assert dataVersions instanceof List :
			"[ModelVersions(${handle})] Expected a List from /versions, got: ${dataVersions?.getClass()}"

			// Normalize API versions → List<Map> like [[version:'x'], ...]
			List<Map> apiVersions = normalize((List) dataVersions, { v ->
				// handle both shapes:
				// 1) ["1.0.0","1.1.0"] (strings)
				// 2) [{"version":"1.0.0"}, ...] (objects)
				def ver = (v instanceof Map) ? v.version : v
				[ version: ver ]
			}, "API-Versions(${handle})")

			// --- Neo4j versions query ---
			String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelVersions')

			List<Map> neo4jVersions = fetchFromNeo4j(cypher, [handle: handle])

			// Compare
			compareLists(
					apiVersions,
					neo4jVersions,
					['version'],
					"ModelVersions(${handle})"
					)

			KeywordUtil.logInfo("[ModelVersions-All] <<< PASSED versions parity for ${handle}")
		}

		KeywordUtil.logInfo("[ModelVersions-All] Completed model versions parity across all handles.")
	}


	/**
	 * Verify node properties parity by model, version, and node - /properties endpoint
	 * Atomic verification method
	 **/
	static void verifyNodePropertiesParity(String modelHandle, String versionString, String nodeHandle) {
		String modelVersionHandle = "NodeProperties(${modelHandle}:${versionString}:${nodeHandle})"
		KeywordUtil.logInfo("[${modelVersionHandle}] Verifying properties parity...")

		// --- API ---
		def rProps = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetNodeProperties',
				[modelHandle: modelHandle, versionString: versionString, nodeHandle: encodePath(nodeHandle)]
				)

		int status = rProps.response.getStatusCode()

		// --- if 404 status = node has no properties (valid case) ---
		if (status == 404) {
			KeywordUtil.logInfo("[${modelVersionHandle}] API returned 404 (treating as no properties). Verifying Neo4j is also empty.")

			String cypher404 = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodePropertiesEmpty')

			List<Map> neoProps = fetchFromNeo4j(cypher404, [
				modelHandle  : modelHandle,
				versionString: versionString,
				nodeHandle   : nodeHandle
			])

			assert neoProps.isEmpty() :
			"[${modelVersionHandle}] API returned 404 but Neo4j returned ${neoProps.size()} properties"

			return
		}

		// --- Otherwise must be 200 ---
		assert status == 200 :
		"[${modelVersionHandle}] Expected 200, got ${status}"

		assert rProps.data instanceof List :
		"[${modelVersionHandle}] Expected List, got ${rProps.data?.getClass()}"

		//		List<Map> apiProps = normalize((List) rProps.data, { p ->
		//			[
		//				model  : modelHandle,
		//				version: versionString,
		//				node   : nodeHandle,
		//				handle : p.handle,
		//				nanoid : p.nanoid,
		//				//normalize boolean and desc fields
		//				is_key      : toBoolOrNull(p.is_key),
		//				is_strict	: toBoolOrNull(p.is_strict),
		//				is_nullable : toBoolOrNull(p.is_nullable),
		//				is_required : toBoolOrNull(p.is_required),
		//				desc        : normalizeDesc(p.desc)
		//			]
		//		}, "API-${modelVersionHandle}")
		//
		//		// --- Neo4j ---
		//		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodeProperties')
		//
		//		List<Map> neoPropsRaw = fetchFromNeo4j(cypher, [
		//			modelHandle  : modelHandle,
		//			versionString: versionString,
		//			nodeHandle   : nodeHandle
		//		])
		//
		//		List<Map> neoProps = normalize(neoPropsRaw, { r ->
		//			[
		//				model       : r.model,
		//				version     : r.version,
		//				node        : r.node,
		//				handle      : r.handle,
		//				nanoid      : r.nanoid,
		//				// match the API fields:
		//				is_key      : toBoolOrNull(r.is_key),
		//				is_strict	: toBoolOrNull(r.is_strict),
		//				is_nullable : toBoolOrNull(r.is_nullable),
		//				is_required : toBoolOrNull(r.is_required),
		//				desc        : normalizeDesc(r.desc)
		//			]
		//		}, "NEO-${modelVersionHandle}")

		List<Map> apiProps = normalizeNodePropertiesFromApi(
				(List<Map>) rProps.data,
				modelHandle,
				versionString,
				nodeHandle
				)

		// --- Neo4j ---
		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodeProperties')

		List<Map> neoPropsRaw = fetchFromNeo4j(cypher, [
			modelHandle  : modelHandle,
			versionString: versionString,
			nodeHandle   : nodeHandle
		])

		List<Map> neoProps = normalizeNodePropertiesFromNeo(neoPropsRaw)

		// Optional: stable sort for logs / deterministic output
		apiProps = normalize(apiProps, { it }, "API-${modelVersionHandle}")
		neoProps = normalize(neoProps, { it }, "NEO-${modelVersionHandle}")

		compareLists(
				apiProps,
				neoProps,
				[
					'model',
					'version',
					'node',
					'handle',
					'nanoid',
					'is_key',
					'is_strict',
					'is_nullable',
					'is_required',
					'value_domain',
					'desc'
				],
				modelVersionHandle
				)
	}

	/**
	 * Get latest version for model, get its nodes, and verify properties parity via verifyNodePropertiesParity(modelHandle, versionString, nodeHandle) - /properties endpoint
	 **/
	static void verifyModelNodePropertiesParityLatest(String modelHandle) {
		// latest version
		def rLatest = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetModelLatestVersion',
				[modelHandle: modelHandle]
				)
		assert rLatest.response.getStatusCode() == 200 :
		"[LatestVersion(${modelHandle})] Expected 200, got ${rLatest.response.getStatusCode()}"

		def latestData = rLatest.data
		String versionString = (latestData instanceof Map) ? latestData.version?.toString() : latestData?.toString()
		versionString = versionString?.trim()
		if (!versionString) KeywordUtil.markFailedAndStop("[LatestVersion(${modelHandle})] Missing version")

		//Get nodes for model
		def rNodes = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetModelNodes',
				[modelHandle: modelHandle, versionString: versionString]
				)
		validateListResponse(rNodes.response, rNodes.data, "nodes", ['handle'])

		List<Map> nodes = (List) rNodes.data

		nodes.each { n ->
			String nodeHandle = n.handle?.toString()
			if (!nodeHandle) return
				verifyNodePropertiesParity(modelHandle, versionString, nodeHandle)
		}
	}

	/**
	 * Get models and verify node properties parity via verifyModelNodePropertiesParityLatest(modelHandle) - /properties endpoint
	 * Wrapper method to run for test case
	 **/
	static void verifyAllModelsNodePropertiesParityLatest(List<String> handlesFilter = []) {
		KeywordUtil.logInfo("[NodeProperties-All] Starting node->properties parity for all models (latest-version). filter=${handlesFilter}")

		def rModels = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		validateListResponse(rModels.response, rModels.data, "models", ['handle'])

		List<Map> models = (List) rModels.data
		Set<String> handles = models.collect { it.handle }
		.findAll { it != null && it.toString().trim() != "" }
		.toSet()

		if (!handlesFilter.isEmpty()) {
			handles = handles.findAll { it in handlesFilter }.toSet()
		}

		handles.sort().each { String h ->
			KeywordUtil.logInfo("[NodeProperties-All] >>> Model=${h}")
			verifyModelNodePropertiesParityLatest(h)
			KeywordUtil.logInfo("[NodeProperties-All] <<< Model=${h} complete")
		}

		KeywordUtil.logInfo("[NodeProperties-All] Completed.")
	}
}