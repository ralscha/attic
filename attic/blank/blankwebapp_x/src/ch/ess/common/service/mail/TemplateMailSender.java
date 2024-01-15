package ch.ess.common.service.mail;

import java.util.Locale;
import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:04 $
 */
public interface TemplateMailSender {
  public void send(Locale locale, SimpleMailMessage message, Map context) throws MailException;
}