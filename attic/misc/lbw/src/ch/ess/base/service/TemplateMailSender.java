package ch.ess.base.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import ch.ess.base.dao.TextResourceDao;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateMailSender {

  private TextResourceDao textResourceDao;
  private JavaMailSender mailSender;
  private String subject;
  private String body;

  public void send(final Locale locale, final SimpleMailMessage message, final Map ctx) throws MailException {

    final String bodyStr = textResourceDao.getText(body, locale.toString());
    final String subjectStr = textResourceDao.getText(subject, locale.toString());

    Configuration configuration = new Configuration();

    try {
      Template textTemplate = new Template("text", new StringReader(bodyStr), configuration);
      Template subjectTemplate = new Template("subject", new StringReader(subjectStr), configuration);

      message.setText(FreeMarkerTemplateUtils.processTemplateIntoString(textTemplate, ctx));
      message.setSubject(FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, ctx));
    } catch (IOException e) {
      throw new MailSendException("evaluate", e);
    } catch (TemplateException e) {
      throw new MailSendException("evaluate", e);
    }

    mailSender.send(message);
  }

  public void setBody(final String bodyTemplate) {
    this.body = bodyTemplate;
  }

  public void setSubject(final String subjectTemplate) {
    this.subject = subjectTemplate;
  }

  public void setMailSender(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

}