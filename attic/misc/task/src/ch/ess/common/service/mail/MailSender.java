package ch.ess.common.service.mail;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:31:08 $ 
  */
public interface MailSender {
  public void send(MailMessage message) throws MailException;

  public void reinit(String defaultSender, String host, int port, String user, String password);
   
}
