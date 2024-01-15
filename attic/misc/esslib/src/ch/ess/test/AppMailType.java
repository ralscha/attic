package ch.ess.test;

import ch.ess.mail.*;


public class AppMailType extends MailType {

  private AppMailType(int type) {
    super(type);
  }

  private static int i = 0;  
  public static final AppMailType T = new AppMailType(i++);

}
