package ch.rasc.github;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Url {

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://api.github.com/users/ralscha/repos");
		InputStream is = url.openStream();

		final char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();
		try (Reader in = new InputStreamReader(is, "UTF-8")) {
			int read;
			while ((read = in.read(buffer, 0, buffer.length)) > 0) {
				out.append(buffer, 0, read);
			}
		}

		String json = out.toString();
		System.out.println(json);

		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> result = mapper.readValue(json, List.class);

		for (Map<String, Object> repo : result) {
			System.out.println("Name: " + repo.get("name"));
			System.out.println("URL: " + repo.get("git_url"));
			System.out.println();
		}

	}
}
