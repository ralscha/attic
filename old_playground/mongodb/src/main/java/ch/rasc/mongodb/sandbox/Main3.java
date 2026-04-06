package ch.rasc.mongodb.sandbox;

import java.util.function.Consumer;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Main3 {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("tutorial");

		MongoCollection<Document> collection = db.getCollection("users");
		collection.drop();

		Document dbObj = new Document("last_name", "smith");
		dbObj.append("age", 30);
		collection.insertOne(dbObj);

		dbObj = new Document("last_name", "jones");
		dbObj.append("age", 40);
		collection.insertOne(dbObj);

		collection.find().forEach((Consumer<Document>) d -> System.out.println(d));

		Document update = new Document("$set", new Document("city", "Chicago"));
		collection.updateOne(Filters.eq("last_name", "smith"), update);

		for (Document doc : collection.find(Filters.gt("age", 20))) {
			ObjectId oid = (ObjectId) doc.get("_id");
			System.out.println(oid);
			System.out.println(oid.getDate());
			System.out.println(doc);
		}
	}

}
