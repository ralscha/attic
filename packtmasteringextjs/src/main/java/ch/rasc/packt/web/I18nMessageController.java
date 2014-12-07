package ch.rasc.packt.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.ralscha.extdirectspring.util.JsonHandler;

@Controller
public class I18nMessageController implements InitializingBean {

	private static final String JS_CONTENT_TYPE = "application/javascript;charset=UTF-8";

	@Autowired(required = false)
	private JsonHandler jsonHandler;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jsonHandler == null) {
			jsonHandler = new JsonHandler();
		}
	}

	@RequestMapping(value = "/resources/i18n/v1/{language}.js",
			method = RequestMethod.GET)
	public void i18n(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String language) throws IOException {
		Locale locale;
		if ("de".equals(language)) {
			locale = Locale.GERMAN;
		}
		else {
			locale = Locale.ENGLISH;
		}
		byte[] output = buildResponse(locale);
		ExtDirectSpringUtil.handleCacheableResponse(request, response, output,
				JS_CONTENT_TYPE);
	}

	private byte[] buildResponse(Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

		Map<String, String> messages = new HashMap<>();
		Enumeration<String> e = rb.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			messages.put(key, rb.getString(key));
		}

		String output = "var i18n = " + jsonHandler.writeValueAsString(messages) + ";";
		return output.getBytes(StandardCharsets.UTF_8);
	}

}
