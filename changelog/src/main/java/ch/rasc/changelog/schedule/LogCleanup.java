package ch.rasc.changelog.schedule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.changelog.entity.LoggingEvent;
import ch.rasc.changelog.entity.QLoggingEvent;

@Component
public class LogCleanup {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {

		// Delete all log entries that are older than 2 months
		DateTime yesterday = DateTime.now().minusMonths(2);

		for (LoggingEvent le : new JPAQuery(this.entityManager)
				.from(QLoggingEvent.loggingEvent)
				.where(QLoggingEvent.loggingEvent.timestmp
						.loe(yesterday.toDate().getTime()))
				.list(QLoggingEvent.loggingEvent)) {
			this.entityManager.remove(le);
		}

	}
}
