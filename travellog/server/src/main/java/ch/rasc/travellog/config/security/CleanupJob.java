package ch.rasc.travellog.config.security;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static ch.rasc.travellog.db.tables.AppUser.APP_USER;

import java.time.LocalDateTime;

import org.jooq.DSLContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ch.rasc.travellog.config.AppProperties;

@Component
public class CleanupJob {

	private final DSLContext dsl;

	private final AppProperties appProperties;

	public CleanupJob(DSLContext dsl, AppProperties appProperties) {
		this.dsl = dsl;
		this.appProperties = appProperties;
	}

	@Scheduled(cron = "0 0 5 * * *")
	public void doCleanup() {

		// Delete all users that are expired for the configured amount of time
		if (this.appProperties.getExpiredUserMaxAge() != null) {
			this.dsl.delete(APP_USER).where(APP_USER.EXPIRED.le(
					LocalDateTime.now().minus(this.appProperties.getExpiredUserMaxAge())))
					.execute();
		}

		// Delete all users that created a registration but never confirmed it
		this.dsl.delete(
				APP_USER).where(
						APP_USER.CONFIRMATION_TOKEN_CREATED
								.le(LocalDateTime.now()
										.minus(this.appProperties
												.getSignupNotConfirmedUserMaxAge()))
								.and(APP_USER.CONFIRMATION_TOKEN.isNotNull())
								.and(APP_USER.EMAIL_NEW.isNull())
								.and(APP_USER.ENABLED.eq(false)))
				.execute();

		// Delete all email change requests where the confirmation token is invalid
		this.dsl.update(APP_USER).setNull(APP_USER.CONFIRMATION_TOKEN_CREATED)
				.setNull(APP_USER.CONFIRMATION_TOKEN).setNull(APP_USER.EMAIL_NEW)
				.where(APP_USER.EMAIL_NEW.isNotNull().and(
						APP_USER.CONFIRMATION_TOKEN_CREATED.le(LocalDateTime.now().minus(
								this.appProperties.getSignupNotConfirmedUserMaxAge()))))
				.execute();

		// Inactivate all users where the last access was older than the configured max
		// age
		if (this.appProperties.getInactiveUserMaxAge() != null) {
			this.dsl.update(APP_USER).set(APP_USER.EXPIRED, LocalDateTime.now())
					.setNull(APP_USER.PASSWORD_HASH)
					.where(APP_USER.LAST_ACCESS.le(LocalDateTime.now()
							.minus(this.appProperties.getInactiveUserMaxAge())))
					.execute();
		}

		// Delete all sessions where the last access is older than the configured max age
		this.dsl.delete(APP_SESSION).where(APP_SESSION.LAST_ACCESS.le(
				LocalDateTime.now().minus(this.appProperties.getInactiveSessionMaxAge())))
				.execute();

	}

}
