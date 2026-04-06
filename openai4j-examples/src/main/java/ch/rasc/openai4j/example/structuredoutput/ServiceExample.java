package ch.rasc.openai4j.example.structuredoutput;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsService;
import ch.rasc.openai4j.example.Util;

public class ServiceExample {

	record CapitalResponse(@JsonProperty(
			required = true) @JsonPropertyDescription("name of capital") String capital) {
	}

	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		ObjectMapper om = new ObjectMapper();
		var service = new ChatCompletionsService(client.chatCompletions, om);

		var response = service.<CapitalResponse>createModel(r -> r.addMessages(SystemMessage.of("You are a helpful assistant"),
				UserMessage.of("What is the capital of Spain?"))
				.model("gpt-4o-mini").responseModel(CapitalResponse.class)
				.maxRetries(2));
		
		
	    System.out.println(response.responseModel());
	    System.out.println(response.response());
	    System.out.println(response.error());

	}
}
