
package ch.ess.mail;


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
