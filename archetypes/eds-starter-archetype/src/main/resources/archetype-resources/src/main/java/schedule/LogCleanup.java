#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.schedule;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.LoggingEvent;
import ${package}.entity.QLoggingEvent;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class LogCleanup {

	private final EntityManager entityManager;

	@Autowired
	public LogCleanup(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {

		// Delete all log entries that are older than 2 months
		long twoMonthsAgo = ZonedDateTime.now(ZoneOffset.UTC).minusMonths(2)
				.toEpochSecond() * 1000L;

		for (LoggingEvent le : new JPAQuery(entityManager)
				.from(QLoggingEvent.loggingEvent)
				.where(QLoggingEvent.loggingEvent.timestmp.loe(twoMonthsAgo))
				.list(QLoggingEvent.loggingEvent)) {
			entityManager.remove(le);
		}

	}
}
