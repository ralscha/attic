package ch.rasc.playground.arangodb;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;

public class Update {
	public static void main(String[] args) {

		ArangoDB arangoDB = new ArangoDB.Builder().user("root").build();
		ArangoDatabase db = arangoDB.db("mydb");
		ArangoCollection collection = db.collection("firstCollection");

		BaseDocument myObject = new BaseDocument();
		myObject.setKey("myKey");
		myObject.addAttribute("a", "Foo");
		myObject.addAttribute("b", 42);
		myObject.addAttribute("c", 3.141);
		collection.updateDocument("myKey", myObject);

	}
}
