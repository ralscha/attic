package ch.rasc.eds.starter.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.rasc.eds.starter.config.AppProperties;

@Controller
public class HtmlController {

	private static final String END_TAGS = "</body></html>";

	private final String indexHtml;

	private final String appJs;

	private final String loginJs;

	private final String passwordResetJs;

	private final Environment environment;

	@Autowired
	public HtmlController(Environment environment, ServletContext servletContext,
			AppProperties appProperties) throws IOException {
		this.environment = environment;

		ClassPathResource cp = new ClassPathResource("loader.css");
		String loadercss;
		try (InputStream is = cp.getInputStream()) {
			loadercss = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
		}

		cp = new ClassPathResource("index.template");
		String htmlTemplate;
		try (InputStream is = cp.getInputStream()) {
			htmlTemplate = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
		}

		this.indexHtml = htmlTemplate.replace("application.loader_css", loadercss)
				.replace("application.app_css",
						(String) servletContext.getAttribute("app_css"))
				.replace("{appProperties.applicationName}",
						appProperties.getApplicationName());

		this.appJs = (String) servletContext.getAttribute("app_js");
		this.loginJs = (String) servletContext.getAttribute("login_js");
		this.passwordResetJs = (String) servletContext.getAttribute("passwordreset_js");
	}

	@RequestMapping(value = { "/", "/index.html" }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index(HttpServletResponse response, Locale locale) {
		response.setContentType("text/html; charset=utf-8");
		return this.indexHtml + createI18nScript(this.environment, locale) + this.appJs
				+ createExtJSLocale(this.environment, locale) + END_TAGS;
	}

	@RequestMapping(value = { "/login.html" }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String login(HttpServletResponse response, Locale locale) {
		response.setContentType("text/html; charset=utf-8");
		return this.indexHtml + createI18nScript(this.environment, locale) + this.loginJs
				+ createExtJSLocale(this.environment, locale) + END_TAGS;
	}

	@RequestMapping(value = { "/passwordreset.html" },
			produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String passwordreset(HttpServletResponse response, Locale locale, String token)
			throws IOException {
		if (!StringUtils.hasText(token)) {
			response.sendRedirect("/index.html");
		}

		response.setContentType("text/html; charset=utf-8");
		return this.indexHtml + createI18nScript(this.environment, locale)
				+ "<script>var passwordResetToken = '" + token + "';</script>"
				+ this.passwordResetJs + createExtJSLocale(this.environment, locale)
				+ END_TAGS;
	}

	private static String createExtJSLocale(Environment environment, Locale locale) {
		String extjsLocale = "";
		if (locale != null && locale.getLanguage().toLowerCase().equals("de")) {
			String debug = "";
			if (environment.acceptsProfiles("development")) {
				debug = "-debug";
			}
			String extVersion = environment.getProperty("ext.version");
			extjsLocale = "<script src=\"resources/ext/" + extVersion
					+ "/locale/ext-locale-de" + debug + ".js\"></script>";
		}
		return extjsLocale;
	}

	private static String createI18nScript(Environment environment, Locale locale) {
		String i18nScript;
		if (environment.acceptsProfiles("development")) {
			i18nScript = "<script src=\"i18n.js\"></script>";
		}
		else {
			i18nScript = "<script src=\"i18n-" + locale + "_"
					+ environment.getProperty("application.version") + ".js\"></script>";
		}
		return i18nScript;
	}

}
