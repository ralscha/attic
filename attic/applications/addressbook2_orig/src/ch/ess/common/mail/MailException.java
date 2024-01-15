package ch.ess.common.mail;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class MailException extends Exception {

  private Throwable rootCause;

  public MailException() {
    super();
  }

  public MailException(String message) {
    super(message);
  }

  public MailException(String message, Throwable rootCause) {

    super(message);

    this.rootCause = rootCause;
  }

  public MailException(Throwable rootCause) {

    super(rootCause.getLocalizedMessage());

    this.rootCause = rootCause;
  }

  public Throwable getRootCause() {
    return rootCause;
  }
}
