package ch.rasc.e4ds.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HtmlController {

	private final String indexHtml;

	private final String appJs;

	private final String loginJs;

	private final Environment environment;

	@Autowired
	public HtmlController(Environment environment, ServletContext servletContext)
			throws IOException {
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
						(String) servletContext.getAttribute("app_css"));

		this.appJs = (String) servletContext.getAttribute("app_js");
		this.loginJs = (String) servletContext.getAttribute("login_js");
	}

	@RequestMapping(value = { "/", "/index.html" }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index(HttpServletResponse response, Locale locale) {
		response.setContentType("text/html; charset=utf-8");
		return this.indexHtml + createI18nScript(this.environment, locale) + this.appJs
				+ createExtJSLocale(this.environment, locale) + "</body></html>";
	}

	@RequestMapping(value = { "/login.html" }, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String login(HttpSession session, HttpServletResponse response, Locale locale) {
		response.setContentType("text/html; charset=utf-8");
		return this.indexHtml + createI18nScript(this.environment, locale) + this.loginJs
				+ createExtJSLocale(this.environment, locale)
				+ createLoginErrorScript(session) + "</body></html>";
	}

	private static String createLoginErrorScript(HttpSession session) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<script>");
		sb.append("Ext.onReady(function() {");
		sb.append("Ext.fly('circularG').destroy();");
		if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
			sb.append("E4ds.ux.window.Notification.error(i18n.error, i18n.login_failed);");
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		sb.append("});");
		sb.append("</script>");
		return sb.toString();
	}

	private static String createExtJSLocale(Environment environment, Locale locale) {
		String extjsLocale = "";
		if (locale != null && locale.getLanguage().toLowerCase().equals("de")) {
			String extjsVersion = environment.getProperty("extjs.version");
			extjsLocale = "<script src=\"resources/extjs-gpl/" + extjsVersion
					+ "/locale/ext-lang-de.js\"></script>";
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
