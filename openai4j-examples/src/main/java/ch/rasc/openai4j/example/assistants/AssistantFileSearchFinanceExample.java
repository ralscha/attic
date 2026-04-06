package ch.rasc.openai4j.example.assistants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.assistants.FileSearchTool;
import ch.rasc.openai4j.assistants.ToolResources;
import ch.rasc.openai4j.example.Util;
import ch.rasc.openai4j.threads.TextMessageContent;
import ch.rasc.openai4j.threads.TextMessageContent.Text.FileCitation;

public class AssistantFileSearchFinanceExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var assistant = client.assistants.create(c -> c
				.name("Financial Analyst Assistant").model("gpt-4o")
				.instructions(
						"You are an expert financial analyst. Use you knowledge base to answer questions about audited financial statements.")
				.addTools(FileSearchTool.of()));

		var vectorStore = client.vectorStores.create(c -> c.name("Financial Statements"));

		Path file1 = download(
				"https://abc.xyz/assets/4b/01/aae7bef55a59851b0a2d983ef18f/596de1b094c32cf0592a08edfe84ae74.pdf");
		Path file2 = download("https://www.berkshirehathaway.com/qtrly/1stqtr24.pdf");

		var file1Obj = client.files.createForAssistants(file1);
		var file2Obj = client.files.createForAssistants(file2);

		var vectorStoreFileBatch = client.vectorStoresFileBatches.create(vectorStore.id(),
				file1Obj.id(), file2Obj.id());
		client.vectorStoresFileBatches.waitForProcessing(vectorStoreFileBatch);

		assistant = client.assistants.modify(assistant.id(), c -> c.toolResources(
				ToolResources.ofFileSearch(r -> r.vectorStoreIds(vectorStore.id()))));

		Path file3 = download(
				"https://d18rn0p25nwr6d.cloudfront.net/CIK-0000320193/a4b40e13-bf4b-47bc-b2fe-e5ccd72f5815.pdf");
		var file3Obj = client.files.createForAssistants(file3);

		var thread = client.threads.create(c -> c.addMessage(m -> m.userRole().content(
				"How many shares of AAPL were outstanding at the end of of October 2023.")
				.addAttachment(
						a -> a.fileId(file3Obj.id()).addTools(FileSearchTool.of()))));

		var assistantId = assistant.id();
		var run = client.threadsRuns.create(thread.id(), c -> c.assistantId(assistantId));
		run = client.threadsRuns.waitForProcessing(run);
		var runId = run.id();
		var messages = client.threadsMessages.list(thread.id(), q -> q.runId(runId));
		var messageContent = messages.data().get(0).content().get(0);
		var textMessageContent = (TextMessageContent) messageContent;
		var annotations = textMessageContent.text().annotations();

		var citations = new StringBuilder();
		String value = textMessageContent.text().value();
		for (int i = 0; i < annotations.size(); i++) {
			var annotation = (FileCitation) annotations.get(i);
			value = value.replace(annotation.text(), "[" + i + "]");
			if (annotation.fileCitation() != null) {
				var citedFile = client.files.retrieve(annotation.fileCitation().fileId());
				citations.append("[").append(i).append("] ").append(citedFile.filename())
						.append("\n");
			}
		}
		System.out.println(value);
		System.out.println(citations);

		// delete everything
		client.assistants.delete(assistantId);
		thread.toolResources().fileSearch().vectorStoreIds()
				.forEach(client.vectorStores::delete);
		client.vectorStores.delete(vectorStore.id());
		client.files.delete(file1Obj.id());
		client.files.delete(file2Obj.id());
		client.files.delete(file3Obj.id());
	}

	private static Path download(String url) {
		try (var httpClient = HttpClient.newHttpClient()) {
			var request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			var body = response.body();

			String suffix = url.substring(url.lastIndexOf('.'));
			Path tmpFile = Files.createTempFile(UUID.randomUUID().toString(), suffix);
			Files.writeString(tmpFile, body);
			return tmpFile;
		}
		catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
