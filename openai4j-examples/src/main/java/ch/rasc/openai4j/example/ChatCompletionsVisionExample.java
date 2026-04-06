package ch.rasc.openai4j.example;

import java.util.ArrayList;
import java.util.List;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage.Content;
import ch.rasc.openai4j.chatcompletions.UserMessage.ImageContent;
import ch.rasc.openai4j.chatcompletions.UserMessage.TextContent;

public class ChatCompletionsVisionExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		List<Content> content = new ArrayList<>();
		content.add(TextContent.of("What’s in this image?"));
		content.add(ImageContent.of(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg"));

		var response = client.chatCompletions.create(
				r -> r.addMessages(SystemMessage.of("You are a helpful assistant"),
						UserMessage.of(content)).model("gpt-4o").maxTokens(300));
		System.out.println(response.choices().get(0).message().content());

	}
}
