package ch.rasc.perf;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.util.StopWatch;

public class MainJsonP {

	public static void main(String[] args) {

		DataFactory df = new DataFactory();
		Date today = new Date();
		JsonGeneratorFactory jsonGeneratorFactory = Json
				.createGeneratorFactory(Collections.<String, Object>emptyMap());
		JsonParserFactory jsonParserFactory = Json
				.createParserFactory(Collections.<String, Object>emptyMap());

		// warm up
		StopWatch sw = new StopWatch();

		List<String> storage = new ArrayList<>();
		sw.start("write");
		for (int i = 0; i < 1000; i++) {
			User user = new User();
			user.setEmail(df.getEmailAddress());
			user.setEnabled(df.chance(90));
			user.setFailedLogins(df.getNumberUpTo(3));
			user.setFirstName(df.getFirstName());
			user.setId(1L);
			user.setLocale(df.getItem(new String[] { "de", "en", "fr", "it" }));
			if (df.chance(98)) {
				user.setLockedOut(df.getDate(today, 0, 100));
			}
			user.setDob(df.getBirthDate());
			user.setName(df.getLastName());
			user.setRole(df.getItem(new String[] { "ADMIN", "USER", "READ" }));
			user.setUserName(df.getRandomChars(5, 8));

			String value = toJson(jsonGeneratorFactory, user);
			storage.add(value);
		}
		sw.stop();
		sw.start("read");
		for (int i = 0; i < 1000; i++) {
			toObject(jsonParserFactory, storage.get(i));
		}
		sw.stop();
		storage.clear();

		int totalRuns = 500000;
		if (args.length > 0) {
			totalRuns = Integer.parseInt(args[0]);
		}

		System.out.println("Total Runs: " + totalRuns);
		sw = new StopWatch();
		sw.start();
		for (int r = 0; r < totalRuns; r++) {
			storage.clear();
			for (int i = 0; i < 10; i++) {
				User user = new User();
				user.setEmail(df.getEmailAddress());
				user.setEnabled(df.chance(90));
				user.setFailedLogins(df.getNumberUpTo(3));
				user.setFirstName(df.getFirstName());
				user.setId(1L);
				user.setLocale(df.getItem(new String[] { "de", "en", "fr", "it" }));
				if (df.chance(98)) {
					user.setLockedOut(df.getDate(today, 0, 100));
				}
				user.setDob(df.getBirthDate());
				user.setName(df.getLastName());
				user.setRole(df.getItem(new String[] { "ADMIN", "USER", "READ" }));
				user.setUserName(df.getRandomChars(5, 8));

				String value = toJson(jsonGeneratorFactory, user);
				storage.add(value);
			}
			for (int i = 0; i < 10; i++) {
				toObject(jsonParserFactory, storage.get(i));
			}
		}
		sw.stop();
		System.out.println(sw.shortSummary());

	}

	private static String toJson(JsonGeneratorFactory jsonFactory, User user) {
		StringWriter sw = new StringWriter();
		try (JsonGenerator jg = jsonFactory.createGenerator(sw)) {
			jg.writeStartObject();
			jg.write("id", user.getId());
			jg.write("userName", user.getUserName());
			jg.write("name", user.getName());
			jg.write("firstName", user.getFirstName());
			jg.write("email", user.getEmail());
			jg.write("role", user.getRole());
			jg.write("locale", user.getLocale());
			jg.write("enabled", user.isEnabled());
			jg.write("failedLogins", user.getFailedLogins());

			if (user.getLockedOut() != null) {
				jg.write("lockedOut", user.getLockedOut().getTime());
			}
			else {
				jg.writeNull("lockedOut");
			}

			if (user.getDob() != null) {
				jg.write("dob", user.getDob().getTime());
			}
			else {
				jg.writeNull("dob");
			}
			jg.writeEnd();
		}
		return sw.toString();
	}

	private static User toObject(JsonParserFactory jsonParserFactory, String json) {
		User user = new User();

		try (JsonParser parser = jsonParserFactory.createParser(new StringReader(json))) {

			parser.next();

			// id
			parser.next(); // id
			parser.next();
			user.setId(parser.getLong());

			parser.next(); // userName
			parser.next();
			user.setUserName(parser.getString());

			parser.next(); // name
			parser.next();
			user.setName(parser.getString());

			parser.next(); // firstName
			parser.next();
			user.setFirstName(parser.getString());

			parser.next(); // email
			parser.next();
			user.setEmail(parser.getString());

			parser.next(); // role
			parser.next();
			user.setRole(parser.getString());

			parser.next(); // locale
			parser.next();
			user.setLocale(parser.getString());

			parser.next(); // enabled
			Event token = parser.next();
			if (token == Event.VALUE_TRUE) {
				user.setEnabled(true);
			}
			else {
				user.setEnabled(false);
			}

			parser.next();
			token = parser.next(); // failedLogins
			if (token == Event.VALUE_NULL) {
				user.setFailedLogins(null);
			}
			else {
				user.setFailedLogins(parser.getInt());
			}

			parser.next();
			token = parser.next(); // lockedOut
			if (token == Event.VALUE_NULL) {
				user.setLockedOut(null);
			}
			else {
				user.setLockedOut(new Date(parser.getLong()));
			}

			parser.next();
			token = parser.next(); // dob
			if (token == Event.VALUE_NULL) {
				user.setDob(null);
			}
			else {
				user.setDob(new Date(parser.getLong()));
			}
		}
		return user;
	}

}
