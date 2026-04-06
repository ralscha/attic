package ch.rasc.mongodb.sandbox;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class Main {

	public static void main(String[] args)
			throws MongoException, JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> userData = mapper
				.readValue(Main.class.getResourceAsStream("/users.json"), List.class);

		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(userData, mongo);
		}

	}

	private static void doSomething(List<Map<String, Object>> userData,
			MongoClient mongo) {
		mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);

		MongoDatabase db = mongo.getDatabase("testdbs");
		// db.setWriteConcern(WriteConcern.SAFE);

		MongoCollection<Document> collection = db.getCollection("testcollection");
		// collection.setWriteConcern(WriteConcern.SAFE);

		collection.drop();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		List<Document> newDocuments = new ArrayList<>();
		for (Map<String, Object> row : userData) {

			// replace string with a date
			String dateOfBirth = (String) row.get("dob");

			try {
				row.put("dob", df.parse(dateOfBirth));
			}
			catch (ParseException e) {
				e.printStackTrace();
			}

			newDocuments.add(new Document(row));
		}
		collection.insertMany(newDocuments);

		collection.find().forEach((Consumer<Document>) d -> System.out.println(d));

		collection.find(Filters.eq("username", "johnd"))
				.projection(Projections.include("username", "password"))
				.forEach((Consumer<Document>) d -> System.out.println(d));
	}

}
