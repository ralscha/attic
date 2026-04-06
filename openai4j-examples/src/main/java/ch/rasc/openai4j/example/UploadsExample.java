package ch.rasc.openai4j.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.files.Purpose;

public class UploadsExample {
	public static void main(String[] args) throws IOException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var createResponse = client.uploads.create(r -> r.filename("test.txt").bytes(10)
				.purpose(Purpose.ASSISTANTS).mimeType("text/plain"));
		System.out.println(createResponse);

		Random rnd = new Random();

		byte[] bytes = new byte[5];
		rnd.nextBytes(bytes);
		File part1File = File.createTempFile("uploads", "random", null);
		Files.write(part1File.toPath(), bytes);

		bytes = new byte[5];
		rnd.nextBytes(bytes);
		File part2File = File.createTempFile("uploads", "random", null);
		Files.write(part2File.toPath(), bytes);

		var part1 = client.uploads.addPart(createResponse.id(), part1File);
		var part2 = client.uploads.addPart(createResponse.id(), part2File);

		System.out.println(part1);
		System.out.println(part2);

		var completeResponse = client.uploads.complete(createResponse.id(),
				r -> r.addPartId(part1.id(), part2.id()));
		System.out.println(completeResponse);

		// String uploadId = createResponse.id();
		// var cancelResponse = client.uploads.cancel(uploadId);
		// System.out.println(cancelResponse);
	}
}
