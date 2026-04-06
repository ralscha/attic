package ch.rasc.playground.arangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;

public class Delete {
	public static void main(String[] args) {

		ArangoDB arangoDB = new ArangoDB.Builder().user("root").build();
		ArangoDatabase db = arangoDB.db("mydb");
		ArangoCollection collection = db.collection("firstCollection");
		collection.deleteDocument("myKey");

	}
}
