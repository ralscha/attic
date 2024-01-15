package ch.rasc.bitprototype.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.bitprototype.entity.LogEvent;
import ch.rasc.bitprototype.entity.QLogEvent;

@Controller
public class LogEventExport {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MessageSource messageSource;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/logEventExport.txt", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public void logEventExport(HttpServletResponse response,
			@RequestParam(required = false) final String level) throws Exception {

		response.setContentType("text/plain");
		response.addHeader("Content-disposition", "attachment;filename=logs.txt");

		String separator = new String(new char[140]).replace("\0", "=");

		try (OutputStream out = response.getOutputStream();
				PrintWriter pw = new PrintWriter(out)) {

			List<LogEvent> events;
			if (StringUtils.isNotBlank(level)) {
				events = new JPAQuery(this.entityManager).from(QLogEvent.logEvent)
						.where(QLogEvent.logEvent.level.eq(level))
						.list(QLogEvent.logEvent);
			}
			else {
				events = new JPAQuery(this.entityManager).from(QLogEvent.logEvent)
						.list(QLogEvent.logEvent);
			}

			for (LogEvent event : events) {

				pw.println("Time   : " + event.getEventDate());
				pw.println("User   : " + StringUtils.defaultString(event.getUserName()));
				pw.println("IP     : " + StringUtils.defaultString(event.getIp()));
				pw.println("Level  : " + event.getLevel());

				pw.println("Source : " + event.getSource());
				pw.println("Message: " + event.getMessage());

				pw.println(StringUtils.defaultString(event.getException()));
				pw.println(separator);

			}

		}

	}

}
