package ch.rasc.openai4j.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsModelRequest.Mode;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsService;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsService.ChatCompletionsModelResponse;
import ch.rasc.openai4j.example.Util;

public class UserExample {

	record User(
			@JsonProperty(
					required = true) @JsonPropertyDescription("Age of a user") int age,
			@JsonProperty(
					required = true) @JsonPropertyDescription("Name of a user") String name,

			@JsonPropertyDescription("Role of a user. Optional") String role) {
	}

	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));
		ObjectMapper om = new ObjectMapper();
		var service = new ChatCompletionsService(client.chatCompletions, om);

		ChatCompletionsModelResponse<User> response = service.createModel(r -> r
				.addMessages(
						UserMessage.of("Get user details for: Jason is 25 years old"))
				.model("gpt-4o").responseModel(User.class).mode(Mode.JSON_OBJECT)
				.maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());

		System.out.println("=======");

		response = service.createModel(r -> r
				.addMessages(
						UserMessage.of("Get user details for: Jason is 25 years old"))
				.model("gpt-4o").responseModel(User.class).mode(Mode.TOOL).maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());

		System.out.println("=======");

		response = service.createModel(
				r -> r.addMessages(UserMessage.of("Jason is a 25 years old scientist"))
						.model("gpt-3.5-turbo-0125").responseModel(User.class)
						.mode(Mode.TOOL).maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());

		System.out.println("=======");

		response = service.createModel(
				r -> r.addMessages(UserMessage.of("Jason is a 25 years old scientist"))
						.model("gpt-3.5-turbo-0125").responseModel(User.class)
						.mode(Mode.JSON_OBJECT).maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());
	}

}
