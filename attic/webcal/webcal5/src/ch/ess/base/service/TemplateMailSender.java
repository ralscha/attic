package ch.ess.base.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

  public void send(final Locale locale, final SimpleMailMessage message, final Map<String,String> ctx) throws MailException {

    final String bodyStr = textResourceDao.getText(body, locale.toString());
    final String subjectStr = textResourceDao.getText(subject, locale.toString());

    Configuration configuration = new Configuration();

    try {
      Template textTemplate = new Template("text", new StringReader(bodyStr), configuration);
      Template subjectTemplate = new Template("subject", new StringReader(subjectStr), configuration);

      message.setText(FreeMarkerTemplateUtils.processTemplateIntoString(textTemplate, ctx));
      message.setSubject(FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, ctx));
    } catch (IOException e) {
      throw new MailSendException("send", e);
    } catch (TemplateException e) {
      throw new MailSendException("send", e);
    }

    mailSender.send(message);
  }

  public void send(final Locale locale, final MimeMessageHelper message, final Map<String,String> ctx) throws MailException {

    final String bodyStr = textResourceDao.getText(body, locale.toString());
    final String subjectStr = textResourceDao.getText(subject, locale.toString());

    Configuration configuration = new Configuration();

    try {
      Template textTemplate = new Template("text", new StringReader(bodyStr), configuration);
      Template subjectTemplate = new Template("subject", new StringReader(subjectStr), configuration);

      String bodyText = FreeMarkerTemplateUtils.processTemplateIntoString(textTemplate, ctx);
      message.setText(bodyText, bodyText.contains("<html>"));
      message.setSubject(FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, ctx));
    } catch (IOException e) {
      throw new MailSendException("send", e);
    } catch (TemplateException e) {
      throw new MailSendException("send", e);
    } catch (MessagingException e) {
      throw new MailSendException("send", e);
    }

    mailSender.send(message.getMimeMessage());
  }
  
  public MimeMessageHelper getMimeMessageHelper(boolean multipart) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
    return helper;
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