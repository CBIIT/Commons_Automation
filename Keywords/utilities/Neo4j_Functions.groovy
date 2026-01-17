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

	/**
	 * Run a Cypher query with parameters and return the result as List<Map>.
	 * Each map is columnName -> value.
	 */
	static List<Map> runQuery(String cypher, Map params) {
		KeywordUtil.logInfo("[Neo4j_Functions] Called runQuery with Cypher:\n${cypher}")
		KeywordUtil.logInfo("[Neo4j_Functions] Params: ${params}")

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
		} catch (Exception e) {
			KeywordUtil.logError("[Neo4j_Functions] Error executing Neo4j query: ${e.message}")
			// Optionally rethrow if you want tests to fail on connection issues:
			// throw e
		} finally {
			if (session != null) {
				session.close()
			}
		}

		return rows
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