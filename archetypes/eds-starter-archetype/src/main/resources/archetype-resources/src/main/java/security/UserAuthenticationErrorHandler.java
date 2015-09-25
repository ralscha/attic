#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.QUser;
import ${package}.entity.User;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class UserAuthenticationErrorHandler implements
		ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof String) {
			User user = new JPAQuery(entityManager).from(QUser.user)
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
					user.setLockedOut(LocalDateTime.now().plusMinutes(10));
				}

			}
			else {
				LoggerFactory.getLogger(UserAuthenticationErrorHandler.class).warn(
						"Unknown user login attempt: {}", principal);
			}
		}
	}
}