package ch.rasc.mongodb.blog;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class MainDelete {
	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");
		// db.dropDatabase();

		MongoCollection<Document> collection = db.getCollection("users");
		// collection.drop();

		DeleteResult dr = collection.deleteMany(
				Filters.and(Filters.eq("enabled", false), Filters.eq("noOfLogins", 0)));
		System.out.println(dr);

		for (Document d : collection.find()) {
			System.out.println(d);
		}
	}
}
