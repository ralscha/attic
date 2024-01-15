package ch.ess.common.service.mail;

import java.util.Locale;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:52 $ 
  */
public interface TemplateMailSender {
  public void send(Locale locale, MailMessage message, Map context) throws MailException;   
}
