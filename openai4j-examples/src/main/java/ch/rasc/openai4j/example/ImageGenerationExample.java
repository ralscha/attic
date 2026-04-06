package ch.rasc.openai4j.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.images.ImageGenerationRequest;
import ch.rasc.openai4j.images.ImageGenerationRequest.Quality;
import ch.rasc.openai4j.images.ImageGenerationRequest.Style;
import ch.rasc.openai4j.images.ImageModel;
import ch.rasc.openai4j.images.ImageResponseFormat;
import ch.rasc.openai4j.images.ImageSize;

public class ImageGenerationExample {

	public static void main(String[] args) throws IOException, InterruptedException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		String input = "A bunch of people are standing in a field. They are wearing colorful clothes and holding"
				+ " umbrellas.";
		var response = client.images
				.generate(r -> r.model(ImageModel.DALL_E_3).quality(Quality.HD)
						.prompt(input).style(Style.NATURAL).size(ImageSize.S_1024));
		var url = response.data().get(0).url();

		try (var httpClient = HttpClient.newHttpClient()) {
			var request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			var resp = httpClient.send(request,
					HttpResponse.BodyHandlers.ofInputStream());
			var fileName = "image1.png";
			try (var body = resp.body()) {
				Files.copy(body, Paths.get(fileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		}

		String input2 = "A couple of cats are sitting on a couch.";
		response = client.images.generate(r -> r.model(ImageModel.DALL_E_3)
				.quality(ImageGenerationRequest.Quality.HD).prompt(input2)
				.style(ImageGenerationRequest.Style.NATURAL)
				.responseFormat(ImageResponseFormat.B64_JSON).size(ImageSize.S_1024));
		String b64Json = response.data().get(0).b64Json();
		byte[] decodedBytes = Base64.getDecoder().decode(b64Json);
		Files.write(Paths.get("image2.png"), decodedBytes);
	}
}
