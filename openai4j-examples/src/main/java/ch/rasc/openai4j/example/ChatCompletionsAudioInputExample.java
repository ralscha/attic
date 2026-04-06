package ch.rasc.openai4j.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage.Content;
import ch.rasc.openai4j.chatcompletions.UserMessage.InputAudioFormat;
import ch.rasc.openai4j.chatcompletions.UserMessage.InputAudioMessageContent;

public class ChatCompletionsAudioInputExample {
	public static void main(String[] args) throws IOException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		List<Content> content = new ArrayList<>();
		Path inputAudio = Paths.get("question.mp3");
		byte[] audioBytes = Files.readAllBytes(inputAudio);
		String base64Audio = Base64.getEncoder().encodeToString(audioBytes);
		
		content.add(InputAudioMessageContent.of(base64Audio, InputAudioFormat.MP3));
		
		var response = client.chatCompletions.create(r -> r
				.addMessages(SystemMessage.of("You are a helpful assistant"),
						UserMessage.of(content))
				.model("gpt-4o-audio-preview"));
		System.out.println(response.choices().get(0).message().content());

	}
}
