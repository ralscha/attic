package ch.ess.cal.mail;

import java.util.*;

import ch.ess.cal.resource.*;
import ch.ess.mail.*;

public class InitMail {

  
  public static void init(boolean reinit) {
    String smtpHost = AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, "mail");
    String defaultSender = AppConfig.getStringProperty(AppConfig.MAIL_SENDER, "sender");
    String testReceiver = null;

    Map classMap = new HashMap();
    classMap.put(AppMailType.TEST, TestMail.class);

    MailConfiguration mconfig = new MailConfiguration();
    mconfig.setDefaultSender(defaultSender);
    mconfig.setGreetingsKey(null);
    mconfig.setClassMap(classMap);
    mconfig.setSmtpServer(smtpHost);
    mconfig.setSendMail(true);
    mconfig.setBundle("resources");

    if (testReceiver != null) {
      mconfig.setTest(testReceiver);
    }

    if (reinit) {
      MailQueue.reinit(mconfig);
    } else {
      MailQueue.init(mconfig);
    }
  }
}
