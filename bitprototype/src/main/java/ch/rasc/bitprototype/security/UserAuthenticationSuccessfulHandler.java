package ch.rasc.bitprototype.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.bitprototype.entity.User;

@Component
public class UserAuthenticationSuccessfulHandler
		implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof JpaUserDetails) {
			User user = this.entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());
			user.setLockedOut(null);
			user.setFailedLogins(null);
		}
	}
}