package ch.ess.addressbook.mail;

import java.util.HashMap;
import java.util.Map;

import ch.ess.addressbook.resource.AppConfig;
import ch.ess.common.mail.MailConfiguration;
import ch.ess.common.mail.MailQueue;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class InitMail {

  public static void init(boolean reinit) {
    String smtpHost = AppConfig.getStringProperty(AppConfig.MAIL_SMTPHOST, "mail");
    String defaultSender = AppConfig.getStringProperty(AppConfig.MAIL_SENDER, "sender");
    String testReceiver = null;

    Map classMap = new HashMap();
    classMap.put(AppMailType.TEST, TestMail.class);
    classMap.put(AppMailType.PASSWORD, PasswordMail.class);

    MailConfiguration mconfig = new MailConfiguration();
    mconfig.setDefaultSender(defaultSender);
    mconfig.setGreetingsKey(null);
    mconfig.setClassMap(classMap);
    mconfig.setSmtpServer(smtpHost);
    mconfig.setSendMail(true);
    mconfig.setBundle("application");

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
