package ch.rasc.openai4j.example;

import ch.rasc.openai4j.OpenAIClient;

public class ModerationsExample {

	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var input = "Hallo schöne Welt wie geht es dir?";

		var response = client.moderations.create(r -> r.input(input));
		System.out.println(response);
	}
}
