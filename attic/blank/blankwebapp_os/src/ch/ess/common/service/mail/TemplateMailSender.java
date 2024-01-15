package ch.ess.common.service.mail;

import java.util.Locale;
import java.util.Map;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 12:24:50 $
 */
public interface TemplateMailSender {
  public void send(Locale locale, MailMessage message, Map context) throws MailException;
}