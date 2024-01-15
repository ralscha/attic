
package ch.ess.mail;


public abstract class MailType {

  private final int type;

  protected MailType(int type) {
    this.type = type;
  }

  public String toString() {
    return String.valueOf(type);
  }

  
}
