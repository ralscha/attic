package ch.ess.cal.mail;

import ch.ess.cal.resource.*;
import ch.ess.mail.*;

public class TestMail extends Mail {

  protected String getBody() {
    return "This ist a test";
  }

  protected String getSubject() {
    return "Test Mail from Web Calendar";
  }

  protected Recipient getRecipient() {
    return new Recipient() {
      public String[] getRecipient() {
        return new String[] { 
          AppConfig.getStringProperty(AppConfig.ERROR_MAIL_RECEIVER) 
        };
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
