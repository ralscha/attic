package ch.rasc.s3selectdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.CompressionType;
import software.amazon.awssdk.services.s3.model.ExpressionType;
import software.amazon.awssdk.services.s3.model.InputSerialization;
import software.amazon.awssdk.services.s3.model.JSONInput;
import software.amazon.awssdk.services.s3.model.JSONOutput;
import software.amazon.awssdk.services.s3.model.JSONType;
import software.amazon.awssdk.services.s3.model.OutputSerialization;
import software.amazon.awssdk.services.s3.model.SelectObjectContentRequest;
import software.amazon.awssdk.services.s3.model.SelectObjectContentResponseHandler;

public class Main {
	public static void main(String[] args) {

		try (ProfileCredentialsProvider profileCredential = ProfileCredentialsProvider
				.create("home");
				S3AsyncClient s3Client = S3AsyncClient.builder().region(Region.US_EAST_1)
						.credentialsProvider(profileCredential).build()) {
			String query = "select p.id,p.name from S3Object[*].pokemon[*] p";
			selectObject(s3Client, query, false);

			query = "select p.id,p.name,p.type from S3Object[*].pokemon[*] p where p.type[0] = 'Fire' or p.type[1] = 'Fire'";
			selectObject(s3Client, query, false);

			query = "select p from S3Object[*].pokemon[*] p where p.name = 'Charmander'";
			selectObject(s3Client, query, false);

			query = "select count(*) from S3Object[*].pokemon[*] p";
			selectObject(s3Client, query, true);
		}

		// if the application runs on AWS
		// S3AsyncClient s3Client = S3AsyncClient.builder().build();

	}

	private static void selectObject(S3AsyncClient s3Client, String query,
			boolean compressed) {
		String bucket = "rasc-select-demo";
		String keyUncompressed = "pokedex.json";
		String keyCompressed = "pokedex.json.bz2";

		SelectObjectContentRequest request = SelectObjectContentRequest.builder()
				.bucket(bucket).key(compressed ? keyCompressed : keyUncompressed)
				.expression(query).expressionType(ExpressionType.SQL)
				.inputSerialization(InputSerialization.builder()
						.json(JSONInput.builder().type(JSONType.DOCUMENT).build())
						.compressionType(
								compressed ? CompressionType.BZIP2 : CompressionType.NONE)
						.build())
				.outputSerialization(OutputSerialization.builder()
						.json(JSONOutput.builder().build()).build())
				.build();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		SelectObjectContentResponseHandler.Visitor visitor = SelectObjectContentResponseHandler.Visitor
				.builder().onRecords(r -> {
					System.out.println("record event");
					try {
						baos.write(r.payload().asByteArray());
					}
					catch (IOException e) {
						throw new RuntimeException(e);
					}
				}).onStats(se -> {
					System.out.println("stats event: ");
					System.out
							.println("bytes processed: " + se.details().bytesProcessed());
				}).onEnd(_ -> System.out.println("end event")).build();

		SelectObjectContentResponseHandler handler = SelectObjectContentResponseHandler
				.builder().subscriber(visitor).build();

		CompletableFuture<Void> future = s3Client.selectObjectContent(request, handler);
		future.join();

		System.out.println(new String(baos.toByteArray()));
	}
}