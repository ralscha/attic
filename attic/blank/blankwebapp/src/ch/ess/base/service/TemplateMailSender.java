package ch.ess.base.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:25 $
 */
public interface TemplateMailSender {
  void send(Locale locale, SimpleMailMessage message, Map context) throws MailException;
}