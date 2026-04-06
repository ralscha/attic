package ch.rasc.openai4j.example.structuredoutput;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.ChatCompletionResponse.Message;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.example.Util;

public class ChainOfThoughtExample {

	record Step(@JsonProperty(required = true) String explanation,
			@JsonProperty(required = true) String output) {
	}

	record MathReasoning(@JsonProperty(required = true) List<Step> steps,
			@JsonProperty(required = true) String finalAnswer) {
	}

	public static void main(String[] args)
			throws JsonMappingException, JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		JsonSchemaService jsonSchemaService = new JsonSchemaService();

		var response = client.chatCompletions.create(r -> r.addMessages(SystemMessage.of(
				"You are a helpful math tutor. Guide the user through the solution step by step."),
				UserMessage.of("how can I solve 8x + 7 = -23"))
				.responseFormat(
						jsonSchemaService.createStrictResponseFormat(MathReasoning.class))
				.model("gpt-4o-2024-08-06"));
		Message message = response.choices().get(0).message();
		String content = message.content();
		String refusal = message.refusal();
		if (refusal != null) {
			System.out.println("Refusal: " + refusal);
			return;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		MathReasoning reasoning = objectMapper.readValue(content, MathReasoning.class);

		System.out.println("Final answer: " + reasoning.finalAnswer());
		System.out.println("---");
		for (Step step : reasoning.steps()) {
			System.out.println(step.explanation());
			System.out.println(step.output());
			System.out.println("---");
		}

	}

}
