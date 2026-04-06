package ch.rasc.mongodb.blog;

import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MainUpdate {
	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");
		MongoCollection<Document> collection = db.getCollection("users");

		collection.updateMany(Filters.eq("username", "johnd"),
				new Document("$set", new Document("lastLogin", new Date())));
		collection.updateMany(Filters.eq("username", "johnd"),
				new Document("$inc", new Document("noOfLogins", -1)));

		Document user = collection.findOneAndUpdate(Filters.eq("username", "johnd"),
				new Document("$set", new Document("country", "US")));
		System.out.println(user);
	}
}
