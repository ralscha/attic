package ch.rasc.openai4j.example;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.IntArrayList;
import com.knuddels.jtokkit.api.ModelType;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;

public class ChatCompletionsExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var response = client.chatCompletions.create(r -> r
				.addMessages(SystemMessage.of("You are a helpful assistant"),
						UserMessage.of("What is the capital of Spain?"))
				.logprobs(true).model("gpt-4o-mini"));
		System.out.println(response.choices().get(0).message().content());
		System.out.println(response.choices().get(0).logprobs());

		EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
		Encoding enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
		IntArrayList encoded = enc.encode("What is the capital of Spain?");
		System.out.println("Token length: " + encoded.size());

		/*
		 * var azureClient = OpenAIClient
		 * .create(Configuration.builder().apiVersion("2024-02-01")
		 * .apiKey("...").azureDeployment("gpt-35-turbo")
		 * .azureEndpoint("https://myresource.openai.azure.com/").build()); var request =
		 * ChatCompletionsCreateRequest.builder()
		 * .addMessage(SystemMessage.of("You are a helpful assistant"))
		 * .addMessage(UserMessage.of("What is the capital of Spain?"))
		 * .model("gpt-35-turbo").build(); response =
		 * azureClient.chatCompletions.create(request); System.out.println(response);
		 */
	}
}
