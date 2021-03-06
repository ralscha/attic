#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ${package}.entity.LoggingEvent;
import ${package}.entity.LoggingEventException;
import ${package}.entity.LoggingEventProperty;
import ${package}.entity.QLoggingEvent;

import com.mysema.query.jpa.impl.JPAQuery;

@Controller
public class LoggingEventExport {

	private final EntityManager entityManager;

	private final MessageSource messageSource;

	@Autowired
	public LoggingEventExport(MessageSource messageSource, EntityManager entityManager) {
		this.messageSource = messageSource;
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/loggingEventExport.txt", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public void loggingEventExport(HttpServletResponse response, @RequestParam(
			required = false) final String level, Locale locale) throws Exception {

		response.setContentType("text/plain");
		response.addHeader("Content-disposition", "attachment;filename=logs.txt");

		String separator = new String(new char[140]).replace("${symbol_escape}0", "=");

		try (OutputStream out = response.getOutputStream();
				PrintWriter pw = new PrintWriter(out)) {

			List<LoggingEvent> events;
			if (StringUtils.hasText(level)) {
				events = new JPAQuery(entityManager).from(QLoggingEvent.loggingEvent)
						.where(QLoggingEvent.loggingEvent.levelString.eq(level))
						.list(QLoggingEvent.loggingEvent);
			}
			else {
				events = new JPAQuery(entityManager).from(QLoggingEvent.loggingEvent)
						.list(QLoggingEvent.loggingEvent);
			}

			for (LoggingEvent event : events) {
				String userName = "";
				String ip = "";

				Set<LoggingEventProperty> properties = event.getLoggingEventProperty();
				for (LoggingEventProperty prop : properties) {
					if ("userName".equals(prop.getId().getMappedKey())) {
						userName = prop.getMappedValue();
					}
					else if ("ip".equals(prop.getId().getMappedKey())) {
						ip = prop.getMappedValue();
					}
				}

				String timestampText = messageSource.getMessage("logevents_timestamp",
						null, locale);
				String userText = messageSource.getMessage("user", null, locale);
				String ipText = messageSource.getMessage("logevents_ip", null, locale);
				String levelText = messageSource.getMessage("logevents_level", null,
						locale);
				String classText = messageSource.getMessage("logevents_class", null,
						locale);
				String methodText = messageSource.getMessage("logevents_method", null,
						locale);
				String lineText = messageSource
						.getMessage("logevents_line", null, locale);
				String messageText = messageSource.getMessage("logevents_message", null,
						locale);

				String[] texts = { timestampText, userText, ipText, levelText, classText,
						methodText, lineText, messageText };
				int maxLength = Arrays.stream(texts).mapToInt(String::length)
						.max().getAsInt();

				timestampText = padRight(timestampText, maxLength);
				userText = padRight(userText, maxLength);
				ipText = padRight(ipText, maxLength);
				levelText = padRight(levelText, maxLength);
				classText = padRight(classText, maxLength);
				methodText = padRight(methodText, maxLength);
				lineText = padRight(lineText, maxLength);
				messageText = padRight(messageText, maxLength);

				pw.println(timestampText
						+ ": "
						+ ZonedDateTime.ofInstant(
								Instant.ofEpochMilli(event.getTimestmp().longValue()),
								ZoneOffset.UTC));

				pw.println(userText + ": " + userName);
				pw.println(ipText + ": " + ip);
				pw.println(levelText + ": " + event.getLevelString());
				pw.println(classText + ": " + event.getCallerClass());
				pw.println(methodText + ": " + event.getCallerMethod());
				pw.println(lineText + ": " + event.getCallerLine());
				pw.println(messageText + ": " + event.getFormattedMessage());

				Set<LoggingEventException> stacktrace = event.getLoggingEventException();

				if (!stacktrace.isEmpty()) {
					StringBuilder sb = new StringBuilder(300);
					stacktrace.stream()
							.sorted(Comparator.comparing(e -> e.getId().getI()))
							.forEach(e -> sb.append(e.getTraceLine()).append("${symbol_escape}n"));
					pw.println(sb);
				}
				pw.println(separator);
			}

		}

	}

	private static String padRight(String text, int maxLength) {
		int pad = maxLength - text.length();
		if (pad > 0) {
			return text + new String(new char[pad]).replace("${symbol_escape}0", " ");
		}

		return text;
	}
}
