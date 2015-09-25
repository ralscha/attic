#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.QAccessLog;

import com.mysema.query.jpa.impl.JPAUpdateClause;

@Component
public class SessionDestroyedListener implements
		ApplicationListener<SessionDestroyedEvent> {

	private final EntityManager entityManager;

	@Autowired
	public SessionDestroyedListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void onApplicationEvent(SessionDestroyedEvent event) {
		new JPAUpdateClause(entityManager, QAccessLog.accessLog)
				.set(QAccessLog.accessLog.logOut, LocalDateTime.now())
				.where(QAccessLog.accessLog.sessionId.eq(event.getId())).execute();
	}

}
