package ch.rasc.openai4j.example;

import ch.rasc.openai4j.OpenAIClient;

public class ModelsExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var models = client.models.list();
		for (var response : models.data()) {
			System.out.println(response);
		}

		System.out.println();

		String model = "dall-e-3";
		var modelDetail = client.models.retrieve(model);
		System.out.println(modelDetail);

		var delete = client.models.delete("ft:gpt-3.5-turbo-0125:legal-i-ch::8Q2yAxaf");
		System.out.println(delete);

		System.out.println();

		models = client.models.list();
		for (var response : models.data()) {
			System.out.println(response);
		}
	}
}
