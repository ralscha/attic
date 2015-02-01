package ch.rasc.e4ds.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ch.rasc.e4ds.entity.Configuration;
import ch.rasc.e4ds.entity.ConfigurationKey;
import ch.rasc.e4ds.repository.ConfigurationRepository;

@Service
public class MailService {

	private final ConfigurationRepository configurationRepository;

	private String defaultSender = null;

	private JavaMailSenderImpl mailSender = null;

	@Autowired
	public MailService(ConfigurationRepository configurationRepository) {
		this.configurationRepository = configurationRepository;
		configure();
	}

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

	public void configure() {
		List<Configuration> configurations = this.configurationRepository.findAll();

		this.mailSender = new JavaMailSenderImpl();
		this.mailSender.setHost(read(ConfigurationKey.SMTP_SERVER, configurations));
		String portString = read(ConfigurationKey.SMTP_PORT, configurations);
		if (StringUtils.hasText(portString)) {
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
