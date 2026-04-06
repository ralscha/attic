package ch.rasc.playground.arangodb;

import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;

public class Aql {
	public static void main(String[] args) {
		ArangoDB arangoDB = new ArangoDB.Builder().user("root").build();

		ArangoDatabase db = arangoDB.db("mydb");
		try {
			db.drop();
		}
		catch (ArangoDBException e) {
			System.err.println("Failed to drop database " + e.getMessage());
		}

		arangoDB.createDatabase("mydb");

		db.createCollection("firstCollection");
		ArangoCollection collection = db.collection("firstCollection");

		for (Integer i = 0; i < 10; i++) {
			BaseDocument baseDocument = new BaseDocument();
			baseDocument.setKey(i.toString());
			baseDocument.addAttribute("name", "Homer");
			baseDocument.addAttribute("b", i + 42);
			collection.insertDocument(baseDocument);
		}

		try {
			final String query = "FOR t IN firstCollection FILTER t.name == @name RETURN t";
			final Map<String, Object> bindVars = new MapBuilder().put("name", "Homer")
					.get();
			final ArangoCursor<BaseDocument> cursor = db.query(query, bindVars, null,
					BaseDocument.class);
			for (; cursor.hasNext();) {
				System.out.println("Key: " + cursor.next().getKey());
			}
		}
		catch (final ArangoDBException e) {
			System.err.println("Failed to execute query. " + e.getMessage());
		}

		// delete a document with AQL
		try {
			final String query = "FOR t IN firstCollection FILTER t.name == @name "
					+ "REMOVE t IN firstCollection LET removed = OLD RETURN removed";
			final Map<String, Object> bindVars = new MapBuilder().put("name", "Homer")
					.get();
			final ArangoCursor<BaseDocument> cursor = db.query(query, bindVars, null,
					BaseDocument.class);
			for (; cursor.hasNext();) {
				System.out.println("Removed document " + cursor.next().getKey());
			}
		}
		catch (final ArangoDBException e) {
			System.err.println("Failed to execute query. " + e.getMessage());
		}

	}

}