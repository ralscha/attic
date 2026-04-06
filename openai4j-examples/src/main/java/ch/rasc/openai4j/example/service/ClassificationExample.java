package ch.rasc.openai4j.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsModelRequest.Mode;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsService;
import ch.rasc.openai4j.example.Util;

public class ClassificationExample {

	enum Label {
		SPAM, NOT_SPAM
	}

	record Prediction(@JsonProperty(required = true) Label label) {
	}

	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));
		ObjectMapper om = new ObjectMapper();
		var service = new ChatCompletionsService(client.chatCompletions, om);

		var response = service.<Prediction>createModel(r -> r.addMessages(UserMessage.of(
				"Classify the following text: Hello there I'm a nigerian prince and I want to give you money"))
				.model("gpt-4o-mini").responseModel(Prediction.class).mode(Mode.JSON_OBJECT)
				.maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());

		response = service.<Prediction>createModel(r -> r.addMessages(UserMessage.of(
				"Classify the following text: Hello I'm from the bank and I need your password"))
				.model("gpt-4o-mini").responseModel(Prediction.class).mode(Mode.JSON_SCHEMA)
				.maxRetries(2));
		System.out.println(response.responseModel());
		System.out.println(response.response());
	}

}
