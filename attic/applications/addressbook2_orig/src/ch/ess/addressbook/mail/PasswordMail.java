package ch.ess.addressbook.mail;

import ch.ess.common.mail.Mail;
import ch.ess.common.mail.Recipient;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class PasswordMail extends Mail {

  protected String getBody() {
    String newPassword = (String)getContext().get("newPassword");
    return getString("passwordmail.body", new Object[] { newPassword });
  }

  protected String getSubject() {
    return getString("passwordmail.subject");
  }

  protected Recipient getRecipient() {
    return new Recipient() {
      public String[] getRecipient() {
        return new String[] {(String)getContext().get("recipient")};
      }
      public String getSender() {
        return getDefaultAddress();
      }

    };
  }

  protected void init() {
  }

  protected void release() {
  }

}
