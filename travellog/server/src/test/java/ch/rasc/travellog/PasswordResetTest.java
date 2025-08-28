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

import ch.rasc.travellog.db.tables.records.AppUserRecord;

@SuppressWarnings("null")
class PasswordResetTest extends AbstractEmailTest {

  @Test
  void resetUnknownUser() {
    sendPasswordResetRequest("unknownuser@test.com", true);
  }

  @Test
  void resetConfirmationWrongToken() {
    sendPasswordRequest("wrong_token", "new_password", "INVALID");
  }

  @Test
  void resetConfirmationAfterTokenMaxAge()
      throws MessagingException, IOException, FolderException {
    String email = "user@test.com";
    sendPasswordResetRequest(email, true);
    String token = getResetToken(email);

    getUtilService().sendLogin(email, "password", 200, "USER");

    AppUserRecord appUser = getUtilService().getUser(email);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getPasswordResetToken()).isEqualTo(token);
    assertThat(appUser.getPasswordResetTokenCreated()).isBefore(LocalDateTime.now());

    getDsl().update(APP_USER)
        .set(APP_USER.PASSWORD_RESET_TOKEN_CREATED,
            appUser.getPasswordResetTokenCreated()
                .minus(getAppProperties().getPasswordResetTokenMaxAge()))
        .where(APP_USER.ID.eq(appUser.getId())).execute();

    sendPasswordRequest(token, "new_password", "INVALID");

    appUser = getUtilService().getUser(email);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getPasswordResetToken()).isNull();
    assertThat(appUser.getPasswordResetTokenCreated()).isNull();

    getUtilService().sendLogin(email, "new_password", 401, null);
    getUtilService().sendLogin(email, "password", 200, "USER");
  }

  @Test
  void resetOkay() throws MessagingException, IOException, FolderException {
    String email = "user@test.com";
    sendPasswordResetRequest(email, true);
    String token = getResetToken(email);

    getUtilService().sendLogin(email, "password", 200, "USER");

    AppUserRecord appUser = getUtilService().getUser(email);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getPasswordResetToken()).isEqualTo(token);
    assertThat(appUser.getPasswordResetTokenCreated()).isBefore(LocalDateTime.now());

    sendPasswordRequest(token, "1234567890", "WEAK_PASSWORD");
    sendPasswordRequest(token, "new_password", null);

    appUser = getUtilService().getUser(email);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getPasswordResetToken()).isNull();
    assertThat(appUser.getPasswordResetTokenCreated()).isNull();

    getUtilService().sendLogin(email, "password", 401, null);
    getUtilService().sendLogin(email, "new_password", 200, "USER");

    getDsl().update(APP_USER)
        .set(APP_USER.PASSWORD_HASH, this.getPasswordEncoder().encode("password"))
        .where(APP_USER.EMAIL.eq(email)).execute();
  }

  private String getResetToken(String recipientEmailAddress)
      throws MessagingException, IOException, FolderException {
    getSmtpServer().waitForIncomingEmail(1);
    MimeMessage confirmationMessage = getSmtpServer().getReceivedMessages()[0];

    assertThat(confirmationMessage.getSubject()).endsWith("Password Reset");
    assertThat(confirmationMessage.getRecipients(RecipientType.TO)[0].toString())
        .isEqualTo(recipientEmailAddress);
    assertThat(confirmationMessage.getFrom()[0].toString())
        .isEqualTo(getAppProperties().getDefaultEmailSender());

    String emailContent = (String) confirmationMessage.getContent();
    Pattern linkPattern = Pattern.compile("http://.*/#/password-reset/([^\"]+)");
    Matcher matcher = linkPattern.matcher(emailContent);
    assertThat(matcher.find()).isTrue();
    String token = matcher.group(1);
    getSmtpServer().purgeEmailFromAllMailboxes();

    return token;
  }

  private void sendPasswordResetRequest(String email, boolean expectedResponse) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);

    var response = getRestTemplate().postForEntity("/be/reset-password-request",
        new HttpEntity<>(email, headers), boolean.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(expectedResponse);
  }

  private void sendPasswordRequest(String token, String newPassword,
      String expectedResponse) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var body = new LinkedMultiValueMap<String, String>();
    body.add("resetToken", token);
    body.add("password", newPassword);
    ResponseEntity<String> response = getRestTemplate().postForEntity(
        "/be/reset-password", new HttpEntity<>(body, headers), String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    if (expectedResponse == null) {
      assertThat(response.getBody()).isNull();
    }
    else {
      assertThat(response.getBody()).isEqualTo("\"" + expectedResponse + "\"");
    }
  }

}
