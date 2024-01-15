package ch.rasc.changelog.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.ralscha.extdirectspring.util.JsonHandler;

@Controller
public class I18nMessageController implements InitializingBean {

	private static final String JS_CONTENT_TYPE = "application/javascript;charset=UTF-8";

	@Autowired(required = false)
	private JsonHandler jsonHandler;

	private final static String prefix = "var i18n = ";

	private final static String postfix = ";";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.jsonHandler == null) {
			this.jsonHandler = new JsonHandler();
		}
	}

	@RequestMapping(value = "/i18n.js", method = RequestMethod.GET)
	public void i18n(HttpServletResponse response, Locale locale)
			throws JsonGenerationException, JsonMappingException, IOException {

		response.setContentType(JS_CONTENT_TYPE);

		byte[] output = buildResponse(locale);
		response.setContentLength(output.length);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(output);
		out.flush();
	}

	@RequestMapping(value = "/i18n-{version}.js", method = RequestMethod.GET)
	public void i18n(HttpServletRequest request, HttpServletResponse response,
			Locale locale)
					throws JsonGenerationException, JsonMappingException, IOException {

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

		String output = prefix + this.jsonHandler.writeValueAsString(messages) + postfix;
		return output.getBytes(StandardCharsets.UTF_8);
	}

}
