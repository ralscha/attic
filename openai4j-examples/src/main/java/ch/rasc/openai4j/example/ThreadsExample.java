package ch.rasc.openai4j.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.threads.Thread;
import ch.rasc.openai4j.threads.ThreadCreateRequest;

public class ThreadsExample {
	public static void main(String[] args) throws JsonProcessingException {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		ThreadCreateRequest request = ThreadCreateRequest.builder()
				.addMessage(t -> t.userRole().content("hello"))
				.putMetadata("name", "ralph").build();
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.writeValueAsString(request));
		Thread response = client.threads.create(request);
		System.out.println(response);

		var r = client.threads.retrieve(response.id());
		System.out.println(r);

		var ru = client.threads.modify(response.id(), t -> t.putMetadata("name", "john"));
		System.out.println(ru);

		r = client.threads.retrieve(response.id());
		System.out.println(r);

		var df = client.threads.delete(response.id());
		System.out.println(df);
	}
}
