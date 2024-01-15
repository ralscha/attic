package ch.rasc.ab.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.ab.entity.AppUser;

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
			AppUser user = this.entityManager.find(AppUser.class,
					((JpaUserDetails) principal).getAppUserDbId());
			user.setLockedOut(null);
			user.setFailedLogins(null);
		}
	}
}