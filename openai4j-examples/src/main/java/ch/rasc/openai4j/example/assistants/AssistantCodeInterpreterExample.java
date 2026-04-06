package ch.rasc.openai4j.example.assistants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.assistants.Assistant;
import ch.rasc.openai4j.assistants.CodeInterpreterTool;
import ch.rasc.openai4j.assistants.ToolResources;
import ch.rasc.openai4j.example.Util;
import ch.rasc.openai4j.files.FileObject;
import ch.rasc.openai4j.threads.TextMessageContent;

public class AssistantCodeInterpreterExample {
	public static void main(String[] args) throws IOException, InterruptedException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		FileObject file;

		try (var httpClient = HttpClient.newHttpClient()) {
			var request = HttpRequest.newBuilder()
					.uri(URI.create(
							"https://www.gutenberg.org/cache/epub/67098/pg67098.txt"))
					.build();
			var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			var body = response.body();

			Path tmpFile = Files.createTempFile("pooh", ".txt");
			Files.writeString(tmpFile, body);

			// upload file for Assistant
			file = client.files.createForAssistants(tmpFile);
			client.files.waitForProcessing(file.id());

			Files.delete(tmpFile);
		}

		var assistants = client.assistants.list();
		Assistant assistant = null;

		for (var a : assistants.data()) {
			if ("DocumentAnalyzer".equals(a.name())) {
				assistant = a;
				break;
			}
		}

		if (assistant == null) {
			assistant = client.assistants.create(c -> c.name("DocumentAnalyzer")
					.instructions("You are a analyzer and summarizer of documents")
					.addTools(CodeInterpreterTool.of())
					.toolResources(
							ToolResources.ofCodeInterpreter(r -> r.addFileIds(file.id())))
					.model("gpt-4o"));
		}

		var thread = client.threads.create();

		var message = client.threadsMessages.create(thread.id(),
				c -> c.role("user").content(
						"List all persons that appear in the story. Bullet points with a short description. Maximum 10 persons"));

		final Assistant af = assistant;
		var run = client.threadsRuns.create(thread.id(), c -> c.assistantId(af.id()));
		client.threadsRuns.waitForProcessing(run);

		var messages = client.threadsMessages.list(thread.id(),
				p -> p.before(message.id()));
		for (var msg : messages.data()) {
			var content = msg.content().get(0);
			if (content instanceof TextMessageContent text) {
				System.out.println(text.text().value());
			}
		}

		client.assistants.delete(assistant.id());
		client.files.delete(file.id());

	}
}
