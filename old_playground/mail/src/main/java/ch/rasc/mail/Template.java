package ch.rasc.mail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import httl.Engine;
import httl.util.IOUtils;

public class Template {

	public static void main(String[] args)
			throws ParseException, IOException, EmailException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", "A");
		parameters.put("books", "B");

		Engine engine = Engine.getEngine();
		httl.Template t = engine.parseTemplate("<img alt=\"${user}\" src=\"${books}\">");
		System.out.println(t.evaluate(parameters));

		StringWriter sw = new StringWriter();
		try (Reader in = new InputStreamReader(
				Template.class.getResourceAsStream("/basic.html"),
				StandardCharsets.UTF_8)) {
			IOUtils.copy(in, sw);
		}
		System.out.println(sw.toString());

		HtmlEmail email = new HtmlEmail();
		email.setHostName("localhost");
		email.setFrom("boss@test.com", "The Boss");
		email.addTo("test@test.com", "John Test");
		email.setSubject("ink test");

		email.setHtmlMsg(sw.toString());

		email.setTextMsg("a text");

		email.send();

	}

}
