package ch.ess.common.service.mail;

import java.util.Locale;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:29 $ 
  */
public interface TemplateMailSender {
  public void send(Locale locale, MailMessage message, Map context) throws MailException;   
}
