package ch.rasc.travellog;

import static ch.rasc.travellog.db.tables.AppSession.APP_SESSION;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import ch.rasc.travellog.config.security.AuthHeaderFilter;

@SuppressWarnings("null")
class SecurityTests extends AbstractEmailTest {

  @ParameterizedTest
  @ValueSource(strings = { "/syncview", "/sync", "/change-password", "/delete-account",
      "/change-email", "/sessions", "/delete-session", "/invalidate-sessions", "/users",
      "/activate", "/delete", "/enable", "/disable" })
  void testReturns401WhenNotLoggedIn(String url) {
    var response = getRestTemplate().getForEntity(url, Object.class);
    assertThat(response.getStatusCode().value()).isEqualTo(401);
  }

  @Test
  void testAuthenticate() {
    ResponseEntity<String> response = getRestTemplate().getForEntity("/be/authenticate",
        String.class);
    assertThat(response.getBody()).isNull();
    assertThat(response.getStatusCode().value()).isEqualTo(401);

    String token = getUtilService().sendLogin("admin@test.com", "password", 200, "ADMIN");

    HttpHeaders headers = new HttpHeaders();
    headers.set(AuthHeaderFilter.HEADER_NAME, token);
    var request = new HttpEntity<>(headers);
    response = getRestTemplate().exchange("/be/authenticate", HttpMethod.GET, request,
        String.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("ADMIN");

    getUtilService().sendLogout(token, 200);

    response = getRestTemplate().getForEntity("/be/authenticate", String.class);
    assertThat(response.getBody()).isNull();
    assertThat(response.getStatusCode().value()).isEqualTo(401);
  }

  @Test
  void testLogin() {
    getDsl().delete(APP_SESSION).execute();

    String token = getUtilService().sendLogin("admin@test.com", "password", 200, "ADMIN");

    long userId = getUtilService().getUserId("admin@test.com");
    assertThat(getDsl().selectCount().from(APP_SESSION).fetchOne().get(0)).isEqualTo(1);
    assertThat(getDsl().selectCount().from(APP_SESSION)
        .where(APP_SESSION.APP_USER_ID.eq(userId).and(APP_SESSION.ID.eq(token)))
        .fetchOne().get(0)).isEqualTo(1);

    getUtilService().sendLogout(token, 200);
    assertThat(getDsl().selectCount().from(APP_SESSION).fetchOne().get(0)).isEqualTo(0);
  }

}
