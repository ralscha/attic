package ch.rasc.openai4j.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.batch.BatchRequestInput;
import ch.rasc.openai4j.batch.BatchRequestOutput;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest;
import ch.rasc.openai4j.chatcompletions.ChatCompletionResponse;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.files.Purpose;

public class BatchChatCompletionExample {
	public static void main(String[] args) throws IOException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		ObjectMapper objectMapper = new ObjectMapper();

		var request1 = ChatCompletionCreateRequest.builder().model("gpt-4o")
				.maxTokens(100)
				.addMessages(UserMessage
						.of("What movie is this quote from \"Once upon a time\""))
				.build();
		var request2 = ChatCompletionCreateRequest.builder().model("gpt-4o")
				.maxTokens(100).addMessages(UserMessage.of("Where is Spain")).build();
		var request3 = ChatCompletionCreateRequest.builder().model("gpt-4o")
				.maxTokens(100)
				.addMessages(UserMessage.of("What is the capital of Spain?")).build();

		List<BatchRequestInput<ChatCompletionCreateRequest>> batchRequestInputs = List.of(
				BatchRequestInput.of("1", request1), BatchRequestInput.of("2", request2),
				BatchRequestInput.of("3", request3));

		var tmpFile = Files.createTempFile("batch", ".jsonl");
		for (var batchRequestInput : batchRequestInputs) {
			String json = objectMapper.writeValueAsString(batchRequestInput);
			Files.writeString(tmpFile, json + "\n",
					java.nio.file.StandardOpenOption.APPEND);
		}
		var response = client.files.upload(tmpFile, Purpose.BATCH);

		var batchResponse = client.batches.create(
				c -> c.endpoint("/v1/chat/completions").inputFileId(response.id()));
		System.out.println(batchResponse);

		while (true) {
			batchResponse = client.batches.retrieve(batchResponse.id());
			if ("completed".equals(batchResponse.status())) {
				break;
			}
			if (batchResponse.errors() != null) {
				break;
			}
			try {
				TimeUnit.MINUTES.sleep(1);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		System.out.println(batchResponse);

		if (batchResponse.errors() == null) {
			try (var responseFileContent = client.files
					.retrieveContent(batchResponse.outputFileId());
					var body = responseFileContent.body();
					Reader reader = body.asReader(StandardCharsets.UTF_8);
					BufferedReader bufferedReader = new BufferedReader(reader)) {
				List<String> lines = bufferedReader.lines().toList();

				for (var line : lines) {
					var output = objectMapper.readValue(line,
							new TypeReference<BatchRequestOutput<ChatCompletionResponse>>() {
							});
					System.out.println(output.response().body().choices().get(0).message()
							.content());
				}
			}
		}

		if (batchResponse.outputFileId() != null) {
			client.files.delete(batchResponse.outputFileId());
		}

		if (batchResponse.inputFileId() != null) {
			client.files.delete(batchResponse.inputFileId());
		}

		if (batchResponse.errorFileId() != null) {
			client.files.delete(batchResponse.errorFileId());
		}

	}
}
