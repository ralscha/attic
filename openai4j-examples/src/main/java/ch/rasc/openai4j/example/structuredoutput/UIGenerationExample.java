package ch.rasc.openai4j.example.structuredoutput;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.example.Util;

public class UIGenerationExample {

	record Attribute(@JsonProperty(required = true) String name,
			@JsonProperty(required = true) String value) {
	}

	enum UIType {
		div, button, header, section, field, form
	}

	record UI(@JsonProperty(required = true) UIType type,
			@JsonProperty(required = true) String label,
			@JsonProperty(required = true) List<UI> children,
			@JsonProperty(required = true) List<Attribute> attributes) {
	}

	public static void main(String[] args)
			throws JsonMappingException, JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		JsonSchemaService jsonSchemaService = new JsonSchemaService();

		var response = client.chatCompletions.create(r -> r
				.addMessages(SystemMessage.of(
						"You are a UI generator AI. Convert the user input into a UI."),
						UserMessage.of("Make a User Profile Form"))
				.responseFormat(
						jsonSchemaService.createStrictResponseFormat(UI.class, "ui"))
				.model("gpt-4o-2024-08-06"));
		String content = response.choices().get(0).message().content();

		ObjectMapper objectMapper = new ObjectMapper();
		UI ui = objectMapper.readValue(content, UI.class);

		System.out.println(ui);

	}

}
