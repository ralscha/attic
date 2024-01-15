package ch.rasc.proto.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.unbescape.html.HtmlEscape;

import ch.rasc.proto.config.AppProperties;
import ch.rasc.proto.entity.User;

@Service
public class MailService {

	private final JavaMailSender mailSender;
	private final String defaultSender;
	private final MessageSource messageSource;
	private final AppProperties appProperties;

	@Autowired
	public MailService(JavaMailSender mailSender, MessageSource messageSource,
			AppProperties appProperties) {
		this.mailSender = mailSender;
		this.defaultSender = appProperties.getDefaultEmailSender();
		this.messageSource = messageSource;
		this.appProperties = appProperties;
	}

	@Async
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(this.defaultSender);
		mailMessage.setTo(to);
		mailMessage.setText(text);
		mailMessage.setSubject(subject);
		this.mailSender.send(mailMessage);
	}

	@Async
	public void sendPasswortResetEmail(User receiver, String token) {
		String link = this.appProperties.getUrl() + "/passwordreset.html?token="
				+ Base64.getUrlEncoder().encodeToString(token.getBytes());

		try {
			Locale userLocale = new Locale(receiver.getLocale());
			sendHtmlMessage(
					this.defaultSender,
					receiver.getEmail(),
					this.messageSource.getMessage("user_passwordreset_emailsubject",
							null, userLocale),
					getEmailText(userLocale)
							.replace("{login}",
									"<strong>" + receiver.getLoginName() + "</strong>")
							.replace("\n", "<br>")
							.replace("{link}",
									"<a href=\"" + link + "\">" + link + "</a>"));
		}
		catch (MessagingException | IOException e) {
			LoggerFactory.getLogger(MailService.class).error("sendPasswortResetEmail", e);
		}
	}

	private static String getEmailText(Locale locale) throws IOException {
		String resource = "password_reset_email.txt";
		if (locale != null && locale.getLanguage().toLowerCase().equals("de")) {
			resource = "password_reset_email_de.txt";
		}
		ClassPathResource cp = new ClassPathResource(resource);
		try (InputStream is = cp.getInputStream()) {
			String text = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
			text = HtmlEscape.escapeHtml4(text);
			return text;
		}
	}

	@Async
	public void sendHtmlMessage(String from, String to, String subject, String text)
			throws MessagingException {
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(this.defaultSender);
		helper.setTo(to);
		helper.setReplyTo(from);
		helper.setText(text, true);
		helper.setSubject(subject);

		this.mailSender.send(message);
	}

}
