package ch.rasc.mongodb.morphia;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

public class Read {

	public static void main(String[] args) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("ch.rasc.mongodb.morphia");

		try (MongoClient mongoClient = new MongoClient()) {
			Datastore datastore = morphia.createDatastore(mongoClient, "morphia_example");
			Query<Employee> query = datastore.createQuery(Employee.class);
			List<Employee> employees = query.asList();
			employees.stream().forEach(System.out::println);

			System.out.println("---");
			List<Employee> underpaid = datastore.createQuery(Employee.class)
					.field("salary").lessThanOrEq(30000).asList();
			underpaid.stream().forEach(System.out::println);

			System.out.println("---");
			underpaid = datastore.createQuery(Employee.class).filter("salary <=", 30000)
					.asList();
			underpaid.stream().forEach(System.out::println);

		}

	}

}
