package ch.rasc.openai4j.example;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.UserMessage;

public class PredictedOutputsExample {

	public static void main(String[] args) {

		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		String code = "class User {\n  firstName: string = \"\";\n  lastName: string = \"\";\n  username: string = \"\";\n}\n\nexport default User;\n";
		String refactorPrompt = "Replace the \"username\" property with an \"email\" property. Respond only with code, and with no markdown formatting.";

		var response = client.chatCompletions.create(
				r -> r.addMessages(UserMessage.of(refactorPrompt), UserMessage.of(code))
						.model("gpt-4o").prediction(p -> p.content(code)));

		System.out.println(response.choices().get(0).message().content());
		System.out.println(response.usage());
	}
}
