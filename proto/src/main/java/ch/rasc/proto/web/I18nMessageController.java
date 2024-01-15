package ch.rasc.proto.web;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.rasc.proto.config.AppProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class I18nMessageController {

	private static final String JS_CONTENT_TYPE = "application/javascript;charset=UTF-8";

	private final ObjectMapper objectMapper;

	private final AppProperties appProperties;

	@Autowired
	public I18nMessageController(ObjectMapper objectMapper, AppProperties appProperties) {
		this.objectMapper = objectMapper;
		this.appProperties = appProperties;
	}

	@RequestMapping(value = "/i18n.js", method = RequestMethod.GET)
	public void i18n(HttpServletResponse response, Locale locale) throws IOException {

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
			Locale locale) throws IOException {

		byte[] output = buildResponse(locale);
		ExtDirectSpringUtil.handleCacheableResponse(request, response, output,
				JS_CONTENT_TYPE);
	}

	private byte[] buildResponse(Locale locale) throws JsonProcessingException {
		Map<String, String> messages = new HashMap<>();

		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Enumeration<String> e = rb.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			messages.put(key, rb.getString(key));
		}

		rb = ResourceBundle.getBundle("ValidationMessages", locale);
		e = rb.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			messages.put(key, rb.getString(key));
		}

		messages.put("app_title", this.appProperties.getApplicationName());

		String output = "var i18n = " + this.objectMapper.writeValueAsString(messages)
				+ ";";
		return output.getBytes(StandardCharsets.UTF_8);
	}

}
