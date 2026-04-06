package ch.rasc.perf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.util.StopWatch;

public class Main {

	public static void main(String[] args) throws IOException {

		DataFactory df = new DataFactory();
		Date today = new Date();
		com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		// objectMapper.registerModule(new AfterburnerModule());

		// warm up
		StopWatch sw = new StopWatch();
		User u = null;
		List<String> storage = new ArrayList<>();
		sw.start("write");
		for (int i = 0; i < 100000; i++) {
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

			String value = objectMapper.writeValueAsString(user);
			storage.add(value);
		}
		sw.stop();
		sw.start("read");
		for (int i = 0; i < 100000; i++) {
			u = objectMapper.readValue(storage.get(i), User.class);
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

				String value = objectMapper.writeValueAsString(user);
				storage.add(value);
			}
			for (int i = 0; i < 10; i++) {
				u = objectMapper.readValue(storage.get(i), User.class);
			}
		}
		System.out.println(u);
		sw.stop();
		System.out.println(sw.shortSummary());

	}

}
