package ch.ess.common.mail;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public abstract class Recipient {

  public abstract String[] getRecipient();

  public String[] getCC() {
    return null;
  }

  public abstract String getSender();

  protected String getDefaultAddress() {
    return MailQueue.getConfig().getDefaultSender();
  }
}
