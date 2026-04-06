package ch.rasc.openai4j.example;

import java.util.Arrays;
import java.util.List;

import ch.rasc.openai4j.OpenAIClient;
import ch.rasc.openai4j.embeddings.EmbeddingCreateRequest;
import ch.rasc.openai4j.embeddings.EmbeddingCreateRequest.EncodingFormat;

public class EmbeddingsExample {
	public static void main(String[] args) {
		String apiKey = Util.getApiKey();
		var client = OpenAIClient.create(c -> c.apiKey(apiKey));

		EmbeddingCreateRequest request = EmbeddingCreateRequest.builder()
				.input(List.of("HelloWorld", "HelloWorld2"))
				.encodingFormat(EncodingFormat.BASE64).model("text-embedding-3-large")
				.build();
		var response = client.embeddings.create(request);
		System.out.println(response.data().get(0).embedding().base64());

		request = EmbeddingCreateRequest.builder()
				.input(List.of("HelloWorld", "iPhone", "Monkey"))
				.model("text-embedding-3-small").build();
		var response2 = client.embeddings.create(request);
		for (var embedding : response2.data()) {
			System.out.println(Arrays.toString(embedding.embedding().doubleArray()));
		}

		response = client.embeddings.create(r -> r.input("HelloWorld")
				.encodingFormat(EncodingFormat.FLOAT).model("text-embedding-3-small"));
		double[] embeddings = response.data().get(0).embedding().doubleArray();
		System.out.println(Arrays.toString(embeddings));
		System.out.println(Arrays.equals(embeddings,
				response2.data().get(0).embedding().doubleArray()));

		response = client.embeddings.create(r -> r.input("iPhone")
				.encodingFormat(EncodingFormat.FLOAT).model("text-embedding-3-small"));
		embeddings = response.data().get(0).embedding().doubleArray();
		System.out.println(Arrays.toString(embeddings));
		System.out.println(Arrays.equals(embeddings,
				response2.data().get(1).embedding().doubleArray()));

		response = client.embeddings.create(r -> r.input("Monkey")
				.encodingFormat(EncodingFormat.FLOAT).model("text-embedding-3-small"));
		embeddings = response.data().get(0).embedding().doubleArray();
		System.out.println(Arrays.toString(embeddings));
		System.out.println(Arrays.equals(embeddings,
				response2.data().get(2).embedding().doubleArray()));

		response = client.embeddings.create(r -> r.input("Monkey").dimensions(256)
				.encodingFormat(EncodingFormat.FLOAT).model("text-embedding-3-small"));
		embeddings = response.data().get(0).embedding().doubleArray();
		System.out.println(embeddings.length);
	}
}
