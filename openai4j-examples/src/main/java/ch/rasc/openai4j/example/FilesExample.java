package ch.rasc.openai4j.example;

import java.io.FileOutputStream;
import java.nio.file.Paths;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.files.Purpose;

public class FilesExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var files = client.files.list();
		System.out.println(files);
		for (var response : files.data()) {
			System.out.println(response);

			var dstatus = client.files.delete(response.id());
			System.out.println(dstatus);
		}

		System.out.println();

		var filesWithAPurpose = client.files.list(Purpose.FINE_TUNE);
		System.out.println(filesWithAPurpose);

		var p = Paths.get("./image1.png");
		var response = client.files.createForAssistants(p);
		System.out.println(response);

		System.out.println();
		var fileInfo = client.files.retrieve(response.id());
		System.out.println(fileInfo);

		System.out.println();
		files = client.files.list();
		for (var f : files.data()) {
			System.out.println(f);
		}

		try (var resp = client.files.retrieveContent(response.id());
				FileOutputStream fos = new FileOutputStream("image1_copy.png");
				var body = resp.body();
				var is = body.asInputStream()) {
			is.transferTo(fos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		files = client.files.list();
		for (var r : files.data()) {
			client.files.delete(r.id());
		}
	}
}
