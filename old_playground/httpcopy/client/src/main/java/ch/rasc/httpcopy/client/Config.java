package ch.rasc.httpcopy.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.LoggerFactory;

public class Config {

	private static final String CLIENT_ID_KEY = "client.id";
	private static final String OVERWRITE_KEY = "overwrite";
	private static final String SERVER_ENDPOINT_KEY = "server.endpoint";
	private static final String WATCH_DIR_KEY = "watch.dir";

	private Path appPropertiesPath;
	private Properties appProperties;

	public Config() {
		try {
			init();
		}
		catch (URISyntaxException | IOException e) {
			LoggerFactory.getLogger(Config.class).error("construct config", e);
		}
	}

	private void init() throws URISyntaxException, IOException {
		this.appProperties = new Properties();

		Path jarPath = new File(Config.class.getProtectionDomain().getCodeSource()
				.getLocation().toURI().getPath()).toPath();
		this.appPropertiesPath = jarPath.resolveSibling("application.properties");

		if (Files.exists(this.appPropertiesPath)) {
			try (InputStream is = Files.newInputStream(this.appPropertiesPath)) {
				this.appProperties.load(is);
			}
		}
		else {
			this.appProperties.put(CLIENT_ID_KEY, getRandomId());
			this.appProperties.put(SERVER_ENDPOINT_KEY, "http://localhost:8080");
			this.appProperties.put(OVERWRITE_KEY, "false");
			store();
		}
	}

	public void store() {
		try {
			try (OutputStream out = Files.newOutputStream(this.appPropertiesPath)) {
				this.appProperties.store(out, "httpcopy");
			}
		}
		catch (IOException e) {
			LoggerFactory.getLogger(Config.class).error("store properties", e);
		}
	}

	private static String getRandomId() {
		UUID uuid = UUID.randomUUID();
		byte[] idBytes = ByteBuffer.allocate(16).putLong(uuid.getLeastSignificantBits())
				.putLong(uuid.getMostSignificantBits()).array();
		return Base64.getUrlEncoder().encodeToString(idBytes);
	}

	// true: overwrite files on the server when they have the same filename (default)
	// false: rename file when a file with the same name exists (e.g. test.txt ->
	// test_1.txt)
	public boolean isOverwrite() {
		return Boolean.parseBoolean(this.appProperties.getProperty(OVERWRITE_KEY));
	}

	public void setOverwrite(boolean overwrite) {
		this.appProperties.put(OVERWRITE_KEY, Boolean.toString(overwrite));
	}

	public String getClientId() {
		return this.appProperties.getProperty(CLIENT_ID_KEY);
	}

	public void setServerEndpoint(String serverEndpoint) {
		this.appProperties.put(SERVER_ENDPOINT_KEY, serverEndpoint);
	}

	public String getServerEndpoint() {
		return this.appProperties.getProperty(SERVER_ENDPOINT_KEY);
	}

	public void setWatchDir(String path) {
		this.appProperties.put(WATCH_DIR_KEY, path);
	}

	public Path getWatchDir() {
		String watchDirName = this.appProperties.getProperty(WATCH_DIR_KEY);
		if (watchDirName != null) {
			return Paths.get(watchDirName);
		}
		return null;
	}

}
