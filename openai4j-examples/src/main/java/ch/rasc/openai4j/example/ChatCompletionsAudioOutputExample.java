package ch.rasc.openai4j.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.Builder.AudioFormat;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.Builder.Voice;
import ch.rasc.openai4j.chatcompletions.ChatCompletionCreateRequest.Modality;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;

public class ChatCompletionsAudioOutputExample {
	public static void main(String[] args) throws IOException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var response = client.chatCompletions.create(r -> r
				.addMessages(SystemMessage.of("You are a helpful assistant"),
						UserMessage.of("What is the capital of Spain?"))
				.modalities(Modality.AUDIO, Modality.TEXT)
				.audio(Voice.ONYX, AudioFormat.MP3)
				.model("gpt-4o-audio-preview"));
		var audioResponse = response.choices().get(0).message().audio();
		String b64audio = audioResponse.data();
		Files.write(Paths.get("audio.mp3"), Base64.getDecoder().decode(b64audio));

	}
}
