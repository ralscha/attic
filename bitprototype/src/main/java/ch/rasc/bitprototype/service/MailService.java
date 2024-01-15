package ch.rasc.bitprototype.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.bitprototype.entity.Configuration;
import ch.rasc.bitprototype.entity.ConfigurationKey;
import ch.rasc.bitprototype.entity.QConfiguration;

@Service
public class MailService {

	@PersistenceContext
	private EntityManager entityManager;

	private String defaultSender = null;

	private JavaMailSenderImpl mailSender = null;

	public void sendSimpleMessage(String to, String subject, String text) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(this.defaultSender);
		mailMessage.setTo(to);
		mailMessage.setText(text);
		mailMessage.setSubject(subject);

		this.mailSender.send(mailMessage);
	}

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

	@Transactional(readOnly = true)
	public void configure() {
		List<Configuration> configurations = new JPAQuery(this.entityManager)
				.from(QConfiguration.configuration).list(QConfiguration.configuration);

		this.mailSender = new JavaMailSenderImpl();
		this.mailSender.setHost(read(ConfigurationKey.SMTP_SERVER, configurations));
		String portString = read(ConfigurationKey.SMTP_PORT, configurations);
		if (StringUtils.isNotBlank(portString)) {
			this.mailSender.setPort(Integer.parseInt(portString));
		}
		this.mailSender.setUsername(read(ConfigurationKey.SMTP_USERNAME, configurations));
		this.mailSender.setPassword(read(ConfigurationKey.SMTP_PASSWORD, configurations));

		this.defaultSender = read(ConfigurationKey.SMTP_SENDER, configurations);
	}

	private static String read(ConfigurationKey key, List<Configuration> list) {
		for (Configuration conf : list) {
			if (conf.getConfKey() == key) {
				return conf.getConfValue();
			}
		}
		return null;
	}

}
