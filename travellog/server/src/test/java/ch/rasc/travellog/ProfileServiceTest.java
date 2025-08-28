package ch.rasc.travellog;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static ch.rasc.travellog.db.tables.AppUser.APP_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.store.FolderException;

import ch.rasc.travellog.config.security.AuthHeaderFilter;
import ch.rasc.travellog.db.tables.records.AppSessionRecord;

@SuppressWarnings("null")
class ProfileServiceTest extends AbstractEmailTest {

  @Test
  void testSessions() throws IOException {
    getDsl().delete(APP_SESSION).execute();

    String authToken = getUtilService().sendLogin("admin@test.com", "password", 200,
        "ADMIN");
    HttpHeaders headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);
    var request = new HttpEntity<>(headers);

    ResponseEntity<String> response = getRestTemplate().exchange("/be/sessions",
        HttpMethod.GET, request, String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    ObjectMapper om = new ObjectMapper();
    List<TestSession> sessions = om.readValue(response.getBody(),
        new TypeReference<List<TestSession>>() {
          // nothing_here
        });
    assertThat(sessions).hasSize(1);

    AppSessionRecord record = getDsl().selectFrom(APP_SESSION).fetchOne();

    TestSession session = sessions.get(0);
    assertThat(session.id).isEqualTo(record.getId());
    assertThat(session.loggedIn).isTrue();
    assertThat(session.lastAccess)
        .isEqualTo(record.getLastAccess().toEpochSecond(ZoneOffset.UTC));
    assertThat(session.ip).isEqualTo(record.getIp());
    assertThat(session.userAgent).isEqualTo(record.getUserAgent());

    // second session

    authToken = getUtilService().sendLogin("admin@test.com", "password", 200, "ADMIN");
    headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);
    request = new HttpEntity<>(headers);

    response = getRestTemplate().exchange("/be/sessions", HttpMethod.GET, request,
        String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);

    sessions = om.readValue(response.getBody(), new TypeReference<List<TestSession>>() {
      // nothing_here
    });
    assertThat(sessions).hasSize(2);
    int noOfLoggedInSessions = 0;
    for (TestSession sess : sessions) {
      if (sess.loggedIn) {
        noOfLoggedInSessions++;
      }
    }
    assertThat(noOfLoggedInSessions).isEqualTo(1);

  }

  @Test
  void testDeleteSession() throws IOException {

    getDsl().delete(APP_SESSION).execute();

    String authToken = getUtilService().sendLogin("admin@test.com", "password", 200,
        "ADMIN");

    assertThat(getDsl().selectCount().from(APP_SESSION).fetchOne().get(0)).isEqualTo(1);

    HttpHeaders headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);
    var request = new HttpEntity<>(headers);

    ResponseEntity<String> response = getRestTemplate().exchange("/be/sessions",
        HttpMethod.GET, request, String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
    ObjectMapper om = new ObjectMapper();
    List<TestSession> sessions = om.readValue(response.getBody(),
        new TypeReference<List<TestSession>>() {
          // nothing_here
        });
    assertThat(sessions).hasSize(1);
    TestSession session = sessions.get(0);

    headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);

    ResponseEntity<Void> deleteSessionResponse = getRestTemplate().postForEntity(
        "/be/delete-session", new HttpEntity<>(session.id, headers), Void.class);
    assertThat(deleteSessionResponse.getStatusCode().value()).isEqualTo(204);

    assertThat(getDsl().selectCount().from(APP_SESSION).fetchOne().get(0)).isEqualTo(0);
  }

  @Test
  void testChangePassword() throws MessagingException, IOException, FolderException {
    getDsl().delete(APP_SESSION).execute();

    String oldPassword = "password";
    String newPassword = "newPassword";

    String authToken = getUtilService().sendLogin("admin@test.com", oldPassword, 200,
        "ADMIN");

    HttpHeaders headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var body = new LinkedMultiValueMap<String, String>();
    body.add("oldPassword", oldPassword + "WRONG");
    body.add("newPassword", newPassword);

    var request = new HttpEntity<MultiValueMap<String, String>>(body, headers);
    var response = getRestTemplate().postForEntity("/be/change-password", request,
        String.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("\"INVALID\"");

    String token = getUtilService().sendLogin("admin@test.com", oldPassword, 200,
        "ADMIN");
    getUtilService().sendLogout(token, 200);
    getUtilService().sendLogin("admin@test.com", newPassword, 401, null);

    // correct old password, weak new password
    body = new LinkedMultiValueMap<>();
    body.add("oldPassword", oldPassword);
    body.add("newPassword", "1234567890");

    request = new HttpEntity<>(body, headers);
    response = getRestTemplate().postForEntity("/be/change-password", request,
        String.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("\"WEAK_PASSWORD\"");

    token = getUtilService().sendLogin("admin@test.com", oldPassword, 200, "ADMIN");
    getUtilService().sendLogout(token, 200);
    getUtilService().sendLogin("admin@test.com", "1234567890", 401, null);

    // correct old password
    body = new LinkedMultiValueMap<>();
    body.add("oldPassword", oldPassword);
    body.add("newPassword", newPassword);

    request = new HttpEntity<>(body, headers);
    response = getRestTemplate().postForEntity("/be/change-password", request,
        String.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isNull();

    token = getUtilService().sendLogin("admin@test.com", newPassword, 200, "ADMIN");
    getUtilService().sendLogout(token, 200);
    getUtilService().sendLogin("admin@test.com", oldPassword, 401, null);

    waitForPasswordChangedEmail();

    assertThat(getDsl().selectCount().from(APP_SESSION).fetchOne().get(0)).isEqualTo(0);

    // and back
    authToken = getUtilService().sendLogin("admin@test.com", newPassword, 200, "ADMIN");

    headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, authToken);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    body = new LinkedMultiValueMap<>();
    body.add("oldPassword", newPassword);
    body.add("newPassword", "a very secret password");

    request = new HttpEntity<>(body, headers);
    response = getRestTemplate().postForEntity("/be/change-password", request,
        String.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isNull();

    authToken = getUtilService().sendLogin("admin@test.com", "a very secret password",
        200, "ADMIN");
    getUtilService().sendLogout(token, 200);
    getUtilService().sendLogin("admin@test.com", newPassword, 401, null);

    waitForPasswordChangedEmail();

    getDsl().update(APP_USER)
        .set(APP_USER.PASSWORD_HASH, this.getPasswordEncoder().encode("password"))
        .where(APP_USER.EMAIL.eq("admin@test.com")).execute();

  }

  private void waitForPasswordChangedEmail()
      throws MessagingException, IOException, FolderException {
    getSmtpServer().waitForIncomingEmail(1);
    MimeMessage confirmationMessage = getSmtpServer().getReceivedMessages()[0];

    assertThat(confirmationMessage.getSubject()).endsWith("Password Changed");
    assertThat(confirmationMessage.getRecipients(RecipientType.TO)[0].toString())
        .isEqualTo("admin@test.com");
    assertThat(confirmationMessage.getFrom()[0].toString())
        .isEqualTo(getAppProperties().getDefaultEmailSender());

    String emailContent = (String) confirmationMessage.getContent();
    Pattern linkPattern = Pattern.compile("http://.*/#/password-reset-request");
    Matcher matcher = linkPattern.matcher(emailContent);
    assertThat(matcher.find()).isTrue();
    getSmtpServer().purgeEmailFromAllMailboxes();
  }

}
