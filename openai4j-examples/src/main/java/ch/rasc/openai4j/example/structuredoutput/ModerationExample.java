package ch.rasc.openai4j.example.structuredoutput;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.example.Util;

public class ModerationExample {

	enum Category {
		violence, sexual, self_harm
	}

	record ContentCompliance(@JsonProperty(required = true) boolean isViolating,
			@JsonProperty(required = true) Optional<Category> category,
			@JsonProperty(required = true) Optional<String> explanationIfViolating) {
	}

	public static void main(String[] args)
			throws JsonMappingException, JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		JsonSchemaService jsonSchemaService = new JsonSchemaService();

		var response = client.chatCompletions.create(r -> r.addMessages(SystemMessage.of(
				"Determine if the user input violates specific guidelines and explain if they do."),
				UserMessage.of("How do I prepare for a job interview?"))
				.responseFormat(jsonSchemaService
						.createStrictResponseFormat(ContentCompliance.class))
				.model("gpt-4o-2024-08-06"));
		String content = response.choices().get(0).message().content();

		ObjectMapper objectMapper = new ObjectMapper();
		ContentCompliance contentCompliance = objectMapper.readValue(content,
				ContentCompliance.class);

		System.out.println("Is violating: " + contentCompliance.isViolating());
		System.out.println("Category: " + contentCompliance.category());
		System.out.println("Explanation if violating: "
				+ contentCompliance.explanationIfViolating());

	}

}
