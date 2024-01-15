package ch.rasc.changelog.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.changelog.entity.QUser;
import ch.rasc.changelog.entity.User;

@Component
public class UserAuthenticationErrorHandler
		implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof String) {
			User user = new JPAQuery(this.entityManager).from(QUser.user)
					.where(QUser.user.userName.eq((String) principal))
					.singleResult(QUser.user);
			if (user != null) {
				if (user.getFailedLogins() == null) {
					user.setFailedLogins(1);
				}
				else {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}

				if (user.getFailedLogins() > 10) {
					user.setLockedOut(DateTime.now().plusMinutes(10));
				}

			}
			else {
				LoggerFactory.getLogger(UserAuthenticationErrorHandler.class)
						.warn("Unknown user login attempt: {}", principal);
			}
		}
	}
}