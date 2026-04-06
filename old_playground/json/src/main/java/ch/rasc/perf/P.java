package ch.rasc.perf;

import java.util.stream.IntStream;

import org.boon.IO;
import org.boon.json.JsonFactory;
import org.springframework.util.StopWatch;

public class P {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String userJson = IO.read("./src/main/resources/user.json");
		org.boon.json.ObjectMapper boon = JsonFactory.create();
		com.fasterxml.jackson.databind.ObjectMapper jackson = new com.fasterxml.jackson.databind.ObjectMapper();
		User user = new User();
		// user.setDob(new Date());

		StopWatch sw = new StopWatch();

		IntStream.range(0, 10000).forEach(n -> {
			try {
				String json = jackson.writeValueAsString(user);
				json.charAt(0);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		});
		sw.start("jackson serial");
		IntStream.range(0, 10000).forEach(n -> {
			try {
				String json = jackson.writeValueAsString(user);
				json.charAt(0);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		});
		sw.stop();

		IntStream.range(0, 10000).forEach(n -> {
			String json = boon.writeValueAsString(user);
			json.charAt(0);
		});
		sw.start("boon serial");
		IntStream.range(0, 10000).forEach(n -> {
			String json = boon.toJson(user);
			json.charAt(0);
		});
		sw.stop();

		System.out.println(sw.prettyPrint());

		sw = new StopWatch();
		IntStream.range(0, 10000).forEach(n -> {
			try {
				Object u = jackson.readValue(userJson, Object.class);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		sw.start("jackson deserial");
		IntStream.range(0, 10000).forEach(n -> {
			try {
				Object u = jackson.readValue(userJson, Object.class);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		sw.stop();

		IntStream.range(0, 10000).forEach(n -> {
			try {
				Object u = boon.fromJson(userJson);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		sw.start("boon deserial");
		IntStream.range(0, 10000).forEach(n -> {
			try {
				Object u = boon.fromJson(userJson);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		sw.stop();

		System.out.println(sw.prettyPrint());

	}

}
