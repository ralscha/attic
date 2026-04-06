package ch.rasc.openai4j.example.assistants;

import ch.rasc.openai4j.Configuration;
import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.assistants.CodeInterpreterTool;
import ch.rasc.openai4j.example.Util;

public class AssistantsExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(Configuration.builder().apiKey(apiKey).build());

		var assistants = client.assistants.list();
		for (var a : assistants.data()) {
			System.out.println(a);
		}

		var c = client.assistants.create(r -> r.description("my test assistant")
				.name("ralph").model("gpt-4o").instructions("you are a helpul assistant")
				.putMetadata("userId", "1").addTools(CodeInterpreterTool.of()));
		System.out.println(c);

		var m = client.assistants.modify(c.id(),
				ra -> ra.description("my test assistant2").name("ralph2")
						.putMetadata("userId", "2"));
		System.out.println(m);

		var m2 = client.assistants.retrieve(m.id());
		System.out.println(m2);

		assistants = client.assistants.list();
		for (var a : assistants.data()) {
			client.assistants.delete(a.id());
		}
		System.out.println("LIST");
		assistants = client.assistants.list();
		for (var a : assistants.data()) {
			System.out.println(a);
		}

	}
}
