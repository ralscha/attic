package @packageProject@.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class NewPasswordMailSender {

  final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  Configuration freemarkerMailConfiguration;

  @Autowired
  private JavaMailSender mailSender;

  private String template = "newpassword";
  private String from = "no-reply@test.ch";

  public void send(final String email, String newPassword) {
    
    final Map<String, Object> model = new HashMap<String, Object>();
    model.put("newPassword", newPassword);

    MimeMessagePreparator preparator = new MimeMessagePreparator() {

      public void prepare(MimeMessage mm) throws MessagingException {

        MimeMessageHelper mimeMessage = new MimeMessageHelper(mm, true, "UTF-8");
        mimeMessage.setFrom(getFrom());
        mimeMessage.setTo(email);
        mimeMessage.setSubject("@projectName@: Your new Password");

        try {
          String resultTxt = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerMailConfiguration.getTemplate(getTemplate()
              + "-txt.ftl"), model);
          String resultHtml = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerMailConfiguration.getTemplate(getTemplate()
              + "-html.ftl"), model);
          mimeMessage.setText(resultTxt, resultHtml);
        } catch (IOException e) {
          log.error("send", e);
        } catch (TemplateException e) {
          log.error("send", e);
        }

      }
    };

    mailSender.send(preparator);

  }

  public String getFrom() {
    return from;
  }

  public String getTemplate() {
    return template;
  }

}