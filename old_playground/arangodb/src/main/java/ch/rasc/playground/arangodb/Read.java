package ch.rasc.playground.arangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;

public class Read {
	public static void main(String[] args) {

		ArangoDB arangoDB = new ArangoDB.Builder().user("root").build();
		ArangoDatabase db = arangoDB.db("mydb");
		ArangoCollection collection = db.collection("firstCollection");

		BaseDocument doc = collection.getDocument("myKey", BaseDocument.class);
		if (doc != null) {
			System.out.println("Key: " + doc.getKey());
			System.out.println("Attribute 'a': " + doc.getProperties().get("a"));
			System.out.println("Attribute 'b': " + doc.getProperties().get("b"));
			System.out.println("Attribute 'c': " + doc.getProperties().get("c"));
		}
		else {
			System.err.println("document not found");
		}
	}
}
