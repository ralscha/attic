package ch.ess.eventlog;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * @author sr
 */
public class Mail {

  private static Log LOG = LogFactory.getLog(Mail.class);
  
  private JavaMailSender mailSender;
  EventFileReader fileReader;
  String subject;
  String from;
  String to;
  String cc;
  String title;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setFileReader(EventFileReader fileReader) {
    this.fileReader = fileReader;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void send() {

    final StringBuffer sb = new StringBuffer();
    
    try {
      List eventList = fileReader.read();
      if ((eventList != null) && (!eventList.isEmpty())) {
        sb.append("<html><head></head><body>");
        sb.append("<h3>");
        sb.append(title);
        sb.append(" ");
        sb.append(new Date());
        sb.append("</h3>");
        sb.append("<table border=\"1\"><tr><th align=\"left\">Typ</th><th align=\"left\">Date</th><th align=\"left\">Time</th><th align=\"left\">Source</th><th align=\"left\">Description</th></tr>");
        for (Iterator it = eventList.iterator(); it.hasNext();) {
          Event event = (Event)it.next();
          sb.append("<tr>");
          sb.append("<td>").append(event.getTyp()).append("</td>");          
          sb.append("<td>").append(event.getDate()).append("</td>");
          sb.append("<td>").append(event.getTime()).append("</td>");
          sb.append("<td>").append(event.getSource()).append("</td>");
          sb.append("<td>").append(event.getDescription()).append("</td>");
          sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body></html>");
      }
    } catch (IOException e) {
      LOG.error("send", e);
    }

    if (sb.length() > 0) {
      mailSender.send(
      new MimeMessagePreparator() {
        public void prepare(MimeMessage mimeMessage) throws MessagingException {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
  
          message.setSubject(subject);
          message.setFrom(from);
          if (cc != null) {
            message.setCc(cc);
          }
          message.setTo(to);
  
          message.setText(sb.toString(), true);
  
        }
      });
     
    }    
  }
}