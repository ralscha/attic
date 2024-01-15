package ch.rasc.eds.starter.config.security;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import ch.rasc.eds.starter.entity.Configuration;
import ch.rasc.eds.starter.entity.ConfigurationKey;
import ch.rasc.eds.starter.entity.QConfiguration;
import ch.rasc.eds.starter.entity.QUser;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.edsutil.JPAQueryFactory;

@Component
public class UserAuthenticationErrorHandler
		implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private final JPAQueryFactory jpaQueryFactory;

	private Integer loginLockAttempts;

	private Integer loginLockMinutes;

	private final TransactionTemplate transactionTemplate;

	@Autowired
	public UserAuthenticationErrorHandler(JPAQueryFactory jpaQueryFactory,
			TransactionTemplate transactionTemplate) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.transactionTemplate = transactionTemplate;

		transactionTemplate.execute(ts -> {
			configure();
			return null;
		});
	}

	public void configure() {
		Configuration conf = readConfiguration(ConfigurationKey.LOGIN_LOCK_ATTEMPTS);
		if (conf != null && conf.getConfValue() != null) {
			this.loginLockAttempts = Integer.valueOf(conf.getConfValue());
		}
		else {
			this.loginLockAttempts = null;
		}

		conf = readConfiguration(ConfigurationKey.LOGIN_LOCK_MINUTES);
		if (conf != null && conf.getConfValue() != null) {
			this.loginLockMinutes = Integer.valueOf(conf.getConfValue());
		}
		else {
			this.loginLockMinutes = null;
		}
	}

	public Configuration readConfiguration(ConfigurationKey key) {
		return this.jpaQueryFactory.selectFrom(QConfiguration.configuration)
				.where(QConfiguration.configuration.confKey.eq(key)).fetchFirst();
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		this.transactionTemplate.execute(ts -> {
			updateLockedProperties(event);
			return null;
		});
	}

	private void updateLockedProperties(AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();

		if (this.loginLockAttempts != null && principal instanceof String) {
			User user = this.jpaQueryFactory.selectFrom(QUser.user)
					.where(QUser.user.loginName.eq((String) principal))
					.where(QUser.user.deleted.isFalse()).fetchFirst();
			if (user != null) {
				if (user.getFailedLogins() == null) {
					user.setFailedLogins(1);
				}
				else {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}

				if (user.getFailedLogins() >= this.loginLockAttempts) {
					if (this.loginLockMinutes != null) {
						user.setLockedOutUntil(ZonedDateTime.now(ZoneOffset.UTC)
								.plusMinutes(this.loginLockMinutes));
					}
					else {
						user.setLockedOutUntil(
								ZonedDateTime.now(ZoneOffset.UTC).plusYears(1000));
					}
				}
				this.jpaQueryFactory.getEntityManager().merge(user);
			}
			else {
				LoggerFactory.getLogger(UserAuthenticationErrorHandler.class)
						.warn("Unknown user login attempt: {}", principal);
			}
		}
		else {
			LoggerFactory.getLogger(UserAuthenticationErrorHandler.class)
					.warn("Invalid login attempt: {}", principal);
		}
	}

}
