/**
 * Connects to Memgraph over Bolt and stores the result in an excel sheet.
 * Memgraph accepts the Neo4j Java driver when encryption is turned off.
 */
package utilities

import java.util.ArrayList
import java.util.List

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Config
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Record
import org.neo4j.driver.Result
import org.neo4j.driver.Session

import com.google.gson.Gson

public class ConnectMemgraph extends ConnectNeo4jV4 {

	@Override
	public void run(String uri, String user, String password, String cypher, String output, String sheetName) {
		List<String> excelData = executeCypher(uri, user, password, cypher)

		//messages.add("Memgraph_URL:")
		//messages.add(uri)
		//messages.add("User_name:")
		//messages.add(user)
		//messages.add("PWD:")
		//messages.add(password)
		messages.add("Cypher:")
		messages.add(cypher)
		//messages.add("Output:")
		//messages.add(output)

		export(excelData, output, sheetName)
	}

	@Override
	public List<String> executeCypher(String uri, String user, String password, String cypher) {
		Gson gson = new Gson()
		List<String> output = new ArrayList<String>()
		Config config = Config.builder().withoutEncryption().build()
		Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password), config)

		try {
			Session session = driver.session()
			try {
				Result result = session.run(cypher)
				while (result.hasNext()) {
					Record record = result.next()
					output.add(gson.toJson(record.asMap()))
				}
			} finally {
				session.close()
			}
		} catch (Exception e) {
			System.out.print(e.getMessage())
			messages.add(e.getMessage())
		} finally {
			driver.close()
		}
		return output
	}
}
