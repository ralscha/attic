package ch.rasc.openai4j.example.structuredoutput;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.chatcompletions.SystemMessage;
import ch.rasc.openai4j.chatcompletions.UserMessage;
import ch.rasc.openai4j.common.JsonSchemaService;
import ch.rasc.openai4j.example.Util;

public class CalendarEventExample {

	record CalendarEvent(@JsonProperty(required = true) String name,
			@JsonProperty(required = true) String date,
			@JsonProperty(required = true) List<String> participants) {
	}

	public static void main(String[] args)
			throws JsonMappingException, JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		JsonSchemaService jsonSchemaService = new JsonSchemaService();

		var response = client.chatCompletions.create(r -> r
				.addMessages(SystemMessage.of("Extract the event information."),
						UserMessage.of(
								"Alice and Bob are going to a science fair on Friday."))
				.responseFormat(
						jsonSchemaService.createStrictResponseFormat(CalendarEvent.class))
				.model("gpt-4o-mini"));
		String content = response.choices().get(0).message().content();
		System.out.println(content);

		ObjectMapper objectMapper = new ObjectMapper();
		CalendarEvent calendarEvent = objectMapper.readValue(content,
				CalendarEvent.class);
		System.out.println(calendarEvent);

	}

}
