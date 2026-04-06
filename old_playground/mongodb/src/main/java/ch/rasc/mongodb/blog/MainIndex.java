package ch.rasc.mongodb.blog;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MainIndex {
	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		MongoCollection<Document> collection = db.getCollection("users");
		// collection.dropIndexes();

		Document index = new Document("username", 1);
		collection.createIndex(index);

		FindIterable<Document> result = collection.find(Filters.eq("username", "johnd"));
		System.out.println(result.first());

		System.out.println("Indices:");
		for (Document ix : collection.listIndexes()) {
			System.out.println(ix);
		}
	}
}
