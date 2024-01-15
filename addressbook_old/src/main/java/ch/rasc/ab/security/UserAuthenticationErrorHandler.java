package ch.rasc.ab.security;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.ab.entity.AppUser;

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
			AppUser user = new JPAQuery(this.entityManager).from(QAppUser.appUser)
					.where(QAppUser.appUser.userName.eq((String) principal))
					.singleResult(QAppUser.appUser);
			if (user != null) {
				if (user.getFailedLogins() == null) {
					user.setFailedLogins(1);
				}
				else {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}

				if (user.getFailedLogins() > 10) {
					Calendar c = new GregorianCalendar();
					c.add(Calendar.MINUTE, 10);
					user.setLockedOut(c.getTime());
				}

			}
			else {
				LoggerFactory.getLogger(UserAuthenticationErrorHandler.class)
						.warn("Unknown user login attempt: {}", principal);
			}
		}
	}
}