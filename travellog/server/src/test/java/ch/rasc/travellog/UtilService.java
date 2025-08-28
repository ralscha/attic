package ch.rasc.travellog;

import static ch.rasc.travellog.db.tables.AppUser.APP_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ch.rasc.travellog.config.security.AuthHeaderFilter;
import ch.rasc.travellog.db.tables.records.AppUserRecord;

@SuppressWarnings("null")
@Service
public class UtilService {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private DSLContext dsl;

  public void sendLogout(String token, int expectStatusCode) {
    HttpHeaders headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, token);
    var request = new HttpEntity<>(headers);
    var response = this.restTemplate.postForEntity("/be/logout", request, Void.class);
    assertThat(response.getStatusCode().value()).isEqualTo(expectStatusCode);
  }

  public String sendLogin(String username, String password, int expectStatusCode,
      String expectBody) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var body = new LinkedMultiValueMap<String, String>();
    body.add("username", username);
    body.add("password", password);

    var request = new HttpEntity<MultiValueMap<String, String>>(body, headers);
    var response = this.restTemplate.postForEntity("/be/login", request, String.class);

    assertThat(response.getStatusCode().value()).isEqualTo(expectStatusCode);
    assertThat(response.getBody()).isEqualTo(expectBody);

    List<String> headerValues = response.getHeaders()
        .getValuesAsList(AuthHeaderFilter.HEADER_NAME);
    if (headerValues != null && headerValues.size() == 1) {
      return headerValues.get(0);
    }

    return null;
  }

  public long getUserId(String email) {
    return this.dsl.select(APP_USER.ID).from(APP_USER).where(APP_USER.EMAIL.eq(email))
        .fetchOne().get(APP_USER.ID);
  }

  public AppUserRecord getUser(String email) {
    return this.dsl.selectFrom(APP_USER).where(APP_USER.EMAIL.eq(email)).fetchOne();
  }

}
