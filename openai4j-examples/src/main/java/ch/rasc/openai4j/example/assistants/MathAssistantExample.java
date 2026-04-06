package ch.rasc.openai4j.example.assistants;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.assistants.Assistant;
import ch.rasc.openai4j.assistants.CodeInterpreterTool;
import ch.rasc.openai4j.example.Util;
import ch.rasc.openai4j.threads.TextMessageContent;

public class MathAssistantExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var assistants = client.assistants.list();
		Assistant assistant = null;

		for (var a : assistants.data()) {
			if ("Math Tutor".equals(a.name())) {
				assistant = a;
				break;
			}
		}

		if (assistant == null) {
			assistant = client.assistants.create(c -> c.name("Math Tutor").instructions(
					"You are a personal math tutor. Write and run code to answer math questions.")
					.addTools(CodeInterpreterTool.of()).model("gpt-4o"));
		}
		System.out.println(assistant);

		var thread = client.threads.create();
		System.out.println(thread);

		var message = client.threadsMessages.create(thread.id(),
				c -> c.userRole().content(
						"I need to solve the equation `3x + 11 = 14`. Can you help me?"));
		System.out.println(message);

		final Assistant af = assistant;
		var run = client.threadsRuns.create(thread.id(),
				c -> c.assistantId(af.id()).instructions(
						"Please address the user as Jane Doe. The user has a premium account."));

		client.threadsRuns.waitForProcessing(run);

		System.out.println("Messages from the assistant");
		var messages = client.threadsMessages.list(thread.id(),
				p -> p.before(message.id()));
		for (var msg : messages.data()) {
			var content = msg.content().get(0);
			if (content instanceof TextMessageContent text) {
				System.out.println(text.text().value());
			}
		}

		System.out.println("list run steps");
		var steps = client.threadsRunsSteps.list(thread.id(), run.id());
		for (var step : steps.data()) {
			System.out.println(step);
		}
	}
}
