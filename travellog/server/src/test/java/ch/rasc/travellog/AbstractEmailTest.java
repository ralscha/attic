package ch.rasc.travellog;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public abstract class AbstractEmailTest extends AbstractBaseTest {

  @Autowired
  private JavaMailSender emailSender;

  private static GreenMail smtpServer;

  @BeforeAll
  public static void setupSMTP() {
    smtpServer = new GreenMail(new ServerSetup(2525, "127.0.0.1", "smtp"));
    smtpServer.start();
  }

  @AfterAll
  public static void tearDownSMTP() {
    smtpServer.stop();
  }

  static GreenMail getSmtpServer() {
    return smtpServer;
  }

  public JavaMailSender getEmailSender() {
    return this.emailSender;
  }

}
