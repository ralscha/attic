package ch.rasc.mongodb.blog;

import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MainSelect {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");

		MongoCollection<Document> collection = db.getCollection("users");

		for (Document d : collection.find()) {
			System.out.println(d);
		}

		System.out.println();

		for (Document d : collection.find(Filters.eq("username", "johnd"))) {
			System.out.println(d);
		}

		System.out.println();

		Document doc = collection.find(Filters.eq("username", "johnd")).first();
		System.out.println(doc);

		System.out.println();

		doc = collection.find(Filters.eq("username", "johnd"))
				.projection(Projections.exclude("_id", "enabled")).first();
		System.out.println(doc);

		System.out.println();

		for (Document d : collection.find(Filters.and(Filters.eq("username", "johnd"),
				Filters.eq("name", "Doe")))) {
			System.out.println(d);
		}

		System.out.println();

		for (Document d : collection.find(Filters.or(Filters.eq("username", "francol"),
				Filters.eq("username", "johnd")))) {
			System.out.println(d);
		}

		System.out.println();

		for (Document d : collection.find(Filters.in("groups", "admin"))) {
			System.out.println(d);
		}

		System.out.println();

		Pattern pattern = Pattern.compile("^D.*", Pattern.CASE_INSENSITIVE);

		for (Document d : collection.find(Filters.regex("name", pattern)).limit(1)
				.skip(10).sort(Sorts.ascending("username"))) {
			System.out.println(d);
		}
	}

}
