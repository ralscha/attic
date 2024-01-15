package ch.ess.timemail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author sr
 */
public class Sender {

  private JavaMailSender mailSender;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("ConTracker 4.2.1");
    message.setFrom("ct@ess.ch");
    message.setBcc("sr@ess.ch");

    message.setTo(new String[]{"martin_michel@gmx.net", "adrian.jungo@swisscom.com", "marc.dolder@swisscom.com",
        "Beat.Schweizer@swisscom.com", "Michael.Scheurer@swisscom.com"});

    String text = "\nDie neue ConTracker Version steht auf unserem FTP Server bereit";
    text += "\n\n";
    text += "Ralph Schär\n";
    text += "ESS Development AG\n\n";
    text += "I.s.a.A."; 

    message.setText(text);

    mailSender.send(message);
  }
}