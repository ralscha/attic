package ch.rasc.openai4j.example.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.chatcompletions.service.ChatCompletionsService;
import ch.rasc.openai4j.chatcompletions.service.JavaFunction;
import ch.rasc.openai4j.example.Util;

public class ChatCompletionsFunctionExample {
	private final static ObjectMapper om = new ObjectMapper();

	static class Location {
		@JsonProperty(required = true)
		@JsonPropertyDescription("Latitude of the location. Geographical WGS84 coordinates")
		public float latitude;

		@JsonProperty(required = true)
		@JsonPropertyDescription("Longitude of the location. Geographical WGS84 coordinates")
		public float longitude;
	}

	static class TemperatureFetcher {

		public Float fetchTemperature(Location location) {
			System.out.println("calling fetchTemperature");
			try (var client = HttpClient.newHttpClient()) {
				var request = HttpRequest.newBuilder()
						.uri(URI.create("https://api.open-meteo.com/v1/metno?latitude="
								+ location.latitude + "&longitude=" + location.longitude
								+ "&current=temperature_2m"))
						.build();
				var response = client.send(request,
						java.net.http.HttpResponse.BodyHandlers.ofString());

				var body = response.body();
				System.out.println(body);
				var jsonNode = om.readTree(body);
				var current = jsonNode.get("current");
				var temperature = current.get("temperature_2m");
				return temperature.floatValue();
			}
			catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}

		}

	}

	public static void main(String[] args) throws JsonProcessingException {

		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		TemperatureFetcher fetcher = new TemperatureFetcher();
		String functionName = "get_temperature";
		JavaFunction<Location, Float> getWeather = JavaFunction.of(functionName,
				"Get the current temperature of a location", Location.class,
				fetcher::fetchTemperature);

		var service = new ChatCompletionsService(client.chatCompletions, om);

		var response = service.createJavaFunctions(r -> r.addMessages(UserMessage.of(
				"What are the current temperatures in Oslo, Norway, and Helsinki, Finland?"))
				.model("gpt-4o").javaFunctions(List.of(getWeather)));
		var choice = response.choices().get(0);
		System.out.println(choice.message().content());

	}
}
