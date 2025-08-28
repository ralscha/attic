package ch.rasc.travellog;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static ch.rasc.travellog.db.tables.AppUser.APP_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.rasc.travellog.config.security.CleanupJob;
import ch.rasc.travellog.db.tables.records.AppUserRecord;

class CleanupJobTest extends AbstractBaseTest {

  @Autowired
  private CleanupJob cleanupJob;

  @Test
  void testExpired() {

    long userId = getDsl()
        .insertInto(APP_USER, APP_USER.EMAIL, APP_USER.PASSWORD_HASH, APP_USER.AUTHORITY,
            APP_USER.ENABLED, APP_USER.EXPIRED, APP_USER.LAST_ACCESS,
            APP_USER.CONFIRMATION_TOKEN, APP_USER.CONFIRMATION_TOKEN_CREATED,
            APP_USER.PASSWORD_RESET_TOKEN, APP_USER.PASSWORD_RESET_TOKEN_CREATED)
        .values("expired@test.com", null, "USER", true, LocalDateTime.now(), null, null,
            null, null, null)
        .returning(APP_USER.ID).fetchOne().getId();

    this.cleanupJob.doCleanup();

    AppUserRecord user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId))
        .fetchOne();
    assertThat(user).isNotNull();

    getDsl().update(APP_USER)
        .set(APP_USER.EXPIRED,
            LocalDateTime.now().minus(getAppProperties().getExpiredUserMaxAge()))
        .where(APP_USER.ID.eq(userId)).execute();
    this.cleanupJob.doCleanup();

    user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId)).fetchOne();
    assertThat(user).isNull();
  }

  @Test
  void testSignUpNeverConfirmed() {
    long userId = getDsl()
        .insertInto(APP_USER, APP_USER.EMAIL, APP_USER.PASSWORD_HASH, APP_USER.AUTHORITY,
            APP_USER.ENABLED, APP_USER.EXPIRED, APP_USER.LAST_ACCESS,
            APP_USER.CONFIRMATION_TOKEN, APP_USER.CONFIRMATION_TOKEN_CREATED,
            APP_USER.PASSWORD_RESET_TOKEN, APP_USER.PASSWORD_RESET_TOKEN_CREATED)
        .values("signupneverconfirmed@test.com", getPasswordEncoder().encode("password"),
            "USER", false, null, null, "confirmToken", LocalDateTime.now(), null, null)
        .returning(APP_USER.ID).fetchOne().getId();

    this.cleanupJob.doCleanup();

    AppUserRecord user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId))
        .fetchOne();
    assertThat(user).isNotNull();

    getDsl().update(APP_USER)
        .set(APP_USER.CONFIRMATION_TOKEN_CREATED,
            LocalDateTime.now()
                .minus(getAppProperties().getSignupNotConfirmedUserMaxAge()))
        .where(APP_USER.ID.eq(userId)).execute();
    this.cleanupJob.doCleanup();

    user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId)).fetchOne();
    assertThat(user).isNull();
  }

  @Test
  void testEmailChangesNeverConfirmed() {
    long userId = getDsl()
        .insertInto(APP_USER, APP_USER.EMAIL, APP_USER.PASSWORD_HASH, APP_USER.AUTHORITY,
            APP_USER.ENABLED, APP_USER.EXPIRED, APP_USER.LAST_ACCESS,
            APP_USER.CONFIRMATION_TOKEN, APP_USER.CONFIRMATION_TOKEN_CREATED,
            APP_USER.PASSWORD_RESET_TOKEN, APP_USER.PASSWORD_RESET_TOKEN_CREATED,
            APP_USER.EMAIL_NEW)
        .values("emailchangeneverconfirmed@test.com",
            getPasswordEncoder().encode("password"), "USER", true, null, null,
            "confirmToken", LocalDateTime.now(), null, null, "newemail@test.com")
        .returning(APP_USER.ID).fetchOne().getId();

    this.cleanupJob.doCleanup();

    AppUserRecord user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId))
        .fetchOne();
    assertThat(user).isNotNull();

    getDsl().update(APP_USER)
        .set(APP_USER.CONFIRMATION_TOKEN_CREATED,
            LocalDateTime.now()
                .minus(getAppProperties().getSignupNotConfirmedUserMaxAge()))
        .where(APP_USER.ID.eq(userId)).execute();
    this.cleanupJob.doCleanup();

    user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId)).fetchOne();
    assertThat(user).isNotNull();
    assertThat(user.getConfirmationToken()).isNull();
    assertThat(user.getConfirmationTokenCreated()).isNull();
    assertThat(user.getEmailNew()).isNull();
  }

  @Test
  void testInactivateUser() {

    long userId = getDsl()
        .insertInto(APP_USER, APP_USER.EMAIL, APP_USER.PASSWORD_HASH, APP_USER.AUTHORITY,
            APP_USER.ENABLED, APP_USER.EXPIRED, APP_USER.LAST_ACCESS,
            APP_USER.CONFIRMATION_TOKEN, APP_USER.CONFIRMATION_TOKEN_CREATED,
            APP_USER.PASSWORD_RESET_TOKEN, APP_USER.PASSWORD_RESET_TOKEN_CREATED)
        .values("inactive@test.com", getPasswordEncoder().encode("password"), "USER",
            true, null, null, null, LocalDateTime.now(), null, null)
        .returning(APP_USER.ID).fetchOne().getId();

    this.cleanupJob.doCleanup();

    AppUserRecord user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId))
        .fetchOne();
    assertThat(user).isNotNull();
    assertThat(user.getEnabled()).isTrue();
    assertThat(user.getPasswordHash()).isNotNull();
    assertThat(user.getExpired()).isNull();

    getDsl().update(APP_USER)
        .set(APP_USER.LAST_ACCESS,
            LocalDateTime.now().minus(getAppProperties().getInactiveUserMaxAge()))
        .where(APP_USER.ID.eq(userId)).execute();
    this.cleanupJob.doCleanup();

    user = getDsl().selectFrom(APP_USER).where(APP_USER.ID.eq(userId)).fetchOne();
    assertThat(user).isNotNull();
    assertThat(user.getEnabled()).isTrue();
    assertThat(user.getPasswordHash()).isNull();
    assertThat(user.getExpired()).isNotNull();
  }

  @Test
  void testDeleteInactiveSessions() {
    getDsl().delete(APP_SESSION).execute();

    long userId = getDsl().select(APP_USER.ID).from(APP_USER).fetchAny().get(APP_USER.ID);

    getDsl()
        .insertInto(APP_SESSION, APP_SESSION.ID, APP_SESSION.APP_USER_ID,
            APP_SESSION.LAST_ACCESS, APP_SESSION.IP, APP_SESSION.USER_AGENT)
        .values("1", userId, LocalDateTime.now(), "127.0.0.1", "UA")
        .values("2", userId, LocalDateTime.now().minusMinutes(5), "127.0.0.1", "UA")
        .values("3", userId,
            LocalDateTime.now().minus(getAppProperties().getInactiveSessionMaxAge()),
            "127.0.0.1", "UA")
        .execute();

    this.cleanupJob.doCleanup();

    var result = getDsl().selectFrom(APP_SESSION).orderBy(APP_SESSION.ID).fetch();
    assertThat(result).hasSize(2);

    assertThat(result.get(0).getId()).isEqualTo("1");
    assertThat(result.get(1).getId()).isEqualTo("2");

    getDsl().delete(APP_SESSION).execute();
  }

}
