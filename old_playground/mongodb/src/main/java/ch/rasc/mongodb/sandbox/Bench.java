package ch.rasc.mongodb.sandbox;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.util.StopWatch;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Bench {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo, WriteConcern.ACKNOWLEDGED);
			doSomething(mongo, WriteConcern.UNACKNOWLEDGED);
			doSomething(mongo, WriteConcern.JOURNALED);
		}
	}

	private static void doSomething(MongoClient mongo, WriteConcern writeConcern) {
		MongoDatabase db = mongo.getDatabase("testdb");
		db.getCollection("files").drop();
		MongoCollection<Document> collection = db.getCollection("files")
				.withWriteConcern(writeConcern);
		collection.insertOne(new Document("myKey", "0"));

		StopWatch watch = new StopWatch();
		watch.start();

		for (int o = 0; o < 1_000; o++) {
			List<Document> docs = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				docs.add(new Document("myKey", "aValue:" + o + "_" + i));
			}
			collection.insertMany(docs);
		}

		watch.stop();
		System.out.print(writeConcern);
		System.out.print(": ");
		System.out.println(watch.getTotalTimeMillis());
	}

}
