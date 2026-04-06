package ch.rasc.playground.arangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;

public class Write {
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

		BaseDocument myObject = new BaseDocument();
		myObject.setKey("myKey");
		myObject.addAttribute("a", "Foo");
		myObject.addAttribute("b", 42);

		collection.insertDocument(myObject);
		System.out.println("Document created");
	}

}