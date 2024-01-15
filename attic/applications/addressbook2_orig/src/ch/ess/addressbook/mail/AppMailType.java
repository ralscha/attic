package ch.ess.addressbook.mail;

import ch.ess.common.mail.MailType;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class AppMailType extends MailType {
  private AppMailType(int type) {
    super(type);
  }

  private static int i = 0;
  public static final AppMailType TEST = new AppMailType(i++);
  public static final AppMailType PASSWORD = new AppMailType(i++);
}