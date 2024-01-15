package ch.rasc.eds.starter.schedule;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.eds.starter.entity.QAccessLog;
import ch.rasc.edsutil.JPAQueryFactory;

@Component
public class AccessLogCleanup {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public AccessLogCleanup(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Transactional
	@Scheduled(cron = "0 0 5 * * *")
	public void doCleanup() {
		// Delete all access logs that are older than 6 months
		ZonedDateTime sixMonthAgo = ZonedDateTime.now(ZoneOffset.UTC).minusMonths(6);
		this.jpaQueryFactory.delete(QAccessLog.accessLog)
				.where(QAccessLog.accessLog.loginTimestamp.loe(sixMonthAgo)).execute();
	}

}
