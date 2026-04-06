package ch.rasc.mongodb.geolite;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FindIp {

	public static void main(String[] args) {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.geolite")) {
			Datastore datastore = ctx.getBean(Datastore.class);

			// long myIp = 16777216l * 188 + 65536l * 61 + 256l * 140l + 26;
			long firstIp = 16777217; // first document
			long lastIp = 3758096129L; // last document
			long testIp = 1275328930;

			// first document
			System.out.print("First document: ");
			find(datastore, firstIp);

			// last document
			System.out.print("Last document: ");
			find(datastore, lastIp);

			// test document
			System.out.print("Test document: ");
			find(datastore, testIp);
		}

	}

	private static void find(Datastore datastore, long ip) {
		Query<Geolite> q = datastore.createQuery(Geolite.class);
		q.field("startIpNum").lessThanOrEq(ip);
		q.field("endIpNum").greaterThanOrEq(ip);

		long start = System.currentTimeMillis();
		Geolite geo = q.get();
		System.out.println(System.currentTimeMillis() - start + " ms");
		System.out.println(geo);
	}

}
