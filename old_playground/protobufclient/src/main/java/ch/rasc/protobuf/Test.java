package ch.rasc.protobuf;

import java.io.IOException;
import java.util.Arrays;

import ch.rasc.protobuf.SensorDataOuterClass.SensorData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Test {

	public static void main(String[] args) throws IOException {
		SensorData sd = SensorData.newBuilder().setId("1").setRx(100).setTx(200).build();

		System.out.println(sd);

		System.out.println(Arrays.toString(sd.toByteArray()));
		System.out.println(sd.toByteArray().length);

		String json = "{\"id\":\"1\",\"tx\":200,\"rx\":100}";
		System.out.println(json.length());

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),
				sd.toByteArray());
		Request request = new Request.Builder().url("http://localhost:8080/receiver")
				.post(body).build();
		client.newCall(request).execute();

	}

}
