package ch.rasc.openai4j.example;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.audio.AudioRecognitionModel;
import ch.rasc.openai4j.audio.AudioRecognitionResponseFormat;
import ch.rasc.openai4j.audio.AudioSpeechRequest.AudioResponseFormat;
import ch.rasc.openai4j.audio.AudioSpeechRequest.SpeechModel;
import ch.rasc.openai4j.audio.AudioSpeechRequest.Voice;
import ch.rasc.openai4j.audio.AudioTranscriptionRequest.TimestampGranularity;
import feign.Response;

public class AudioExample {

	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		var input = "Wie geht es dir?";

		try (Response response = client.audio
				.create(r -> r.input(input).model(SpeechModel.TTS_1_HD)
						.responseFormat(AudioResponseFormat.MP3).voice(Voice.ALLOY));
				FileOutputStream fos = new FileOutputStream("hello.mp3");
				var body = response.body();
				var is = body.asInputStream()) {
			is.transferTo(fos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Path inp = Paths.get("hello.mp3");
		var resp = client.audio
				.transcriptionsCreate(r -> r.model(AudioRecognitionModel.WHISPER_1)
						.responseFormat(AudioRecognitionResponseFormat.VERBOSE_JSON)
						.addTimestampGranularities(TimestampGranularity.WORD,
								TimestampGranularity.SEGMENT)
						.file(inp));
		System.out.println(resp);

		var resp2 = client.audio.translationsCreate(
				r -> r.model(AudioRecognitionModel.WHISPER_1).file(inp));
		System.out.println(resp2);
	}
}
