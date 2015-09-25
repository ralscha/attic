#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ${package}.entity.User;

@Component
public class UserAuthenticationSuccessfulHandler implements
		ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof JpaUserDetails) {
			User user = entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());
			user.setLockedOut(null);
			user.setFailedLogins(null);
		}
	}
}