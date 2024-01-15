package ch.rasc.proto.config.security;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mapdb.DB;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import ch.rasc.proto.config.DbManager;
import ch.rasc.proto.entity.ConfigurationKey;
import ch.rasc.proto.entity.User;

@Component
public class UserAuthenticationErrorHandler implements
		ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private final DbManager dbManager;

	private Integer loginLockAttempts;

	private Integer loginLockMinutes;

	@Autowired
	public UserAuthenticationErrorHandler(DbManager dbManager) {
		this.dbManager = dbManager;
		configure();
	}

	public void configure() {
		String value = this.dbManager.readConfig(ConfigurationKey.LOGIN_LOCK_ATTEMPTS);
		if (value != null) {
			this.loginLockAttempts = Integer.valueOf(value);
		}
		else {
			this.loginLockAttempts = null;
		}

		value = this.dbManager.readConfig(ConfigurationKey.LOGIN_LOCK_MINUTES);
		if (value != null) {
			this.loginLockMinutes = Integer.valueOf(value);
		}
		else {
			this.loginLockMinutes = null;
		}
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		this.dbManager.runInTxWithoutResult(db -> {
			updateLockedProperties(db, event);
		});
	}

	private void updateLockedProperties(DB db,
			AuthenticationFailureBadCredentialsEvent event) {
		Object principal = event.getAuthentication().getPrincipal();

		if (this.loginLockAttempts != null && principal instanceof String) {
			Optional<User> userOptional = DbManager.getAll(db, User.class).stream()
					.filter(u -> u.getEmail().equals(principal))
					.filter(u -> !u.isDeleted()).findFirst();

			if (userOptional.isPresent()) {
				User user = userOptional.get();
				if (user.getFailedLogins() == null) {
					user.setFailedLogins(1);
				}
				else {
					user.setFailedLogins(user.getFailedLogins() + 1);
				}

				if (user.getFailedLogins() >= this.loginLockAttempts) {
					if (this.loginLockMinutes != null) {
						user.setLockedOutUntil(LocalDateTime.now().plusMinutes(
								this.loginLockMinutes));
					}
					else {
						user.setLockedOutUntil(LocalDateTime.now().plusYears(1000));
					}
				}
				DbManager.put(db, user);
			}
			else {
				LoggerFactory.getLogger(UserAuthenticationErrorHandler.class).warn(
						"Unknown user login attempt: {}", principal);
			}
		}
		else {
			LoggerFactory.getLogger(UserAuthenticationErrorHandler.class).warn(
					"Invalid login attempt: {}", principal);
		}
	}

}
