package ch.rasc.mongodb.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationAction;
import com.mongodb.client.model.ValidationLevel;
import com.mongodb.client.model.ValidationOptions;

public class ValidationMain {
	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("validation");

		ValidationOptions validationOptions = new ValidationOptions();
		validationOptions.validationAction(ValidationAction.ERROR);
		validationOptions.validationLevel(ValidationLevel.STRICT);

		// {"lastname" : {"$exists" : true, "$ne" : ""}
		List<Bson> validators = new ArrayList<>();

		validators.add(
				Filters.and(Filters.ne("username", null), Filters.ne("username", "")));
		validators.add(
				Filters.and(Filters.ne("password", null), Filters.ne("password", "")));
		validators.add(Filters.and(Filters.lte("count", 10), Filters.gt("count", 0)));

		validationOptions.validator(Filters.and(validators));

		if (!StreamSupport.stream(db.listCollectionNames().spliterator(), false)
				.filter(n -> n.equals("users")).findAny().isPresent()) {
			db.createCollection("users",
					new CreateCollectionOptions().validationOptions(validationOptions));
		}

		insertOkay(db);
		try {
			insertFailure(db);
		}
		catch (MongoWriteException e) {
			System.out.println(e.getError());
		}
	}

	private static void insertOkay(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("users");
		Document user = new Document("username", "johnd").append("password", "mypw")
				.append("count", 5);
		collection.insertOne(user);
	}

	private static void insertFailure(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("users");
		Document user = new Document("username", "ralph").append("password", "pw")
				.append("count", 11);
		collection.insertOne(user);
	}
}