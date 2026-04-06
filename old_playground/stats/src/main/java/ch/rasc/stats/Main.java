package ch.rasc.stats;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.RateLimiter;

public class Main {

	private final static String GOOGLE_GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&components=administrative_area:ZH|country:CH&sensor=false";

	public static void main(String[] args) throws URISyntaxException, IOException {

		Path citiesFilePath = Paths.get(Main.class.getResource("/cities.txt").toURI());
		List<String> lines = Files.readAllLines(citiesFilePath, StandardCharsets.UTF_8);

		RestTemplate template = new RestTemplate();
		RateLimiter limiter = RateLimiter.create(2);

		// System.out.println(template.getForObject(GOOGLE_GEOCODE_URL,
		// String.class, "Wettswil a.A."));
		// if (true) return;

		for (String line : lines) {
			limiter.acquire();

			System.out.println("working: " + line);
			GeoCoderResults results = template.getForObject(GOOGLE_GEOCODE_URL,
					GeoCoderResults.class, line.trim());

			if (results.getStatus() == Status.OK) {

				GeoCoderResult result = results.getResults().get(0);
				System.out.println(result.getGeometry().getLocationType());
				System.out.println(result.getGeometry().getLocation().getLat());
				System.out.println(result.getGeometry().getLocation().getLng());

			}
			else {
				System.out.println("Status: " + results.getStatus());
			}

			if (true) {
				return;
			}
		}
	}

}
