package ch.ess.common.service.mail;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $ 
  */
public interface MailSender {
  public void send(MailMessage message) throws MailException;

  public void reinit(String defaultSender, String host, int port, String user, String password);
   
}
