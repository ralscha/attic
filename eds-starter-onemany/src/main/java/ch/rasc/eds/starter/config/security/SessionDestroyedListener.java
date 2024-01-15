package ch.rasc.eds.starter.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.eds.starter.entity.QAccessLog;
import ch.rasc.eds.starter.service.AccessLogService;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.wampspring.EventMessenger;

@Component
public class SessionDestroyedListener
		implements ApplicationListener<SessionDestroyedEvent> {

	private final JPAQueryFactory jpaQueryFactory;

	private final EventMessenger eventMessenger;

	@Autowired
	public SessionDestroyedListener(JPAQueryFactory jpaQueryFactory,
			EventMessenger eventMessenger) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.eventMessenger = eventMessenger;
	}

	@Override
	@Transactional
	public void onApplicationEvent(SessionDestroyedEvent event) {
		Long accessLogId = this.jpaQueryFactory.select(QAccessLog.accessLog.id)
				.from(QAccessLog.accessLog)
				.where(QAccessLog.accessLog.sessionId.eq(event.getId()))
				.orderBy(QAccessLog.accessLog.loginTimestamp.desc()).fetchFirst();

		if (accessLogId != null) {
			AccessLogService.CURRENT_LOGGED_IN_USERS.remove(accessLogId);
			this.eventMessenger.sendToAll("/queue/logout", accessLogId);
		}
	}

}
