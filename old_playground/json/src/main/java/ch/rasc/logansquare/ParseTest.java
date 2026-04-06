package ch.rasc.logansquare;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.boon.json.JsonParserAndMapper;
import org.boon.json.JsonParserFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import com.bluelinelabs.logansquare.JsonMapper;
import com.bluelinelabs.logansquare.LoganSquare;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class ParseTest {

	public static void main(String[] args) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new AfterburnerModule());
		try (InputStream is = ParseTest.class
				.getResourceAsStream("/testdata/largesample.json")) {
			String tiny = StreamUtils.copyToString(is, StandardCharsets.UTF_8);

			Response response = objectMapper.readValue(tiny, Response.class);
			response = LoganSquare.parse(tiny, Response.class);

			StopWatch sw = new StopWatch();
			sw.start("Jackson");
			for (int i = 0; i < 100_00; i++) {
				response = objectMapper.readValue(tiny, Response.class);
			}
			sw.stop();
			System.out.println(response.status);

			sw.start("LoganSquare");
			JsonMapper<Response> jm = LoganSquare.mapperFor(Response.class);
			for (int i = 0; i < 100_00; i++) {
				response = jm.parse(tiny);
			}
			sw.stop();
			System.out.println(response.status);

			JsonParserFactory factory = new JsonParserFactory();
			JsonParserAndMapper parser = factory.createFastParser();

			sw.start("boon");
			for (int i = 0; i < 100_00; i++) {
				response = parser.parse(Response.class, tiny);
			}
			sw.stop();
			System.out.println(response.status);

			System.out.println(sw.prettyPrint());

		}

	}

}
