package ch.rasc.maildeleter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String... args) {
		logger.info("maildeleter: start");
		try {

			Path configFile = Paths.get("./config.yaml");

			if (!Files.exists(configFile)) {
				URL myLocationURL = Main.class.getProtectionDomain().getCodeSource()
						.getLocation();
				Path myPath = Paths.get(myLocationURL.toURI());
				Path myDir = myPath.getParent();

				configFile = myDir.resolve("config.yaml");
			}

			if (Files.exists(configFile)) {
				Config config;
				try (InputStream is = Files.newInputStream(configFile)) {
					Yaml yaml = new Yaml();
					config = yaml.loadAs(is, Config.class);
				}
				new MailDeleter(config).run();
			}
			else {
				logger.error("config file not found");
			}

		}
		catch (URISyntaxException | IOException e) {
			logger.error("error", e);
		}

		logger.info("maildeleter: end");
	}

}
