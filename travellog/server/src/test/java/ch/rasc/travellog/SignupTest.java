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
import org.springframework.util.MultiValueMap;

import com.icegreen.greenmail.store.FolderException;

import ch.rasc.travellog.config.security.AuthHeaderFilter;
import ch.rasc.travellog.db.tables.records.AppUserRecord;

@SuppressWarnings("null")
class SignupTest extends AbstractEmailTest {

  @Test
  void signUpExistingUser() {
    sendSignUp("admin@test.com", "password", "EMAIL_REGISTERED");
  }

  @Test
  void signUpConfirmationWrongToken() {
    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-signup", "some_random_token", boolean.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(false);
  }

  @Test
  void signUpConfirmationAfterTokenMaxAge()
      throws MessagingException, IOException, FolderException {
    String newUserEmail = "newuser@test.com";
    sendSignUp(newUserEmail, "mypassword1234", null);
    getUtilService().sendLogin(newUserEmail, "mypassword1234", 401, null);

    String token = getConfirmationToken(newUserEmail);

    AppUserRecord appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isFalse();
    assertThat(appUser.getConfirmationToken()).isEqualTo(token);
    assertThat(appUser.getConfirmationTokenCreated()).isBefore(LocalDateTime.now());

    getDsl().update(APP_USER)
        .set(APP_USER.CONFIRMATION_TOKEN_CREATED,
            appUser.getConfirmationTokenCreated()
                .minus(getAppProperties().getSignupNotConfirmedUserMaxAge()))
        .where(APP_USER.ID.eq(appUser.getId())).execute();

    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-signup", token, boolean.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(false);

    appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser).isNull();
    getUtilService().sendLogin(newUserEmail, "mypassword1234", 401, null);
  }

  @Test
  void signUpWeakPassword() {
    String newUserEmail = "anotheranotheruser@test.com";
    sendSignUp(newUserEmail, "1234567890", "WEAK_PASSWORD");
    getUtilService().sendLogin(newUserEmail, "1234567890", 401, null);
  }

  @Test
  void signUpOkay() throws FolderException, MessagingException, IOException {
    String newUserEmail = "anotheruser@test.com";
    sendSignUp(newUserEmail, "secret_mypassword1234", null);
    getUtilService().sendLogin(newUserEmail, "secret_mypassword1234", 401, null);

    String token = getConfirmationToken(newUserEmail);

    AppUserRecord appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isFalse();
    assertThat(appUser.getConfirmationToken()).isEqualTo(token);
    assertThat(appUser.getConfirmationTokenCreated()).isBefore(LocalDateTime.now());

    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-signup", token, boolean.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(true);

    appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getConfirmationToken()).isNull();
    assertThat(appUser.getConfirmationTokenCreated()).isNull();

    getUtilService().sendLogin(newUserEmail, "secret_mypassword1234", 200, "USER");
  }

  @Test
  void signUpAndDeleteAccount() throws FolderException, MessagingException, IOException {
    String newUserEmail = "anotheruser2@test.com";
    sendSignUp(newUserEmail, "mypassword1234", null);
    getUtilService().sendLogin(newUserEmail, "mypassword1234", 401, null);

    String token = getConfirmationToken(newUserEmail);

    AppUserRecord appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isFalse();
    assertThat(appUser.getConfirmationToken()).isEqualTo(token);
    assertThat(appUser.getConfirmationTokenCreated()).isBefore(LocalDateTime.now());

    ResponseEntity<Boolean> response = getRestTemplate()
        .postForEntity("/be/confirm-signup", token, boolean.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(true);

    appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getConfirmationToken()).isNull();
    assertThat(appUser.getConfirmationTokenCreated()).isNull();

    String authToken = getUtilService().sendLogin(newUserEmail, "mypassword1234", 200,
        "USER");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);

    // wrong password
    ResponseEntity<Boolean> deleteAccountResponse = getRestTemplate().postForEntity(
        "/be/delete-account", new HttpEntity<>("wrongPassword", headers), boolean.class);

    assertThat(deleteAccountResponse.getStatusCode().value()).isEqualTo(200);
    assertThat(deleteAccountResponse.getBody()).isEqualTo(false);

    appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser.getEnabled()).isTrue();
    assertThat(appUser.getConfirmationToken()).isNull();
    assertThat(appUser.getConfirmationTokenCreated()).isNull();

    // correct password
    deleteAccountResponse = getRestTemplate().postForEntity("/be/delete-account",
        new HttpEntity<>("mypassword1234", headers), boolean.class);

    assertThat(deleteAccountResponse.getStatusCode().value()).isEqualTo(200);
    assertThat(deleteAccountResponse.getBody()).isEqualTo(true);

    appUser = getUtilService().getUser(newUserEmail);
    assertThat(appUser).isNull();
  }

  private String getConfirmationToken(String recipientEmailAddress)
      throws MessagingException, IOException, FolderException {
    getSmtpServer().waitForIncomingEmail(1);
    MimeMessage confirmationMessage = getSmtpServer().getReceivedMessages()[0];

    assertThat(confirmationMessage.getSubject()).endsWith("Sign Up Confirmation");
    assertThat(confirmationMessage.getRecipients(RecipientType.TO)[0].toString())
        .isEqualTo(recipientEmailAddress);
    assertThat(confirmationMessage.getFrom()[0].toString())
        .isEqualTo(getAppProperties().getDefaultEmailSender());

    String emailContent = (String) confirmationMessage.getContent();
    Pattern linkPattern = Pattern.compile("http://.*/#/signup-confirm/([^\"]+)");
    Matcher matcher = linkPattern.matcher(emailContent);
    assertThat(matcher.find()).isTrue();
    String token = matcher.group(1);
    getSmtpServer().purgeEmailFromAllMailboxes();

    return token;
  }

  private void sendSignUp(String email, String password, String expectedResponse) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var body = new LinkedMultiValueMap<String, String>();
    body.add("email", email);
    body.add("password", password);

    var request = new HttpEntity<MultiValueMap<String, String>>(body, headers);

    var response = getRestTemplate().postForEntity("/be/signup", request, String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    if (expectedResponse == null) {
      assertThat(response.getBody()).isNull();
    }
    else {
      assertThat(response.getBody()).isEqualTo("\"" + expectedResponse + "\"");
    }
  }

}
