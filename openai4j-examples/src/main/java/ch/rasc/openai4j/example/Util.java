package ch.rasc.openai4j.example;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Util {
	public static String getApiKey() {
		String token = System.getenv("OPENAI_API_KEY");
		if (token == null || token.isBlank()) {
			// try to read from .env in current directory
			Properties props = new Properties();
			try (InputStream is = Files.newInputStream(Paths.get(".env"))) {
				props.load(is);
				token = props.getProperty("OPENAI_API_KEY");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return token;
	}
}
