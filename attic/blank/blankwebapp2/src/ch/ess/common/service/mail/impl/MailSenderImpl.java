package ch.ess.common.service.mail.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.service.mail.MailException;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.MailSender;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:17 $
 */
public class MailSenderImpl implements MailSender {

  private MailConfiguration configuration;
  private final Session session = Session.getInstance(new Properties());

  public void reinit(String defaultSender, String host, int port, String user, String password) {
    configuration.setDefaultSender(defaultSender);
    configuration.setHost(host);
    configuration.setPort(port);

    if (!GenericValidator.isBlankOrNull(user)) {
      configuration.setUser(user);
    } else {
      configuration.setUser(null);
    }

    if (!GenericValidator.isBlankOrNull(password)) {
      configuration.setPassword(password);
    } else {
      configuration.setPassword(null);
    }
  }

  public void setConfiguration(MailConfiguration config) {
    configuration = config;
    session.setDebug(configuration.isDebug());
  }

  public void send(MailMessage msg) throws MailException {

    if ((msg.getFrom() == null) || (msg.getFrom().length == 0)) {
      try {
        msg.addFrom(configuration.getDefaultSender());
      } catch (UnsupportedEncodingException e) {
        throw new MailException("setDefaultSender", e);
      }
    }

    try {

      Transport transport = session.getTransport(configuration.getProtocol());

      int port = configuration.getPort();

      transport.connect(configuration.getHost(), (port == 0) ? 25 : port, configuration.getUser(), configuration
          .getPassword());

      MimeMessage mimeMessage = new MimeMessage(session);

      copyIntoMimeMessageMessage(msg, mimeMessage);

      try {
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
      } finally {
        transport.close();
      }
    } catch (MessagingException me) {
      throw new MailException("send", me);
    }

  }

  private void copyIntoMimeMessageMessage(MailMessage msg, MimeMessage mimeMessage) throws MessagingException {

    String charset = msg.getCharset();

    mimeMessage.addFrom(msg.getFrom());
    mimeMessage.setRecipients(Message.RecipientType.TO, msg.getTo());
    mimeMessage.setRecipients(Message.RecipientType.CC, msg.getCC());
    mimeMessage.setRecipients(Message.RecipientType.BCC, msg.getBCC());
    mimeMessage.setReplyTo(msg.getReplyTo());

    mimeMessage.setSubject(msg.getSubject(), charset);
    mimeMessage.setSentDate(new Date());

    if (msg.getMimeMultipart() != null) {
      mimeMessage.setContent(msg.getMimeMultipart());
    } else {
      if (msg.isHtml()) {
        mimeMessage.setContent(msg.getText(), "text/html");
      } else {
        mimeMessage.setText(msg.getText(), charset);
      }
    }
  }

}