package ch.rasc.jacksonhibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Start.class)
@WebIntegrationTest({ "server.port=0" })
public class IntegrationTest {

	@Value("${local.server.port}")
	private int port;

	// RestTemplate template = new TestRestTemplate();

	@Test
	public void testPlayers() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet("http://localhost:" + this.port + "/players");
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		ContentType contentType = ContentType.get(entity);
		assertThat(contentType.getMimeType())
				.isEqualTo(ContentType.APPLICATION_JSON.getMimeType());

		String body = EntityUtils.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> json = mapper.readValue(body, List.class);
		assertThat(json).hasSize(7);

		for (int i = 0; i < 7; i++) {
			assertThat(json.get(i)).contains(MapEntry.entry("id", i + 1));
		}

	}

	@Test
	public void testTeams() throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet("http://localhost:" + this.port + "/teams");
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
		ContentType contentType = ContentType.get(entity);
		assertThat(contentType.getMimeType())
				.isEqualTo(ContentType.APPLICATION_JSON.getMimeType());

		String body = EntityUtils.toString(entity);
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> json = mapper.readValue(body, List.class);
		assertThat(json).hasSize(3);

		assertThat(json.get(0)).contains(MapEntry.entry("id", 1));
		assertThat(json.get(1)).contains(MapEntry.entry("id", 2));
		assertThat(json.get(2)).contains(MapEntry.entry("id", 3));

		List<Map<String, Object>> players = (List<Map<String, Object>>) json.get(0)
				.get("player");
		assertThat(players).hasSize(3);

		players = (List<Map<String, Object>>) json.get(1).get("player");
		assertThat(players).hasSize(2);

		players = (List<Map<String, Object>>) json.get(2).get("player");
		assertThat(players).hasSize(2);
	}
}
