package ch.rasc.travellog;

import static ch.rasc.travellog.db.tables.AppUser.APP_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import com.icegreen.greenmail.store.FolderException;

import ch.rasc.travellog.config.security.AuthHeaderFilter;
import ch.rasc.travellog.db.tables.records.AppUserRecord;

@SuppressWarnings("null")
class EmailChangeTest extends AbstractEmailTest {

  @Test
  void changeConfirmationWithWrongToken() {
    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-email-change", "unknown_token", boolean.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(false);
  }

  @Test
  void changeRequestOldAndNewEmailSame() {
    String token = getUtilService().sendLogin("user@test.com", "password", 200, "USER");
    sendChangeRequest(token, "user@test.com", "password", "SAME");
  }

  @Test
  void changeRequestEmailAlreadyRegistered() {
    String token = getUtilService().sendLogin("user@test.com", "password", 200, "USER");
    sendChangeRequest(token, "admin@test.com", "password", "USE");
  }

  @Test
  void changeRequestPasswordWrong() {
    String token = getUtilService().sendLogin("user@test.com", "password", 200, "USER");
    sendChangeRequest(token, "new_user@test.com", "wrongPassword", "PASSWORD");
  }

  @Test
  void changeConfirmationAfterTokenMaxAge()
      throws MessagingException, IOException, FolderException {
    String oldEmail = "user@test.com";
    String newEmail = "new_user@test.com";

    String token = getUtilService().sendLogin(oldEmail, "password", 200, "USER");
    sendChangeRequest(token, newEmail, "password", null);

    String changeToken = getChangeToken(newEmail);

    AppUserRecord appUser = getUtilService().getUser(oldEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getEmailNew()).isEqualTo(newEmail);
    assertThat(appUser.getConfirmationToken()).isEqualTo(changeToken);
    assertThat(appUser.getConfirmationTokenCreated()).isBefore(LocalDateTime.now());

    getDsl().update(APP_USER)
        .set(APP_USER.CONFIRMATION_TOKEN_CREATED,
            appUser.getConfirmationTokenCreated()
                .minus(getAppProperties().getSignupNotConfirmedUserMaxAge()))
        .where(APP_USER.ID.eq(appUser.getId())).execute();

    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-email-change", changeToken, boolean.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(false);

    appUser = getUtilService().getUser(oldEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getEmailNew()).isNull();
    assertThat(appUser.getConfirmationToken()).isNull();
    assertThat(appUser.getConfirmationTokenCreated()).isNull();

    getUtilService().sendLogin(newEmail, "password", 401, null);
    getUtilService().sendLogin(oldEmail, "password", 200, "USER");
  }

  @Test
  void changeOkay() throws MessagingException, IOException, FolderException {
    String oldEmail = "user@test.com";
    String newEmail = "new_user@test.com";

    String token = getUtilService().sendLogin(oldEmail, "password", 200, "USER");
    sendChangeRequest(token, newEmail, "password", null);

    String changeToken = getChangeToken(newEmail);

    AppUserRecord appUser = getUtilService().getUser(oldEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getEmailNew()).isEqualTo(newEmail);
    assertThat(appUser.getConfirmationToken()).isEqualTo(changeToken);
    assertThat(appUser.getConfirmationTokenCreated()).isBefore(LocalDateTime.now());

    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-email-change", changeToken, boolean.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(true);

    appUser = getUtilService().getUser(newEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getEmailNew()).isNull();
    assertThat(appUser.getConfirmationToken()).isNull();
    assertThat(appUser.getConfirmationTokenCreated()).isNull();

    getUtilService().sendLogin(oldEmail, "password", 401, null);
    getUtilService().sendLogin(newEmail, "password", 200, "USER");

    getDsl().update(APP_USER).set(APP_USER.EMAIL, oldEmail)
        .where(APP_USER.EMAIL.eq(newEmail)).execute();
  }

  private String getChangeToken(String recipientEmailAddress)
      throws MessagingException, IOException, FolderException {
    getSmtpServer().waitForIncomingEmail(1);
    MimeMessage confirmationMessage = getSmtpServer().getReceivedMessages()[0];

    assertThat(confirmationMessage.getSubject()).endsWith("Email Change Confirmation");
    assertThat(confirmationMessage.getRecipients(RecipientType.TO)[0].toString())
        .isEqualTo(recipientEmailAddress);
    assertThat(confirmationMessage.getFrom()[0].toString())
        .isEqualTo(getAppProperties().getDefaultEmailSender());

    String emailContent = (String) confirmationMessage.getContent();
    Pattern linkPattern = Pattern.compile("http://.*/#/email-change-confirm/([^\"]+)");
    Matcher matcher = linkPattern.matcher(emailContent);
    assertThat(matcher.find()).isTrue();
    String token = matcher.group(1);
    getSmtpServer().purgeEmailFromAllMailboxes();

    return token;
  }

  private void sendChangeRequest(String authToken, String newEmail, String password,
      String expectedResponse) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);

    var body = new LinkedMultiValueMap<String, String>();
    body.add("newEmail", newEmail);
    body.add("password", password);

    ResponseEntity<String> response = getRestTemplate().postForEntity("/be/change-email",
        new HttpEntity<>(body, headers), String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    if (expectedResponse != null) {
      assertThat(response.getBody()).isEqualTo("\"" + expectedResponse + "\"");
    }
    else {
      assertThat(response.getBody()).isNull();
    }
  }

}
