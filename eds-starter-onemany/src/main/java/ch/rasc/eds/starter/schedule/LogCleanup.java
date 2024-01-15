package ch.rasc.eds.starter.schedule;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.eds.starter.entity.QLoggingEvent;
import ch.rasc.edsutil.JPAQueryFactory;

@Component
public class LogCleanup {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public LogCleanup(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void doCleanup() {

		// Delete all log entries that are older than 2 months
		long twoMonthsAgo = ZonedDateTime.now(ZoneOffset.UTC).minusMonths(2)
				.toEpochSecond() * 1000L;

		this.jpaQueryFactory.delete(QLoggingEvent.loggingEvent)
				.where(QLoggingEvent.loggingEvent.timestmp.loe(twoMonthsAgo)).execute();

	}
}
