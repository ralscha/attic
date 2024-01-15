package ch.ess.test;

import ch.ess.mail.*;

public class TMail extends Mail {

  protected String getBody() {
    return "body";
  }

  protected String getSubject() {
    return "subjekt";
  }

  protected Recipient getRecipient() {
    return new Recipient() {
      public String getSender() {
        return "sender@ess.ch";
      }
      
      public String[] getRecipient() {
        return new String[]{"sr@ess.ch"};
      }
    }; 
  }

  protected void init() {}

  protected void release() {}

}
