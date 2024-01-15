package ch.ess.common.service.mail;

import java.util.Locale;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:46 $ 
  */
public interface TemplateMailSender {
  public void send(Locale locale, MailMessage message, Map context) throws MailException;   
}
