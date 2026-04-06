package ch.rasc.mongodb.author;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportText {
	public static void main(String[] args) throws IOException {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				"ch.rasc.mongodb.author")) {

			Path testdataDir = Paths.get("./testdata");
			downloadTestData(testdataDir);

			TextImporter importer = ctx.getBean("textImporter", TextImporter.class);
			long start = System.currentTimeMillis();

			Files.list(testdataDir).forEach(importer::doImport);

			System.out.println(System.currentTimeMillis() - start + " ms");
		}
	}

	private static void downloadTestData(Path directory) throws IOException {
		Files.createDirectories(directory);

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			downloadFile(client, "http://rasc.ch/testdata/pg2600.txt",
					Paths.get("./testdata/2600.txt"));
			downloadFile(client, "http://rasc.ch/testdata/pg1342.txt",
					Paths.get("./testdata/1342.txt"));
			downloadFile(client, "http://rasc.ch/testdata/pg76.txt",
					Paths.get("./testdata/76.txt"));
			downloadFile(client, "http://rasc.ch/testdata/pg120.txt",
					Paths.get("./testdata/120.txt"));
		}
	}

	private static void downloadFile(CloseableHttpClient client, String url, Path file)
			throws IOException {
		if (!Files.exists(file)) {
			try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					try (OutputStream outstream = Files.newOutputStream(file)) {
						entity.writeTo(outstream);
					}
				}
			}
		}
	}
}
