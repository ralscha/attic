#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ${package}.Application;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
public class CounterJspTest {

	@Value("${symbol_dollar}{local.server.port}")
	private int port;

	@Test
	public void testCounterPage() throws Exception {
		WebClient webClient = new WebClient();
		HtmlPage page = webClient.getPage("http://localhost:" + port);
		assertEquals("webapp", page.getTitleText());
		assertTrue(page.asText().contains("No: 1"));

		page = webClient.getPage("http://localhost:" + port);
		assertTrue(page.asText().contains("No: 2"));

		webClient = new WebClient();
		page = webClient.getPage("http://localhost:" + port);
		assertTrue(page.asText().contains("No: 1"));
	}
}
