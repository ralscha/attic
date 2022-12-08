package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SuccessTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGoodQueryRequest() {
		Client client = Client.newClient();
		client.setNutResponse(fetchNut());
		
		testValidCmd(client, "query", 0);
		testValidCmd(client, "query", 0);
		
//		t.Run("A=1", func(t *testing.T) { testValidCmd(t, client, "query", ssp.TIFIPMatched) })
//		// again with same identity but next nut
//		t.Run("A=2", func(t *testing.T) { testValidCmd(t, client, "query", ssp.TIFIPMatched) })
	}

	private CliResponse testValidCmd(Client client, String cmd, int expectedTIF) {
		ClientBody body = new ClientBody();
		body.setVersion(Set.of(1));
		body.setCmd(cmd);
		
		return testValidReq(client, body, expectedTIF);
	}

	private CliResponse testValidReq(Client client, ClientBody body, int expectedTIF) {
		//cliURL := c.CliURL(c.CurrentNut, c.NutResponse.Can)

				//cli.Server = c.LastServerResponse
				
				if (body.getCmd().equals("ident")) {
					body.setVuk(Base64.getUrlEncoder().withoutPadding()
							.encodeToString(client.getIdentity().getVuk().getEncoded()));
					body.setSuk(Base64.getUrlEncoder().withoutPadding()
							.encodeToString(client.getIdentity().getSuk().getEncoded()));					
				}
				else if (body.getCmd().equals("enable") ||body.getCmd().equals("remove")) {
					body.setIdk(Base64.getUrlEncoder().withoutPadding()
							.encodeToString(client.getIdentity().getIdk().getEncoded()));
					body.setVuk(Base64.getUrlEncoder().withoutPadding()
							.encodeToString(client.getIdentity().getVuk().getEncoded()));
					byte[] ids = TestUtil.sign(client.getIdentity().getIdkPrivate(), body.encode() + ""/*client.getServer()*/);
					byte[] urs = TestUtil.sign(client.getIdentity().getVukPrivate(), body.encode() + ""/*client.getServer()*/);
				}
				else {
					byte[] ids = TestUtil.sign(client.getIdentity().getIdkPrivate(), body.encode() + ""/*client.getServer()*/);
				}
					
				return c.MakeRawCliRequest(cliURL, cli)
	}
		
	private NutResponse fetchNut() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		var request = new HttpEntity<>(headers);
		var response = this.restTemplate.exchange("/nut.sqrl", HttpMethod.GET, request,
				MultiValueMap.class);
		MultiValueMap<String, String> body = response.getBody();

		return new NutResponse(body.getFirst("nut"), body.getFirst("pag"),
				body.getFirst("exp"), body.getFirst("can"));
	}
}
