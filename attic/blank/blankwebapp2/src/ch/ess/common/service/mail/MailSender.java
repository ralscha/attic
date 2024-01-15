package ch.ess.common.service.mail;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 */
public interface MailSender {
  public void send(MailMessage message) throws MailException;

  public void reinit(String defaultSender, String host, int port, String user, String password);

}