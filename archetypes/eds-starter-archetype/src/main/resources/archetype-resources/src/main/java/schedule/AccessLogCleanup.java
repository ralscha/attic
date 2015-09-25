#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.schedule;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.QAccessLog;

import com.mysema.query.jpa.impl.JPADeleteClause;

@Component
public class AccessLogCleanup {

	private final EntityManager entityManager;

	@Autowired
	public AccessLogCleanup(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Scheduled(cron = "0 0 5 * * *")
	public void doCleanup() {
		// Delete all access logs that are older than 6 months
		LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);
		new JPADeleteClause(entityManager, QAccessLog.accessLog).where(
				QAccessLog.accessLog.logIn.loe(sixMonthAgo)).execute();
	}

}
