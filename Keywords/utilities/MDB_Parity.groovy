package utilities

import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonOutput
import com.kms.katalon.core.testdata.TestDataFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.Random

/**
 * Parity utilities for MDB / STS API vs Neo4j.
 *
 * Goal: keep test cases very thin and reuse these methods across multiple endpoints (models, nodes, etc.).
 */
class MDB_Parity {

	/**
	 * Handles that may appear in GET /models with no row where {@code is_latest_version} is true
	 * (known catalog/data issue). Excluded from the "every handle must have a latest row" assertion
	 * and from GET /model/{handle}/latest-version comparison in
	 * {@link #verifyModelLatestVersionAgainstModelsList}.
	 */
	private static final Set<String> MODEL_HANDLES_EXEMPT_FROM_LATEST_ROW_CHECK =
			['CRDCSubmission'] as Set

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
	 * Log response with truncated raw body and a small parsed sample (avoids huge logs / IDE crashes on large lists e.g. /tags).
	 *
	 * @param maxRawChars max characters of raw response body to print
	 * @param maxParsedItems for List data, max leading items to pretty-print
	 */
	static void logResponseSnippet(String label, ResponseObject response, def data,
			int maxRawChars = 4000, int maxParsedItems = 5) {
		String raw = response.getResponseBodyContent() ?: ""
		int rawLen = raw.length()
		String rawOut = (rawLen <= maxRawChars) ? raw :
			(raw.substring(0, maxRawChars) + "\n... [truncated raw body: showing first " + maxRawChars + " of " + rawLen + " chars]")
		KeywordUtil.logInfo("Raw ${label} response (snippet, HTTP " + response.getStatusCode() + "):\n" + rawOut)

		if (data instanceof List) {
			List list = (List) data
			int n = list.size()
			int takeN = Math.min(maxParsedItems, n)
			List head = takeN > 0 ? new ArrayList(list.subList(0, takeN)) : []
			KeywordUtil.logInfo("Parsed ${label}: list size=" + n + ", showing first " + takeN + " item(s):\n" +
					JsonOutput.prettyPrint(JsonOutput.toJson(head)))
		} else {
			String pretty = JsonOutput.prettyPrint(JsonOutput.toJson(data))
			if (pretty.length() > maxRawChars) {
				pretty = pretty.substring(0, maxRawChars) + "\n... [truncated parsed preview]"
			}
			KeywordUtil.logInfo("Parsed ${label} (snippet):\n" + pretty)
		}
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
	 * True if a {@code GET /tag/{key}/{value}/entities} list item should be compared as a {@code Property}
	 * (OpenAPI discriminator {@code type}, or legacy rows with required Property fields).
	 */
	private static boolean isTaggedPropertyApiRow(Map m) {
		if (m == null) return false
		String t = m.type?.toString()
		if (t != null && !t.trim().isEmpty()) {
			return 'Property'.equalsIgnoreCase(t.trim())
		}
		return m.value_domain != null && m.nanoid != null
	}

	/**
	 * Normalize Property-shaped rows from the tag-entities API (already filtered to Property items).
	 * Omits API discriminator {@code type} from the parity map.
	 */
	static List<Map> normalizeTaggedPropertyRowsFromApi(List<Map> rows) {
		return (rows ?: []).collect { Map p ->
			[
				model       : p.model?.toString(),
				version     : p.version?.toString(),
				handle      : p.handle?.toString(),
				nanoid      : p.nanoid?.toString(),
				is_key      : toBoolOrNull(p.is_key),
				is_strict   : toBoolOrNull(p.is_strict),
				is_nullable : toBoolOrNull(p.is_nullable),
				is_required : toBoolOrNull(p.is_required),
				value_domain: p.value_domain?.toString(),
				item_domain : p.item_domain?.toString(),
				units       : p.units?.toString(),
				pattern     : p.pattern?.toString(),
				desc        : normalizeDesc(p.desc),
			]
		}
	}

	/**
	 * From a mixed tag-entities API list, keep Property rows only and normalize (legacy helper).
	 */
	static List<Map> normalizeTaggedEntitiesFromApi(List apiRows) {
		List<Map> only = (apiRows ?: []).findAll { it instanceof Map && isTaggedPropertyApiRow((Map) it) }.collect { (Map) it }
		return normalizeTaggedPropertyRowsFromApi(only)
	}

	/**
	 * Normalize rows from Cypher key {@code verifyTagKeyValueEntities} (Property entities linked via {@code has_tag}).
	 */
	static List<Map> normalizeTaggedEntitiesFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { r ->
			[
				model       : r.model?.toString(),
				version     : r.version?.toString(),
				handle      : r.handle?.toString(),
				nanoid      : r.nanoid?.toString(),
				is_key      : toBoolOrNull(r.is_key),
				is_strict   : toBoolOrNull(r.is_strict),
				is_nullable : toBoolOrNull(r.is_nullable),
				is_required : toBoolOrNull(r.is_required),
				value_domain: r.value_domain?.toString(),
				item_domain : r.item_domain?.toString(),
				units       : r.units?.toString(),
				pattern     : r.pattern?.toString(),
				desc        : normalizeDesc(r.desc),
			]
		}
	}

	/** Tag-entities API / Neo4j rows for {@code type: Node} (same tuple as {@link #normalizeModelNodeTuple}). */
	static List<Map> normalizeTaggedNodeRowsFromApi(List<Map> rows) {
		return (rows ?: []).collect { normalizeModelNodeTuple(it as Map) }
	}

	static List<Map> normalizeTaggedNodeRowsFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { normalizeModelNodeTuple(it as Map) }
	}

	/** Tag-entities API / Neo4j rows for {@code type: Term}. */
	static List<Map> normalizeTaggedTermRowsFromApi(List<Map> rows) {
		return (rows ?: []).collect { t ->
			[
				value         : t.value?.toString(),
				origin_name   : t.origin_name?.toString(),
				handle        : t.handle?.toString(),
				origin_version: t.origin_version?.toString(),
				origin_id     : t.origin_id?.toString(),
				nanoid        : t.nanoid?.toString(),
			]
		}
	}

	static List<Map> normalizeTaggedTermRowsFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { r ->
			[
				value         : r.value?.toString(),
				origin_name   : r.origin_name?.toString(),
				handle        : r.handle?.toString(),
				origin_version: r.origin_version?.toString(),
				origin_id     : r.origin_id?.toString(),
				nanoid        : r.nanoid?.toString(),
			]
		}
	}

	/** Tag-entities API / Neo4j rows for {@code type: Concept}. */
	static List<Map> normalizeTaggedConceptRowsFromApi(List<Map> rows) {
		return (rows ?: []).collect { c ->
			[
				handle : c.handle?.toString(),
				version: c.version?.toString(),
				nanoid : c.nanoid?.toString(),
			]
		}
	}

	static List<Map> normalizeTaggedConceptRowsFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { r ->
			[
				handle : r.handle?.toString(),
				version: r.version?.toString(),
				nanoid : r.nanoid?.toString(),
			]
		}
	}

	/** Tag-entities API / Neo4j rows for {@code type: Relationship} (same fields as Node). */
	static List<Map> normalizeTaggedRelationshipRowsFromApi(List<Map> rows) {
		return (rows ?: []).collect { normalizeModelNodeTuple(it as Map) }
	}

	static List<Map> normalizeTaggedRelationshipRowsFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { normalizeModelNodeTuple(it as Map) }
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

	/**
	 * Normalize Tag.value from API or Neo4j for parity (OpenAPI allows string or boolean).
	 */
	static String normalizeTagScalar(def v) {
		if (v == null) return null
		if (v instanceof Boolean) return v.toString()
		return v.toString()
	}

	/**
	 * Normalize a Model map from GET /models or GET /model/{handle}/latest-version for API↔API comparison.
	 * Omits {@code type} (serialization discriminator). Null and blank {@code repository} normalize to null.
	 */
	static Map normalizeModelRowForLatestCompare(Map m) {
		if (m == null) return [:]
		String repoRaw = m.repository != null ? m.repository.toString().trim() : null
		String nameRaw = m.name != null ? m.name.toString().trim() : null
		return [
			handle             : m.handle?.toString()?.trim(),
			version            : m.version?.toString()?.trim(),
			nanoid             : m.nanoid?.toString()?.trim(),
			name               : (nameRaw == null || nameRaw.isEmpty()) ? null : nameRaw,
			repository         : (repoRaw == null || repoRaw.isEmpty()) ? null : repoRaw,
			is_latest_version  : toBoolOrNull(m.is_latest_version)
		]
	}

	/**
	 * Normalize a Node map from GET .../nodes, GET .../node/{nodeHandle}, or Neo4j node rows for parity ({@code model}, {@code handle}, {@code version}, {@code nanoid}).
	 */
	static Map normalizeModelNodeTuple(Map node) {
		if (node == null) return [:]
		return [
			model  : node.model?.toString(),
			handle : node.handle?.toString(),
			version: node.version?.toString(),
			nanoid : node.nanoid?.toString()
		]
	}

	/**
	 * Normalize fields for /property/{propHandle}/terms from API response
	 */
	static List<Map> normalizePropertyTermsFromApi(List<Map> apiTermsRaw,
			String model, String version, String nodeHandle, String propHandle) {
	
		return (apiTermsRaw ?: []).collect { t ->
			[
				model         : model?.toString(),
				version       : version?.toString(),
				node          : nodeHandle?.toString(),
				property      : propHandle?.toString(),
	
				// term fields
				value         : t.value?.toString(),
				handle        : t.handle?.toString(),
				origin_name   : t.origin_name?.toString(),
				origin_version: t.origin_version?.toString(), // stays null if null
				origin_id     : t.origin_id?.toString(),      // stays null if null
				nanoid        : t.nanoid?.toString()
			]
		}
	}
	
	/**
	 * Normalize fields for /property/{propHandle}/terms from Neo4j response
	 * Neo query must RETURN these aliases: model, version, node, property, value, handle, origin_name, origin_version, origin_id, nanoid
	 */
	static List<Map> normalizePropertyTermsFromNeo(List<Map> neoRows) {
		return (neoRows ?: []).collect { r ->
			[
				model         : r.model?.toString(),
				version       : r.version?.toString(),
				node          : r.node?.toString(),
				property      : r.property?.toString(),
	
				value         : r.value?.toString(),
				handle        : r.handle?.toString(),
				origin_name   : r.origin_name?.toString(),
				origin_version: r.origin_version?.toString(),
				origin_id     : r.origin_id?.toString(),
				nanoid        : r.nanoid?.toString()
			]
		}
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

		if (apiSet.size() >= 10_000) {
			KeywordUtil.logInfo("[${contextLabel}] Parity check in progress (comparing large tuple sets, ~${apiSet.size()} entries). " +
					"This step may take 10 minutes or longer with no further log output — do not stop the test; it is not necessarily hung.")
		}

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
	 * API consistency only: each model row with {@code is_latest_version == true} must match
	 * {@code GET /model/{handle}/latest-version} on comparable fields. DB truth for flags remains
	 * {@link #verifyModelsParity}.
	 * <p>Handles in {@link #MODEL_HANDLES_EXEMPT_FROM_LATEST_ROW_CHECK} skip the requirement that a
	 * latest row exists (known data exceptions).
	 */
	static void verifyModelLatestVersionAgainstModelsList() {
		KeywordUtil.logInfo('[LatestVersion] Verifying /model/{handle}/latest-version vs /models (is_latest_version rows)')

		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, 'models', ['handle', 'version'])

		validateLatestVersionUniqueness((List) data, 'handle', 'is_latest_version')

		List<Map> models = (List<Map>) data
		Set<String> allHandles = models.collect { it.handle?.toString()?.trim() }
				.findAll { it != null && !it.isEmpty() }
				.toSet()

		Map<String, Map> latestByHandle = [:]
		models.each { Map row ->
			if (row.is_latest_version != true) return
			String h = row.handle?.toString()?.trim()
			if (!h) return
			if (latestByHandle.containsKey(h)) {
				KeywordUtil.markFailedAndStop("[LatestVersion] Duplicate is_latest_version=true for handle '${h}'")
			}
			latestByHandle[h] = row
		}

		Set<String> exemptPresent = MODEL_HANDLES_EXEMPT_FROM_LATEST_ROW_CHECK.findAll { it in allHandles }.toSet()
		if (!exemptPresent.isEmpty()) {
			KeywordUtil.logInfo('[LatestVersion] Exempt from is_latest_version=true requirement (known data): ' +
					exemptPresent.sort().join(', '))
		}

		Set<String> handlesRequiringLatest = allHandles - MODEL_HANDLES_EXEMPT_FROM_LATEST_ROW_CHECK
		Set<String> missingLatest = handlesRequiringLatest - latestByHandle.keySet()
		if (!missingLatest.isEmpty()) {
			KeywordUtil.markFailedAndStop(
					"[LatestVersion] These handles have no row with is_latest_version=true: " +
					missingLatest.sort().join(', '))
		}

		latestByHandle.keySet().sort().each { String handle ->
			def rLatest = fetchAndParse(
					'Object Repository/API/MDB/STS/Models/GetModelLatestVersion',
					[modelHandle: handle]
					)
			int code = rLatest.response.getStatusCode()
			assert code == 200 :
					"[LatestVersion(${handle})] Expected HTTP 200, got ${code}"

			def body = rLatest.data
			assert body instanceof Map :
					"[LatestVersion(${handle})] Expected JSON object, got ${body?.getClass()}"

			Map expected = normalizeModelRowForLatestCompare(latestByHandle[handle] as Map)
			Map actual = normalizeModelRowForLatestCompare((Map) body)

			if (expected != actual) {
				KeywordUtil.logInfo("[LatestVersion(${handle})] Mismatch.\nExpected:\n" +
						JsonOutput.prettyPrint(JsonOutput.toJson(expected)) +
						"\nActual:\n" +
						JsonOutput.prettyPrint(JsonOutput.toJson(actual)))
				KeywordUtil.markFailedAndStop("[LatestVersion(${handle})] /latest-version does not match /models latest row")
			}
			String ver = expected.version?.toString() ?: '(unknown)'
			KeywordUtil.logInfo("[LatestVersion(${handle})] OK — matches /models latest row (version ${ver})")
		}

		KeywordUtil.logInfo("[LatestVersion] All ${latestByHandle.size()} handle(s) passed.")
	}

	/**
	 * Parity check for /tags/ endpoint vs Neo4j :tag nodes (key, value, nanoid).
	 * Cypher key {@code verifyTags} in {@code Data Files/API/MDB/CypherQueries} — if parity fails, confirm
	 * schema in Neo4j Browser: {@code CALL db.labels()}, {@code MATCH (t:tag) RETURN t LIMIT 1}.
	 */
	static void verifyTagsParity() {
		KeywordUtil.logInfo("[Tags] Verifying /tags/ parity (API vs Neo4j)")

		def result = fetchAndParse('Object Repository/API/MDB/STS/Tags/GetTags')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, "tags", ['key', 'value', 'nanoid'])

		logResponseSnippet("/tags", response, data)

		List<Map> apiTags = normalize((List) data, { tag ->
			[
				key   : tag.key?.toString(),
				value : normalizeTagScalar(tag.value),
				nanoid: tag.nanoid?.toString()
			]
		}, "API-Tags")

		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyTags')
		List<Map> neoRows = fetchFromNeo4j(cypher, [:])

		List<Map> neoTags = normalize((List) neoRows, { r ->
			[
				key   : r.key?.toString(),
				value : normalizeTagScalar(r.value),
				nanoid: r.nanoid?.toString()
			]
		}, "NEO-Tags")

		compareLists(
				apiTags,
				neoTags,
				['key', 'value', 'nanoid'],
				"Tags"
				)
	}

	/**
	 * Parity for {@code GET /tag/{key}/values} vs Neo4j distinct {@code value}s for that key.
	 * The API returns a JSON array of <strong>scalar</strong> values (e.g. strings) per OpenAPI — not {@code Tag} objects.
	 * If the API returns objects with a {@code value} field instead, that is accepted; comparison uses {@code value} only
	 * (aligned with {@code RETURN DISTINCT t.value} in Cypher key {@code verifyTagValues}).
	 * Does not exercise skip/limit (covered by the spec test framework).
	 */
	static void verifyTagValuesParity(String tagKey) {
		String keyRaw = tagKey?.toString()?.trim()
		assert keyRaw :
				"verifyTagValuesParity: tagKey must be non-empty"

		String encodedKey = encodePath(keyRaw)
		String ctx = "TagValues(${keyRaw})"
		KeywordUtil.logInfo("[${ctx}] Verifying /tag/{key}/values parity (API vs Neo4j)")

		def result = fetchAndParse(
				'Object Repository/API/MDB/STS/Tags/GetTagValues',
				[tagKey: encodedKey]
				)
		ResponseObject response = result.response
		def data = result.data

		assert response.getStatusCode() == 200 :
				"[${ctx}] Expected HTTP 200, got ${response.getStatusCode()}"

		assert data instanceof List :
				"[${ctx}] Expected a List, got: ${data?.getClass()}"

		logResponseSnippet("/tag/${keyRaw}/values", response, data)

		List raw = (List) data
		List<Map> apiRows
		if (raw.isEmpty()) {
			apiRows = []
		} else if (raw[0] instanceof Map) {
			apiRows = normalize(raw, { row ->
				Map m = (Map) row
				[ value: normalizeTagScalar(m.value) ]
			}, "API-${ctx}")
		} else {
			// Typical: JSON array of strings (or booleans / numbers)
			apiRows = normalize(raw, { item ->
				[ value: normalizeTagScalar(item) ]
			}, "API-${ctx}")
		}

		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyTagValues')
		List<Map> neoRaw = fetchFromNeo4j(cypher, [key: keyRaw])

		List<Map> neoRows = normalize((List) neoRaw, { r ->
			[ value: normalizeTagScalar((r as Map).value) ]
		}, "NEO-${ctx}")

		compareLists(
				apiRows,
				neoRows,
				['value'],
				ctx
				)
	}

	/**
	 * For each distinct tag {@code key} from {@code GET /tags}, run {@link #verifyTagValuesParity(String)}.
	 */
	static void verifyAllDistinctTagKeysValuesParity() {
		KeywordUtil.logInfo('[TagValues-All] Starting /tag/{key}/values parity for all distinct keys from /tags')

		def result = fetchAndParse('Object Repository/API/MDB/STS/Tags/GetTags')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, 'tags', ['key', 'value', 'nanoid'])
		logResponseSnippet('/tags (for distinct keys)', response, data)

		List<Map> tags = (List<Map>) data
		Set<String> keys = tags.collect { it.key?.toString()?.trim() }
				.findAll { it != null && !it.isEmpty() }
				.toSet()

		KeywordUtil.logInfo("[TagValues-All] Distinct tag keys: ${keys.size()}")

		if (keys.isEmpty()) {
			KeywordUtil.markFailedAndStop('[TagValues-All] No tag keys found from /tags')
		}

		keys.sort().each { String k ->
			KeywordUtil.logInfo("[TagValues-All] >>> key='${k}'")
			verifyTagValuesParity(k)
			KeywordUtil.logInfo("[TagValues-All] <<< key='${k}' OK")
		}

		KeywordUtil.logInfo('[TagValues-All] Completed.')
	}

	private static String getCypherKeyForTagEntityType(String entType) {
		switch (entType) {
			case 'Property': return 'verifyTagKeyValueEntities'
			case 'Node': return 'verifyTagKeyValueEntitiesNode'
			case 'Term': return 'verifyTagKeyValueEntitiesTerm'
			case 'Concept': return 'verifyTagKeyValueEntitiesConcept'
			case 'Relationship': return 'verifyTagKeyValueEntitiesRelationship'
			default:
				throw new IllegalArgumentException("Unknown tag entity type: ${entType}")
		}
	}

	private static List<String> tagEntityParityKeyFields(String entType) {
		switch (entType) {
			case 'Property': return TAGGED_ENTITY_PARITY_KEY_FIELDS
			case 'Node': return TAGGED_NODE_PARITY_KEY_FIELDS
			case 'Term': return TAGGED_TERM_PARITY_KEY_FIELDS
			case 'Concept': return TAGGED_CONCEPT_PARITY_KEY_FIELDS
			case 'Relationship': return TAGGED_RELATIONSHIP_PARITY_KEY_FIELDS
			default:
				throw new IllegalArgumentException("Unknown tag entity type: ${entType}")
		}
	}

	private static List<Map> normalizeTaggedApiRowsByType(String entType, List<Map> rows) {
		switch (entType) {
			case 'Property': return normalizeTaggedPropertyRowsFromApi(rows)
			case 'Node': return normalizeTaggedNodeRowsFromApi(rows)
			case 'Term': return normalizeTaggedTermRowsFromApi(rows)
			case 'Concept': return normalizeTaggedConceptRowsFromApi(rows)
			case 'Relationship': return normalizeTaggedRelationshipRowsFromApi(rows)
			default:
				throw new IllegalArgumentException("Unknown tag entity type: ${entType}")
		}
	}

	private static List<Map> normalizeTaggedNeoRowsByType(String entType, List<Map> rows) {
		switch (entType) {
			case 'Property': return normalizeTaggedEntitiesFromNeo(rows)
			case 'Node': return normalizeTaggedNodeRowsFromNeo(rows)
			case 'Term': return normalizeTaggedTermRowsFromNeo(rows)
			case 'Concept': return normalizeTaggedConceptRowsFromNeo(rows)
			case 'Relationship': return normalizeTaggedRelationshipRowsFromNeo(rows)
			default:
				throw new IllegalArgumentException("Unknown tag entity type: ${entType}")
		}
	}

	/**
	 * Resolves OpenAPI {@code type} for {@code /tag/.../entities} items; fails on unknown types.
	 * If {@code type} is absent, only Property-shaped rows (legacy) are accepted via {@link #isTaggedPropertyApiRow}.
	 */
	private static String canonicalTagEntityTypeForTagEntities(Map m, String ctx) {
		String t = m?.type?.toString()?.trim()
		if (t) {
			for (String k : TAG_API_ENTITY_TYPES) {
				if (k.equalsIgnoreCase(t)) {
					return k
				}
			}
			assert false :
					"[${ctx}] Unsupported tag entity type '${t}' (expected one of ${TAG_API_ENTITY_TYPES})"
			throw new IllegalStateException("unreachable: ${ctx}")
		}
		if (isTaggedPropertyApiRow(m)) {
			return 'Property'
		}
		assert false :
				"[${ctx}] Missing or ambiguous type discriminator for tag entity (expected type or Property-shaped row)"
		throw new IllegalStateException("unreachable: ${ctx}")
	}

	private static void assertAllTagEntityTypesNeoEmpty(String ctx, Map params) {
		for (String entType : TAG_API_ENTITY_TYPES) {
			String ck = getCypherKeyForTagEntityType(entType)
			String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', ck)
			List<Map> neo = fetchFromNeo4j(cypher, params)
			assert neo.isEmpty() :
					"[${ctx}] Expected all Neo4j tag-entity buckets empty for this tag, but ${ck} returned ${neo.size()} row(s)"
		}
	}

	/**
	 * Parity for {@code GET /tag/{key}/{value}/entities} vs Neo4j: partitions API items by OpenAPI {@code type}
	 * ({@code Property}, {@code Node}, {@code Term}, {@code Concept}, {@code Relationship}) and compares each bucket
	 * to a type-specific {@code has_tag} Cypher query.
	 * Does not exercise {@code skip}/{@code limit} (covered by the spec test framework).
	 * <p>
	 * HTTP 404 or empty API list is accepted only when every Neo4j bucket for this tag is empty.
	 */
	static void verifyTagKeyValueEntitiesParity(String tagKey, String tagValue) {
		String keyRaw = tagKey?.toString()?.trim()
		String valRaw = tagValue?.toString()?.trim()
		assert keyRaw :
				"verifyTagKeyValueEntitiesParity: tagKey must be non-empty"
		assert valRaw :
				"verifyTagKeyValueEntitiesParity: tagValue must be non-empty"

		String encKey = encodePath(keyRaw)
		String encVal = encodePath(valRaw)
		String ctx = "TagEntities(${keyRaw},${valRaw})"
		Map params = [key: keyRaw, value: valRaw]
		KeywordUtil.logInfo("[${ctx}] Verifying /tag/{key}/{value}/entities parity (API vs Neo4j, by entity type)")

		def result = fetchAndParse(
				'Object Repository/API/MDB/STS/Tags/GetTagKeyValueEntities',
				[tagKey: encKey, tagValue: encVal]
				)
		ResponseObject response = result.response
		def data = result.data

		int status = response.getStatusCode()
		if (status == 404) {
			assertAllTagEntityTypesNeoEmpty(ctx, params)
			KeywordUtil.logInfo("[${ctx}] API 404 and all Neo4j buckets empty — OK")
			return
		}

		assert status == 200 :
				"[${ctx}] Expected HTTP 200 or 404, got ${status}"

		assert data instanceof List :
				"[${ctx}] Expected a List, got: ${data?.getClass()}"

		logResponseSnippet("/tag/${keyRaw}/${valRaw}/entities", response, data)

		List rawList = (List) data
		if (rawList.isEmpty()) {
			assertAllTagEntityTypesNeoEmpty(ctx, params)
			KeywordUtil.logInfo("[${ctx}] API returned empty list and all Neo4j buckets empty — OK")
			return
		}

		Map<String, List<Map>> byType = [:]
		for (def item : rawList) {
			assert item instanceof Map :
					"[${ctx}] Expected each entity to be a JSON object, got ${item?.getClass()}"
			Map m = (Map) item
			String ent = canonicalTagEntityTypeForTagEntities(m, ctx)
			if (!byType.containsKey(ent)) {
				byType[ent] = []
			}
			byType[ent] << m
		}

		for (String entType : TAG_API_ENTITY_TYPES) {
			String ck = getCypherKeyForTagEntityType(entType)
			String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', ck)
			List<Map> neoRaw = fetchFromNeo4j(cypher, params)
			List<Map> apiRows = byType[entType] ?: []
			List<Map> apiNorm = normalizeTaggedApiRowsByType(entType, apiRows)
			List<Map> neoNorm = normalizeTaggedNeoRowsByType(entType, neoRaw)

			apiNorm = normalize(apiNorm, { it }, "API-${ctx}-${entType}")
			neoNorm = normalize(neoNorm, { it }, "NEO-${ctx}-${entType}")

			compareLists(
					apiNorm,
					neoNorm,
					tagEntityParityKeyFields(entType),
					"${ctx}[${entType}]"
					)
		}
	}

	/**
	 * From {@code GET /tags}, take distinct {@code (key, value)} pairs in lexicographic order, cap at {@code maxPairs},
	 * and run {@link #verifyTagKeyValueEntitiesParity(String, String)} for each. Use a small cap to avoid huge runtime
	 * (tag cardinality can be very large).
	 */
	static void verifyFirstNDistinctTagKeyValuePairsParity(int maxPairs) {
		assert maxPairs > 0 :
				"verifyFirstNDistinctTagKeyValuePairsParity: maxPairs must be > 0"

		KeywordUtil.logInfo("[TagEntities-FirstN] Distinct (key,value) parity, maxPairs=${maxPairs}")

		def result = fetchAndParse('Object Repository/API/MDB/STS/Tags/GetTags')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, 'tags', ['key', 'value', 'nanoid'])
		logResponseSnippet('/tags (for distinct pairs)', response, data)

		List<Map> tags = (List<Map>) data
		List<List<String>> pairs = tags.collect { t ->
			[
				t.key?.toString()?.trim(),
				normalizeTagScalar(t.value)
			]
		}.findAll { it[0] && it[1] != null }
				.collect { [it[0], it[1].toString()] }
				.unique()
				.sort { a, b ->
					int c = (a[0] <=> b[0])
					c != 0 ? c : (a[1] <=> b[1])
				}

		KeywordUtil.logInfo("[TagEntities-FirstN] Distinct (key,value) pairs: ${pairs.size()}")

		if (pairs.isEmpty()) {
			KeywordUtil.markFailedAndStop('[TagEntities-FirstN] No tag pairs found from /tags')
		}

		int n = Math.min(maxPairs, pairs.size())
		for (int i = 0; i < n; i++) {
			String k = pairs[i][0]
			String v = pairs[i][1]
			KeywordUtil.logInfo("[TagEntities-FirstN] >>> pair ${i + 1}/${n}: key='${k}', value='${v}'")
			verifyTagKeyValueEntitiesParity(k, v)
			KeywordUtil.logInfo("[TagEntities-FirstN] <<< pair OK")
		}

		KeywordUtil.logInfo('[TagEntities-FirstN] Completed.')
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

		// 3) Normalize API nodes (model, handle, version, nanoid)
		List<Map> apiNodes = normalize((List) data, { normalizeModelNodeTuple(it as Map) }, "API-Nodes")

		// 4) Fetch Neo4j nodes
		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodes')

		List<Map> neoRaw = Neo4j_Functions.runQuery(cypher, [handle: handle, version: version])
		List<Map> neo4jNodes = normalize((List) neoRaw, { normalizeModelNodeTuple(it as Map) }, "NEO-Nodes")

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
	 * Parity for {@code GET /model/.../version/.../node/...} (single node) vs Neo4j — same tuple as {@link #verifyModelNodesParity}.
	 */
	static void verifyModelNodeParity(String modelHandle, String versionString, String nodeHandle) {
		String ctx = "ModelSingleNode(${modelHandle}:${versionString}:${nodeHandle})"
		KeywordUtil.logInfo("[${ctx}] Verifying single-node API vs Neo4j")

		def r = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetModelNode',
				[
					modelHandle  : modelHandle,
					versionString: versionString,
					nodeHandle   : encodePath(nodeHandle)
				]
				)

		int code = r.response.getStatusCode()
		assert code == 200 : "[${ctx}] Expected HTTP 200, got ${code}"

		assert r.data instanceof Map : "[${ctx}] Expected JSON object for single node, got ${r.data?.getClass()}"

		List<Map> apiList = [normalizeModelNodeTuple((Map) r.data)]

		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNode')
		List<Map> neoRaw = fetchFromNeo4j(cypher, [
			modelHandle  : modelHandle,
			versionString: versionString,
			nodeHandle   : nodeHandle
		])
		List<Map> neoList = normalize((List) neoRaw, { normalizeModelNodeTuple(it as Map) }, "NEO-${ctx}")

		compareLists(
				apiList,
				neoList,
				['model', 'handle', 'version', 'nanoid'],
				ctx
				)
	}

	/**
	 * Limit N items from {@code items} for quicker runs. When {@code maxItems} <= 0, returns a copy of the full list.
	 * When {@code useRandom} is false, sorts by {@code sortField} for stable "first N".
	 */
	private static List<Map> limitMapSample(List<Map> items, int maxItems, String sortField, boolean useRandom, Long randomSeed,
			String logTag) {
		if (items == null || items.isEmpty()) {
			return items ?: []
		}
		List<Map> work = new ArrayList<>(items)
		if (maxItems <= 0 || work.size() <= maxItems) {
			return work
		}
		if (useRandom) {
			Random rnd = (randomSeed != null) ? new Random(randomSeed) : new Random()
			Collections.shuffle(work, rnd)
		} else {
			work.sort { a, b -> (a[sortField]?.toString() ?: '') <=> (b[sortField]?.toString() ?: '') }
		}
		List<Map> sliced = work.subList(0, maxItems)
		KeywordUtil.logInfo("[${logTag}] Sampled ${sliced.size()} of ${items.size()} (random=${useRandom})")
		return new ArrayList<>(sliced)
	}

	/**
	 * For each selected model version, list nodes via {@code /nodes}, then verify each node's single-node GET vs Neo4j.
	 * Parameters match {@link #verifyAllModelNodesParity}: {@code latestOnly}, {@code handlesFilter}.
	 * (No sampling caps — full node walk; use {@link #verifyAllModelSinglePropertyParity} constants for long-running smoke cuts.)
	 */
	static void verifyAllModelSingleNodeParity(boolean latestOnly = true, List<String> handlesFilter = []) {
		KeywordUtil.logInfo("[ModelSingleNode-All] Starting single-node parity. latestOnly=${latestOnly}, handlesFilter=${handlesFilter}")

		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, 'models', ['handle', 'version'])
		logResponse('/models (for single-node parity)', response, data)

		List<Map> models = (List<Map>) data

		if (!handlesFilter.isEmpty()) {
			models = models.findAll { m -> m.handle in handlesFilter }
			KeywordUtil.logInfo("[ModelSingleNode-All] After handlesFilter, model entries: ${models.size()}")
		}

		if (latestOnly) {
			models = models.findAll { m -> m.is_latest_version == true }
			KeywordUtil.logInfo("[ModelSingleNode-All] After latestOnly filter, model entries: ${models.size()}")
		}

		if (models.isEmpty()) {
			KeywordUtil.markFailedAndStop('[ModelSingleNode-All] No model entries after filtering.')
		}

		String summary = models.collect { m -> "${m.handle}:${m.version}" }.join(', ')
		KeywordUtil.logInfo("[ModelSingleNode-All] Model versions to test:\n${summary}")

		models.each { m ->
			String handle = m.handle?.toString()
			String version = m.version?.toString()
			KeywordUtil.logInfo("[ModelSingleNode-All] >>> ${handle}:${version}")

			def rNodes = fetchAndParse(
					'Object Repository/API/MDB/STS/Models/GetModelNodes',
					[modelHandle: handle, versionString: version]
					)
			validateListResponse(rNodes.response, rNodes.data, 'nodes', ['handle'])

			List<Map> nodes = (List<Map>) rNodes.data
			nodes.each { n ->
				String nh = n.handle?.toString()?.trim()
				if (!nh) return
				verifyModelNodeParity(handle, version, nh)
			}

			KeywordUtil.logInfo("[ModelSingleNode-All] <<< ${handle}:${version} (${nodes.size()} node(s))")
		}

		KeywordUtil.logInfo('[ModelSingleNode-All] Completed.')
	}

	/** Field list aligned with {@link #verifyNodePropertiesParity} / {@link #normalizeNodePropertiesFromApi}. */
	private static final List<String> NODE_PROPERTY_PARITY_KEY_FIELDS = [
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
	]

	/**
	 * Field list for {@code GET /tag/{key}/{value}/entities} Property rows vs Neo4j {@code verifyTagKeyValueEntities}.
	 * Omits API-only discriminator {@code type} from the parity map.
	 */
	private static final List<String> TAGGED_ENTITY_PARITY_KEY_FIELDS = [
		'model',
		'version',
		'handle',
		'nanoid',
		'is_key',
		'is_strict',
		'is_nullable',
		'is_required',
		'value_domain',
		'item_domain',
		'units',
		'pattern',
		'desc'
	]

	/** OpenAPI {@code anyOf} order for {@code GET /tag/{key}/{value}/entities} items. */
	private static final List<String> TAG_API_ENTITY_TYPES = [
		'Property',
		'Node',
		'Term',
		'Concept',
		'Relationship'
	]

	private static final List<String> TAGGED_NODE_PARITY_KEY_FIELDS = [
		'model',
		'handle',
		'version',
		'nanoid'
	]

	private static final List<String> TAGGED_TERM_PARITY_KEY_FIELDS = [
		'value',
		'origin_name',
		'handle',
		'origin_version',
		'origin_id',
		'nanoid'
	]

	private static final List<String> TAGGED_CONCEPT_PARITY_KEY_FIELDS = [
		'handle',
		'version',
		'nanoid'
	]

	private static final List<String> TAGGED_RELATIONSHIP_PARITY_KEY_FIELDS = [
		'model',
		'handle',
		'version',
		'nanoid'
	]

	/**
	 * Parity for {@code GET .../node/.../property/...} (single property) vs Neo4j — same fields as {@link #verifyNodePropertiesParity}.
	 * API 404 is valid when Neo4j returns no row for that property.
	 */
	static void verifySingleNodePropertyParity(String modelHandle, String versionString, String nodeHandle, String propHandle) {
		String ctx = "NodeSingleProperty(${modelHandle}:${versionString}:${nodeHandle}:${propHandle})"
		KeywordUtil.logInfo("[${ctx}] Verifying single-property GET vs Neo4j")

		def r = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetNodeProperty',
				[
					modelHandle  : modelHandle,
					versionString: versionString,
					nodeHandle   : encodePath(nodeHandle),
					propHandle   : encodePath(propHandle)
				]
				)

		int status = r.response.getStatusCode()

		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodeProperty')
		List<Map> neoRaw = fetchFromNeo4j(cypher, [
			modelHandle  : modelHandle,
			versionString: versionString,
			nodeHandle   : nodeHandle,
			propHandle   : propHandle
		])

		if (status == 404) {
			assert neoRaw.isEmpty() :
					"[${ctx}] API returned 404 but Neo4j returned ${neoRaw.size()} row(s)"
			KeywordUtil.logInfo("[${ctx}] API 404 and Neo4j empty — OK")
			return
		}

		assert status == 200 : "[${ctx}] Expected HTTP 200 or 404, got ${status}"

		def body = r.data
		Map propMap = null
		if (body instanceof Map) {
			propMap = (Map) body
		} else if (body instanceof List && !((List) body).isEmpty() && ((List) body)[0] instanceof Map) {
			propMap = (Map) ((List) body)[0]
		}
		assert propMap != null :
				"[${ctx}] Expected JSON object (or single-element array of object), got ${body?.getClass()}"

		List<Map> apiProps = normalizeNodePropertiesFromApi(
				[propMap],
				modelHandle,
				versionString,
				nodeHandle
				)
		List<Map> neoProps = normalizeNodePropertiesFromNeo(neoRaw)

		apiProps = normalize(apiProps, { it }, "API-${ctx}")
		neoProps = normalize(neoProps, { it }, "NEO-${ctx}")

		compareLists(
				apiProps,
				neoProps,
				NODE_PROPERTY_PARITY_KEY_FIELDS,
				ctx
				)
	}

	/**
	 * For each node with a non-empty {@code /properties} list, verify every property against
	 * {@code GET .../property/{propHandle}} vs Neo4j. Skips nodes where {@code /properties} returns 404.
	 * Uses the same {@code latestOnly} and {@code handlesFilter} meaning as {@link #verifyAllModelSingleNodeParity}.
	 * <p>
	 * Optional smoke tuning (TC14): {@code maxNodesPerVersion}, {@code maxPropertiesPerNode} (each >0 caps counts),
	 * {@code sampleRandomNodes} / {@code sampleRandomProperties} with {@code randomSeed} for reproducible random subsets.
	 */
	static void verifyAllModelSinglePropertyParity(boolean latestOnly = true, List<String> handlesFilter = [],
			int maxNodesPerVersion = 0, int maxPropertiesPerNode = 0,
			boolean sampleRandomNodes = false, boolean sampleRandomProperties = false, Long randomSeed = null) {
		KeywordUtil.logInfo("[ModelSingleProperty-All] Starting single-property parity. latestOnly=${latestOnly}, handlesFilter=${handlesFilter}, maxNodesPerVersion=${maxNodesPerVersion}, maxPropertiesPerNode=${maxPropertiesPerNode}, sampleRandomNodes=${sampleRandomNodes}, sampleRandomProperties=${sampleRandomProperties}, randomSeed=${randomSeed}")

		def result = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		ResponseObject response = result.response
		def data = result.data

		validateListResponse(response, data, 'models', ['handle', 'version'])
		logResponse('/models (for single-property parity)', response, data)

		List<Map> models = (List<Map>) data

		if (!handlesFilter.isEmpty()) {
			models = models.findAll { m -> m.handle in handlesFilter }
			KeywordUtil.logInfo("[ModelSingleProperty-All] After handlesFilter, model entries: ${models.size()}")
		}

		if (latestOnly) {
			models = models.findAll { m -> m.is_latest_version == true }
			KeywordUtil.logInfo("[ModelSingleProperty-All] After latestOnly filter, model entries: ${models.size()}")
		}

		if (models.isEmpty()) {
			KeywordUtil.markFailedAndStop('[ModelSingleProperty-All] No model entries after filtering.')
		}

		String summary = models.collect { m -> "${m.handle}:${m.version}" }.join(', ')
		KeywordUtil.logInfo("[ModelSingleProperty-All] Model versions to test:\n${summary}")

		models.each { m ->
			String handle = m.handle?.toString()
			String version = m.version?.toString()
			KeywordUtil.logInfo("[ModelSingleProperty-All] >>> ${handle}:${version}")

			def rNodes = fetchAndParse(
					'Object Repository/API/MDB/STS/Models/GetModelNodes',
					[modelHandle: handle, versionString: version]
					)
			validateListResponse(rNodes.response, rNodes.data, 'nodes', ['handle'])

			List<Map> nodes = (List<Map>) rNodes.data
			Long nodeSeedMix = (randomSeed != null) ? (Long) (randomSeed.longValue() ^ ((long) handle.hashCode() << 17) ^ ((long) version.hashCode())) : null
			List<Map> nodesToTest = limitMapSample(nodes, maxNodesPerVersion, 'handle', sampleRandomNodes,
					sampleRandomNodes ? nodeSeedMix : randomSeed,
					"ModelSingleProperty-All ${handle}:${version} nodes")
			nodesToTest.each { n ->
				String nh = n.handle?.toString()?.trim()
				if (!nh) return

				def rProps = fetchAndParse(
						'Object Repository/API/MDB/STS/Models/GetNodeProperties',
						[
							modelHandle  : handle,
							versionString: version,
							nodeHandle   : encodePath(nh)
						]
						)
				int pStat = rProps.response.getStatusCode()
				if (pStat == 404) {
					KeywordUtil.logInfo("[ModelSingleProperty-All] ${handle}:${version}:${nh} /properties 404 — skip node")
					return
				}

				assert pStat == 200 :
						"[ModelSingleProperty-All] ${handle}:${version}:${nh} Expected 200 or 404 from /properties, got ${pStat}"
				assert rProps.data instanceof List :
						"[ModelSingleProperty-All] ${handle}:${version}:${nh} Expected List from /properties"

				List<Map> props = (List<Map>) rProps.data
				Long propSeedMix = (randomSeed != null) ? (Long) (randomSeed.longValue() ^ ((long) nh.hashCode())) : null
				List<Map> propsToTest = limitMapSample(props, maxPropertiesPerNode, 'handle', sampleRandomProperties, propSeedMix,
						"ModelSingleProperty-All ${handle}:${version}:${nh} props")
				propsToTest.each { p ->
					String ph = p.handle?.toString()?.trim()
					if (!ph) return
					verifySingleNodePropertyParity(handle, version, nh, ph)
				}
			}

			KeywordUtil.logInfo("[ModelSingleProperty-All] <<< ${handle}:${version}")
		}

		KeywordUtil.logInfo('[ModelSingleProperty-All] Completed.')
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
				NODE_PROPERTY_PARITY_KEY_FIELDS,
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
	
	
	/**
	 * Verify parity for:
	 * /v2/model/{modelHandle}/version/{versionString}/node/{nodeHandle}/property/{propHandle}/terms
	 * Atomic verification method
	 */
	static void verifyNodePropertyTermsParity(String modelHandle, String versionString, String nodeHandle, String propHandle) {
		String ctx = "PropertyTerms(${modelHandle}:${versionString}:${nodeHandle}:${propHandle})"
		KeywordUtil.logInfo("[${ctx}] Verifying property->terms parity...")
	
		// --- API ---
		def rTerms = fetchAndParse(
			'Object Repository/API/MDB/STS/Models/GetNodePropertyTerms',
			[
				modelHandle  : modelHandle,
				versionString: versionString,
				nodeHandle   : encodePath(nodeHandle),
				propHandle   : encodePath(propHandle)
			]
		)
	
		int status = rTerms.response.getStatusCode()
	
		// If you discover this endpoint returns 404 when no terms exist, keep this pattern.
		// If it returns 200 with [], this won't trigger and will still work.
		if (status == 404) {
			KeywordUtil.logInfo("[${ctx}] API returned 404 (treating as no terms). Verifying Neo4j is also empty.")
	
			String cypherEmpty = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodePropertyTermsEmpty')
	
			List<Map> neoEmpty = fetchFromNeo4j(cypherEmpty, [
				modelHandle  : modelHandle,
				versionString: versionString,
				nodeHandle   : nodeHandle,
				propHandle   : propHandle
			])
	
			assert neoEmpty.isEmpty() :
				"[${ctx}] API returned 404 but Neo4j returned ${neoEmpty.size()} terms"
	
			return
		}
	
		assert status == 200 : "[${ctx}] Expected 200, got ${status}"
		assert rTerms.data instanceof List : "[${ctx}] Expected List, got ${rTerms.data?.getClass()}"
	
		List<Map> apiTerms = normalizePropertyTermsFromApi(
			(List<Map>) rTerms.data,
			modelHandle, versionString, nodeHandle, propHandle
		)
	
		// --- Neo4j ---
		String cypher = getCypherQuery('Data Files/API/MDB/CypherQueries', 'verifyModelNodePropertyTerms')
	
		List<Map> neoRaw = fetchFromNeo4j(cypher, [
			modelHandle  : modelHandle,
			versionString: versionString,
			nodeHandle   : nodeHandle,
			propHandle   : propHandle
		])
	
		List<Map> neoTerms = normalizePropertyTermsFromNeo(neoRaw)
	
		// Keep logs/sorting consistent
		apiTerms = normalize(apiTerms, { it }, "API-${ctx}")
		neoTerms = normalize(neoTerms, { it }, "NEO-${ctx}")
	
		// Compare including the fields you want to assert parity on
		compareLists(
			apiTerms,
			neoTerms,
			[
				'model','version','node','property',
				'nanoid','value','handle',
				'origin_name','origin_version','origin_id'
			],
			ctx
		)
	}
	
	/**
	 * For a given modelHandle:
	 * - get latest version
	 * - get nodes
	 * - for each node, get properties
	 * - for each property, verify terms parity
	 */
	static void verifyModelNodePropertyTermsParityLatest(String modelHandle) {
		String ctx = "PropertyTerms-Latest(${modelHandle})"
		KeywordUtil.logInfo("[${ctx}] Starting...")
	
		// --- latest version ---
		def rLatest = fetchAndParse(
			'Object Repository/API/MDB/STS/Models/GetModelLatestVersion',
			[modelHandle: modelHandle]
		)
		assert rLatest.response.getStatusCode() == 200 :
			"[${ctx}] Expected 200 from latest version, got ${rLatest.response.getStatusCode()}"
	
		def latestData = rLatest.data
		String versionString = (latestData instanceof Map) ? latestData.version?.toString() : latestData?.toString()
		versionString = versionString?.trim()
		if (!versionString) KeywordUtil.markFailedAndStop("[${ctx}] Missing latest version")
	
		KeywordUtil.logInfo("[${ctx}] Latest version = ${versionString}")
	
		// --- nodes ---
		def rNodes = fetchAndParse(
			'Object Repository/API/MDB/STS/Models/GetModelNodes',
			[modelHandle: modelHandle, versionString: versionString]
		)
	
		// Some models might legitimately have 0 nodes; if that can happen, replace validateListResponse with a softer check.
		validateListResponse(rNodes.response, rNodes.data, "nodes", ['handle'])
		List<Map> nodes = (List) rNodes.data
	
		nodes.each { n ->
			String nodeHandle = n.handle?.toString()
			if (!nodeHandle) return
	
			String nodeCtx = "${ctx}:${nodeHandle}"
			KeywordUtil.logInfo("[${nodeCtx}] Fetching properties...")
	
			// --- properties for node ---
			def rProps = fetchAndParse(
				'Object Repository/API/MDB/STS/Models/GetNodeProperties',
				[
					modelHandle  : modelHandle,
					versionString: versionString,
					nodeHandle   : encodePath(nodeHandle)
				]
			)
	
			int pStatus = rProps.response.getStatusCode()
	
			// Valid: node has no properties
			if (pStatus == 404) {
				KeywordUtil.logInfo("[${nodeCtx}] Properties API returned 404 (no properties). Skipping terms.")
				return
			}
	
			assert pStatus == 200 :
				"[${nodeCtx}] Expected 200 or 404 from properties, got ${pStatus}"
	
			assert rProps.data instanceof List :
				"[${nodeCtx}] Expected List from properties, got ${rProps.data?.getClass()}"
	
			List<Map> props = (List<Map>) rProps.data
	
			props.each { p ->
				String propHandle = p.handle?.toString()
				if (!propHandle) return
	
				// --- terms parity (atomic) ---
				verifyNodePropertyTermsParity(modelHandle, versionString, nodeHandle, propHandle)
			}
		}
	
		KeywordUtil.logInfo("[${ctx}] Completed.")
	}
	
	/**
	 * Get all models and run property->terms parity for each model's latest version.
	 * Wrapper method to run for test case
	 */
	static void verifyAllModelsNodePropertyTermsParityLatest(List<String> handlesFilter = []) {
		KeywordUtil.logInfo("[PropertyTerms-All] Starting model->node->property->terms parity for all models (latest). filter=${handlesFilter}")
	
		def rModels = fetchAndParse('Object Repository/API/MDB/STS/Models/GetModels')
		validateListResponse(rModels.response, rModels.data, "models", ['handle'])
	
		List<Map> models = (List) rModels.data
	
		// Only latest entries (since /models returns versions too)
		models = models.findAll { it.is_latest_version == true }
	
		Set<String> handles = models.collect { it.handle }
			.findAll { it != null && it.toString().trim() != "" }
			.toSet()
	
		if (!handlesFilter.isEmpty()) {
			handles = handles.findAll { it in handlesFilter }.toSet()
		}
	
		if (handles.isEmpty()) {
			KeywordUtil.markFailedAndStop("[PropertyTerms-All] No model handles found after filtering.")
		}
	
		handles.sort().each { String h ->
			KeywordUtil.logInfo("[PropertyTerms-All] >>> Model=${h}")
			verifyModelNodePropertyTermsParityLatest(h)
			KeywordUtil.logInfo("[PropertyTerms-All] <<< Model=${h} complete")
			Neo4j_Functions.closeDriver()
		}
	
		KeywordUtil.logInfo("[PropertyTerms-All] Completed.")
	}
	
	
	
}