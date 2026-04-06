package ch.rasc.openai4j.example.structuredoutput;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.example.Util;

public class StructuredDataExtractionExample {

	record ResearchPaperExtraction(@JsonProperty(required = true) String title,
			@JsonProperty(required = true) List<String> authors,
			@JsonProperty(required = true) String abstractText,
			@JsonProperty(required = true) List<String> keywords) {
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		JsonSchemaService jsonSchemaService = new JsonSchemaService();

		String researchPaperText = "";
		try (HttpClient httpClient = HttpClient.newHttpClient()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
					"https://fastercapital.com/content/Quantum-Space-Exploration--Advancing-Interstellar-Travel-with-QIP.html"))
					.build();
			HttpResponse<String> httpResponse = httpClient.send(request,
					HttpResponse.BodyHandlers.ofString());
			researchPaperText = httpResponse.body();
		}
		final String researchPaperTextFinal = researchPaperText;

		var response = client.chatCompletions.create(r -> r.addMessages(SystemMessage.of(
				"You are an expert at structured data extraction. You will be given unstructured text from a research paper and should convert it into the given structure."),
				UserMessage.of(researchPaperTextFinal))
				.responseFormat(jsonSchemaService
						.createStrictResponseFormat(ResearchPaperExtraction.class))
				.model("gpt-4o-2024-08-06"));
		String content = response.choices().get(0).message().content();

		ObjectMapper objectMapper = new ObjectMapper();
		ResearchPaperExtraction extraction = objectMapper.readValue(content,
				ResearchPaperExtraction.class);

		System.out.println("Title: " + extraction.title());
		System.out.println("Authors: " + extraction.authors());
		System.out.println("Abstract: " + extraction.abstractText());
		System.out.println("Keywords: " + extraction.keywords());

	}

}
