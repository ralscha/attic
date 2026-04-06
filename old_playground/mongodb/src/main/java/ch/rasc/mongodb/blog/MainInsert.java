package ch.rasc.mongodb.blog;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MainInsert {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		MongoCollection<Document> collection = db.getCollection("users");

		Document user = new Document("username", "johnd");
		user.append("_id", 1);
		user.append("firstName", "John");
		user.append("name", "Doe");
		user.append("enabled", Boolean.FALSE);
		user.append("noOfLogins", 0);
		user.append("lastLogin", new Date());
		user.append("groups", Arrays.asList("admin", "user"));

		System.out.println(user);
		collection.insertOne(user);

		user = new Document("username", "francol");
		user.append("firstName", "Franco");
		user.append("name", "Lawrence");
		user.append("enabled", Boolean.TRUE);
		user.append("noOfLogins", 0);
		user.append("lastLogin", new Date());
		user.append("groups", Arrays.asList("user"));
		collection.insertOne(user);
	}

}
