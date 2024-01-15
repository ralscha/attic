package ch.rasc.eds.starter.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.eds.starter.entity.QUser;
import ch.rasc.edsutil.JPAQueryFactory;

@Component
public class UserAuthenticationSuccessfulHandler
		implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public UserAuthenticationSuccessfulHandler(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	@Transactional
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof JpaUserDetails) {
			Long userId = ((JpaUserDetails) principal).getUserDbId();

			this.jpaQueryFactory.update(QUser.user).setNull(QUser.user.lockedOutUntil)
					.setNull(QUser.user.failedLogins).where(QUser.user.id.eq(userId))
					.execute();

		}
	}
}