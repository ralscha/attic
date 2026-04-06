package ch.rasc.perf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class MainManual {

	public static void main(String[] args) throws IOException {

		DataFactory df = new DataFactory();
		Date today = new Date();
		JsonFactory jsonFactory = new JsonFactory();

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

			String value = toJson(jsonFactory, user);
			storage.add(value);
		}
		sw.stop();
		sw.start("read");
		for (int i = 0; i < 1000; i++) {
			toObject(jsonFactory, storage.get(i));
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

				String value = toJson(jsonFactory, user);
				storage.add(value);
			}
			for (int i = 0; i < 10; i++) {
				toObject(jsonFactory, storage.get(i));
			}
		}
		sw.stop();
		System.out.println(sw.shortSummary());

	}

	private static String toJson(JsonFactory jsonFactory, User user) throws IOException {
		StringWriter sw = new StringWriter();
		try (JsonGenerator jg = jsonFactory.createGenerator(sw)) {
			jg.writeStartObject();
			jg.writeNumberField("id", user.getId());
			jg.writeStringField("userName", user.getUserName());
			jg.writeStringField("name", user.getName());
			jg.writeStringField("firstName", user.getFirstName());
			jg.writeStringField("email", user.getEmail());
			jg.writeStringField("role", user.getRole());
			jg.writeStringField("locale", user.getLocale());
			jg.writeBooleanField("enabled", user.isEnabled());
			jg.writeNumberField("failedLogins", user.getFailedLogins());

			if (user.getLockedOut() != null) {
				jg.writeNumberField("lockedOut", user.getLockedOut().getTime());
			}
			else {
				jg.writeNullField("lockedOut");
			}

			if (user.getDob() != null) {
				jg.writeNumberField("dob", user.getDob().getTime());
			}
			else {
				jg.writeNullField("dob");
			}

			jg.writeEndObject();

		}
		return sw.toString();
	}

	private static User toObject(JsonFactory jsonFactory, String json)
			throws IOException {
		User user = new User();

		try (JsonParser parser = jsonFactory.createParser(json)) {

			parser.nextToken();

			// id
			parser.nextValue(); // id
			user.setId(parser.getLongValue());

			parser.nextValue(); // userName
			user.setUserName(parser.getText());

			parser.nextValue(); // name
			user.setName(parser.getText());

			parser.nextValue(); // firstName
			user.setFirstName(parser.getText());

			parser.nextValue(); // email
			user.setEmail(parser.getText());

			parser.nextValue(); // role
			user.setRole(parser.getText());

			parser.nextValue(); // locale
			user.setLocale(parser.getText());

			parser.nextValue(); // enabled
			user.setEnabled(parser.getBooleanValue());

			JsonToken token = parser.nextValue(); // failedLogins
			if (token == JsonToken.VALUE_NULL) {
				user.setFailedLogins(null);
			}
			else {
				user.setFailedLogins(parser.getIntValue());
			}

			token = parser.nextValue(); // lockedOut
			if (token == JsonToken.VALUE_NULL) {
				user.setLockedOut(null);
			}
			else {
				user.setLockedOut(new Date(parser.getLongValue()));
			}

			token = parser.nextValue(); // dob
			if (token == JsonToken.VALUE_NULL) {
				user.setDob(null);
			}
			else {
				user.setDob(new Date(parser.getLongValue()));
			}
		}
		return user;
	}

}
