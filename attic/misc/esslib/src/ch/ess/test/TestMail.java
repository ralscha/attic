package ch.ess.test;

import java.util.*;

import ch.ess.mail.*;

public class TestMail {

  public static void main(String[] args) {
    Map classMap = new HashMap();
    classMap.put(AppMailType.T, TMail.class);

    MailConfiguration mconfig = new MailConfiguration();
    mconfig.setDefaultSender("team@ess.ch");
    mconfig.setGreetingsKey(null);
    mconfig.setClassMap(classMap);
    mconfig.setSmtpServer("mail.ess.ch");
    mconfig.setSendMail(true);
    mconfig.setTest("sr@ess.ch");

    MailQueue.init(mconfig);
    
    long start = System.currentTimeMillis();
    for (int i = 0; i < 200; i++) {
    try {
      Mail.send(AppMailType.T, new HashMap());
    } catch (MailException e) {
      System.err.println(e);
    }
    }
    System.out.println((System.currentTimeMillis() - start) + " ms");
    
    try {
      Thread.sleep(50000);
    } catch (InterruptedException e) {}

  }
}
