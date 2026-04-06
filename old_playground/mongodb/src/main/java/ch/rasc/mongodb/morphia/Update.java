package ch.rasc.mongodb.morphia;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.MongoClient;

public class Update {

	public static void main(String[] args) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("ch.rasc.mongodb.morphia");

		try (MongoClient mongoClient = new MongoClient()) {
			Datastore datastore = morphia.createDatastore(mongoClient, "morphia_example");

			Query<Employee> underPaidQuery = datastore.createQuery(Employee.class)
					.filter("salary <=", 30000);
			UpdateOperations<Employee> updateOperations = datastore
					.createUpdateOperations(Employee.class).inc("salary", 10000);

			UpdateResults results = datastore.update(underPaidQuery, updateOperations);
			System.out.println(results.getUpdatedCount());
			System.out.println(results.getUpdatedExisting());
		}

	}

}
