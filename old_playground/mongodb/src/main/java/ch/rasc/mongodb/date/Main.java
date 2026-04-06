package ch.rasc.mongodb.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	public static void main(String[] args) throws MongoException {
		try (MongoClient mongo = new MongoClient("localhost")) {
			doSomething(mongo);
		}
	}

	private static void doSomething(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("testdb");
		MongoCollection<Document> collection = db.getCollection("users");
		collection.drop();

		Document user = new Document();
		user.append("zdt", Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
		user.append("odt", Date.from(OffsetDateTime.now().toInstant()));
		user.append("idt", Date.from(Instant.now()));

		// Date now = new Date();
		// Instant instant = Instant.ofEpochMilli(now.getTime());
		// OffsetDateTime odt = OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
		// System.out.println(odt);

		collection.insertOne(user);

		for (Document d : collection.find()) {

			Date d1 = (Date) d.get("zdt");
			Date d2 = (Date) d.get("odt");
			Date d3 = (Date) d.get("idt");

			System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(d1.getTime()),
					ZoneOffset.UTC));
			System.out.println(OffsetDateTime
					.ofInstant(Instant.ofEpochMilli(d2.getTime()), ZoneOffset.UTC));
			System.out.println(Instant.ofEpochMilli(d3.getTime()));
		}

	}

	public static Date ldToDate(LocalDate ld) {
		return ldtToDate(ld.atStartOfDay());
	}

	public static Date ldtToDate(LocalDateTime ldt) {
		return Date.from(ldt.atZone(ZoneOffset.UTC).toInstant());
	}

	public static LocalDate dateToLd(Date date) {
		return dateToLdt(date).toLocalDate();
	}

	public static LocalDateTime dateToLdt(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

}
