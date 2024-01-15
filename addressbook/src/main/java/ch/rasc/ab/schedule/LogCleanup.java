package ch.rasc.ab.schedule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.ab.entity.QLogEvent;

import com.mysema.query.jpa.impl.JPADeleteClause;

@Component
public class LogCleanup {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {

		// Delete all log events that are older than 2 months
		DateTime twoMonthsAgo = DateTime.now().minusMonths(2);
		new JPADeleteClause(entityManager, QLogEvent.logEvent).where(
				QLogEvent.logEvent.eventDate.loe(twoMonthsAgo)).execute();

	}
}
