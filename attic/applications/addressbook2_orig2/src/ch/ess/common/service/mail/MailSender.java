package ch.ess.common.service.mail;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:52 $ 
  */
public interface MailSender {
  public void send(MailMessage message) throws MailException;

  public void reinit(String defaultSender, String host, int port, String user, String password);
   
}
