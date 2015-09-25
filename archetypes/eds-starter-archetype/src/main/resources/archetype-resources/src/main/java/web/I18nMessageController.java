#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class I18nMessageController {

	private static final String JS_CONTENT_TYPE = "application/javascript;charset=UTF-8";

	private final ObjectMapper objectMapper;

	@Autowired
	public I18nMessageController(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
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
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

		Map<String, String> messages = new HashMap<>();
		Enumeration<String> e = rb.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			messages.put(key, rb.getString(key));
		}

		String output = "var i18n = " + objectMapper.writeValueAsString(messages) + ";";
		return output.getBytes(StandardCharsets.UTF_8);
	}

}
