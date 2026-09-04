package utilities

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil


import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session
import org.neo4j.driver.Result
import org.neo4j.driver.Record
import org.neo4j.driver.Value

class Neo4j_Functions {

	// Singleton driver instance
	private static Driver driver

/** Maximum total query attempts (initial try + retries) on transient errors. */
	private static final int MAX_QUERY_ATTEMPTS = 3

	/** Pause before each retry so NLB/Neo4j can accept a fresh bolt connection. */
	private static final long RETRY_DELAY_MS = 2000L

	/**
	 * Ensure a Neo4j driver is initialized (lazy init).
	 */
	private static synchronized Driver getDriver() {
		if (driver == null) {
			String uri      = GlobalVariable.neo4jUri
			String user     = GlobalVariable.neo4jUser
			String password = GlobalVariable.neo4jPassword

			KeywordUtil.logInfo("[Neo4j_Functions] Initializing Neo4j driver: ${uri} / user=${user}")

			driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))
		}
		return driver
	}

	private static boolean isRetryableConnectionError(Throwable t) {
		while (t != null) {
			String msg = t.message?.toLowerCase() ?: ''
			if (msg.contains('no routing server') ||
					msg.contains('no longer available') ||
					msg.contains('could not perform discovery') ||
					msg.contains('service unavailable') ||
					msg.contains('connection reset') ||
					msg.contains('connection refused') ||
					msg.contains('connection to the database failed') ||
					msg.contains('unable to connect') ||
					msg.contains('connection closed') ||
					msg.contains('broken pipe') ||
					msg.contains('timed out') ||
					msg.contains('timeout')) {
				return true
			}
			t = t.cause
		}
		return false
	}

	private static void sleepBeforeRetry(int attempt) {
		try {
			Thread.sleep(RETRY_DELAY_MS)
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt()
			KeywordUtil.logInfo(
					"[Neo4j_Functions] Retry sleep interrupted before attempt ${attempt}; continuing.")
		}
	}

	/**
	 * Run Cypher once; throws on failure (no retry).
	 */
	private static List<Map> executeQuery(String cypher, Map params) {
		List<Map> rows = []
		Session session = null
		try {
			session = getDriver().session()

			Result result
			if (params && !params.isEmpty()) {
				result = session.run(cypher, params)
			} else {
				result = session.run(cypher)
			}

			while (result.hasNext()) {
				Record record = result.next()
				Map<String, Object> rowMap = [:]

				record.keys().each { String key ->
					Value val = record.get(key)
					rowMap[key] = val?.asObject()
				}

				rows.add(rowMap)
			}

			KeywordUtil.logInfo("[Neo4j_Functions] Query returned ${rows.size()} row(s).")
		} finally {
			if (session != null) {
				session.close()
			}
		}
		return rows
	}

	/**
	 * Run a Cypher query with parameters and return the result as List<Map>.
	 * Each map is columnName -> value.
	 * On transient connection/routing errors, resets the driver and retries up to MAX_QUERY_ATTEMPTS.
	 */
	static List<Map> runQuery(String cypher, Map params) {
		KeywordUtil.logInfo("[Neo4j_Functions] Called runQuery with Cypher:\n${cypher}")
		KeywordUtil.logInfo("[Neo4j_Functions] Params: ${params}")

		Throwable lastError = null
		for (int attempt = 1; attempt <= MAX_QUERY_ATTEMPTS; attempt++) {
			try {
				List<Map> rows = executeQuery(cypher, params)
				if (attempt > 1) {
					KeywordUtil.logInfo(
							"[Neo4j_Functions] Retry succeeded on attempt ${attempt}; returned ${rows.size()} row(s).")
				}
				return rows
			} catch (Exception e) {
				lastError = e
				boolean canRetry = attempt < MAX_QUERY_ATTEMPTS && isRetryableConnectionError(e)
				if (!canRetry) {
					break
				}
				KeywordUtil.logInfo(
						"[Neo4j_Functions] Retryable Neo4j error on attempt ${attempt}/${MAX_QUERY_ATTEMPTS}; " +
						"resetting driver and retrying: ${e.message}")
				closeDriver()
				sleepBeforeRetry(attempt + 1)
			}
		}

		KeywordUtil.markFailedAndStop(
				"[Neo4j_Functions] Neo4j query failed after ${MAX_QUERY_ATTEMPTS} attempt(s): ${lastError?.message}")
	}

	/**
	 * Optional: call this at the end of the test suite or in a @TearDown method
	 * if you want to explicitly close the driver.
	 */
	static void closeDriver() {
		if (driver != null) {
			KeywordUtil.logInfo("[Neo4j_Functions] Closing Neo4j driver.")
			driver.close()
			driver = null
		}
	}
}
