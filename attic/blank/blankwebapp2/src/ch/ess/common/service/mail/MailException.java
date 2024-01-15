package ch.ess.common.service.mail;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 */
public class MailException extends NestableException {

  public MailException(String msg) {
    super(msg);
  }

  public MailException(String msg, Throwable t) {
    super(msg, t);
  }

}