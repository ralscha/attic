package ch.rasc.webapp.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.rasc.webapp.Application;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
public class AppTest {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void testPages() throws Exception {
		WebClient webClient = new WebClient();
		HtmlPage page = webClient.getPage("http://localhost:" + this.port);

		assertEquals("Congratulations! Spring is running!", page.getTitleText());

		page = webClient.getPage("http://localhost:" + this.port + "/?locale=de_de");
		assertEquals("Glückwünsche! Spring läuft!", page.getTitleText());

		page = webClient
				.getPage("http://localhost:" + this.port + "/myController/doSomething");
		assertEquals("Login Page", page.getTitleText());

		page = login(page, "wrong", "wrong");
		assertEquals("Login Page", page.getTitleText());
		assertTrue(page.asText()
				.contains("Your login attempt was not successful, try again."));
		assertTrue(page.asText().contains("Reason: Bad credentials"));

		page = login(page, "jimi", "jimispassword");
		assertEquals("Response", page.getTitleText());
		assertEquals("jimi", page.getElementById("me").asText());

	}

	private static HtmlPage login(HtmlPage page, String userName, String password)
			throws IOException {
		HtmlForm form = page.getFormByName("f");
		HtmlSubmitInput submitButton = form.getInputByName("submit");
		form.getInputByName("username").setValueAttribute(userName);
		form.getInputByName("password").setValueAttribute(password);
		return submitButton.click();
	}
}
