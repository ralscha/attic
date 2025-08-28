package ch.rasc.travellog;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.rasc.travellog.config.AppProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AbstractBaseTest {

  @Autowired
  private DSLContext dsl;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private UtilService utilService;

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private PasswordEncoder passwordEncoder;

  DSLContext getDsl() {
    return this.dsl;
  }

  TestRestTemplate getRestTemplate() {
    return this.restTemplate;
  }

  UtilService getUtilService() {
    return this.utilService;
  }

  AppProperties getAppProperties() {
    return this.appProperties;
  }

  PasswordEncoder getPasswordEncoder() {
    return this.passwordEncoder;
  }

}
