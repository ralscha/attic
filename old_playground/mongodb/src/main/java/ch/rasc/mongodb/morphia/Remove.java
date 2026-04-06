package ch.rasc.mongodb.morphia;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class Remove {

	public static void main(String[] args) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("ch.rasc.mongodb.morphia");

		try (MongoClient mongoClient = new MongoClient()) {
			Datastore datastore = morphia.createDatastore(mongoClient, "morphia_example");

			Query<Employee> overPaidQuery = datastore.createQuery(Employee.class)
					.filter("salary >", 100000);
			WriteResult wr = datastore.delete(overPaidQuery);
			System.out.println(wr.getN());

		}

	}

}
