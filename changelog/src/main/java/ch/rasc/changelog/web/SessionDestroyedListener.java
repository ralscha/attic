package ch.rasc.changelog.web;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAUpdateClause;

import ch.rasc.changelog.entity.QAccessLog;

@Component
public class SessionDestroyedListener
		implements ApplicationListener<SessionDestroyedEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(SessionDestroyedEvent event) {
		new JPAUpdateClause(this.entityManager, QAccessLog.accessLog)
				.set(QAccessLog.accessLog.logOut, DateTime.now())
				.where(QAccessLog.accessLog.sessionId.eq(event.getId())).execute();
	}

}
