package ch.rasc.openai4j.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.images.Image;
import ch.rasc.openai4j.images.ImageResponseFormat;
import ch.rasc.openai4j.images.ImageSize;

public class ImageEditExample {

	public static void main(String[] args) throws IOException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var response = client.images.edit(r -> r.image(Paths.get("./input.png"))
				.prompt("Cats in Paris").mask(Paths.get("./mask.png")).n(3)
				.responseFormat(ImageResponseFormat.B64_JSON).size(ImageSize.S_1024));
		int i = 7;
		for (Image imageObject : response.data()) {
			String b64Json = imageObject.b64Json();
			byte[] decodedBytes = Base64.getDecoder().decode(b64Json);
			Files.write(Paths.get("image" + i + ".png"), decodedBytes);
			i++;
		}
	}
}
