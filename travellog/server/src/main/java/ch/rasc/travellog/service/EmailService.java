package ch.rasc.travellog.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import ch.rasc.travellog.Application;
import ch.rasc.travellog.config.AppProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	private final String defaultSender;

	private final String appUrl;

	private final String appName;

	enum EmailTemplate {
		PASSWORD_RESET("password-reset.html"), SIGNUP_CONFIRM("signup-confirm.html"),
		EMAIL_CHANGE("email-change.html"), PASSWORD_CHANGED("password-changed.html");

		private final String fileName;

		EmailTemplate(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return this.fileName;
		}
	}

	private final Map<EmailTemplate, Template> templates = new HashMap<>();

	public EmailService(JavaMailSender mailSender, AppProperties appProperties,
			Mustache.Compiler mustacheCompiler,
			@Value("${spring.application.name}") String appName) {

		this.mailSender = mailSender;
		this.defaultSender = appProperties.getDefaultEmailSender();
		this.appUrl = appProperties.getUrl();
		this.appName = appName;

		for (EmailTemplate et : EmailTemplate.values()) {
			ClassPathResource cp = new ClassPathResource("emails/" + et.getFileName());
			try (InputStream is = cp.getInputStream()) {
				this.templates.put(et,
						mustacheCompiler.compile(new InputStreamReader(is)));
			}
			catch (IOException e) {
				Application.log.error("mustache compile failed", e);
			}
		}
	}

	@Async
	public void sendPasswordResetEmail(String email, String resetToken) {
		String resetLink = this.appUrl.trim();
		if (!resetLink.endsWith("/")) {
			resetLink += "/";
		}
		resetLink += "#/password-reset/" + resetToken;

		Map<String, Object> data = new HashMap<>();
		data.put("resetLink", resetLink);

		try {
			sendHtmlMessage(this.defaultSender, email,
					this.appName + ": " + "Password Reset",
					this.templates.get(EmailTemplate.PASSWORD_RESET).execute(data));
		}
		catch (MessagingException e) {
			Application.log.error("sendPasswordResetEmail", e);
		}
	}

	@Async
	public void sendEmailConfirmationEmail(String email, String confirmationToken) {
		String confirmationLink = this.appUrl.trim();
		if (!confirmationLink.endsWith("/")) {
			confirmationLink += "/";
		}
		confirmationLink += "#/signup-confirm/" + confirmationToken;

		Map<String, Object> data = new HashMap<>();
		data.put("confirmationLink", confirmationLink);

		try {
			sendHtmlMessage(this.defaultSender, email,
					this.appName + ": " + "Sign Up Confirmation",
					this.templates.get(EmailTemplate.SIGNUP_CONFIRM).execute(data));
		}
		catch (MessagingException e) {
			Application.log.error("sendEmailConfirmationEmail", e);
		}
	}

	@Async
	public void sendEmailChangeConfirmationEmail(String email, String confirmationToken) {
		String confirmationLink = this.appUrl.trim();
		if (!confirmationLink.endsWith("/")) {
			confirmationLink += "/";
		}
		confirmationLink += "#/email-change-confirm/" + confirmationToken;

		Map<String, Object> data = new HashMap<>();
		data.put("confirmationLink", confirmationLink);

		try {
			sendHtmlMessage(this.defaultSender, email,
					this.appName + ": " + "Email Change Confirmation",
					this.templates.get(EmailTemplate.EMAIL_CHANGE).execute(data));
		}
		catch (MessagingException e) {
			Application.log.error("sendEmailChangeConfirmationEmail", e);
		}
	}

	@Async
	public void sendPasswordChangedEmail(String email) {
		String passwordResetLink = this.appUrl.trim();
		if (!passwordResetLink.endsWith("/")) {
			passwordResetLink += "/";
		}
		passwordResetLink += "#/password-reset-request";

		Map<String, Object> data = new HashMap<>();
		data.put("resetLink", passwordResetLink);

		try {
			sendHtmlMessage(this.defaultSender, email,
					this.appName + ": " + "Password Changed",
					this.templates.get(EmailTemplate.PASSWORD_CHANGED).execute(data));
		}
		catch (MessagingException e) {
			Application.log.error("sendPasswordChangedEmail", e);
		}
	}

	@SuppressWarnings("null")
	private void sendHtmlMessage(String from, String to, String subject, String text)
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
