package ch.rasc.school;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;

public class Main {

	public static void main(String[] args) throws MongoException {

		try (MongoClient mongo = new MongoClient("localhost")) {
			MongoDatabase db = mongo.getDatabase("school");
			MongoCollection<Document> collection = db.getCollection("students");

			for (Document student : collection.find()
					.projection(Projections.exclude("name"))) {

				int id = student.getInteger("_id");
				List<Document> scoreDoc = student.get("scores", List.class);
				Optional<Document> lowestHomework = scoreDoc.stream()
						.filter(s -> s.getString("type").equals("homework"))
						.sorted(Comparator.comparingDouble(d -> d.getDouble("score")))
						.findFirst();

				if (lowestHomework.isPresent()) {
					System.out.println(lowestHomework);
					collection.updateOne(Filters.eq("_id", id),
							Updates.pull("scores", lowestHomework.get()));
				}
			}

		}

	}

}
