#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ${package}.entity.Configuration;
import ${package}.entity.ConfigurationKey;
import ${package}.repository.ConfigurationRepository;

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
		mailMessage.setFrom(defaultSender);
		mailMessage.setTo(to);
		mailMessage.setText(text);
		mailMessage.setSubject(subject);

		mailSender.send(mailMessage);
	}

	public void sendHtmlMessage(String from, String to, String subject, String text)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(defaultSender);
		helper.setTo(to);
		helper.setReplyTo(from);
		helper.setText(text, true);
		helper.setSubject(subject);

		mailSender.send(message);
	}

	public void configure() {
		List<Configuration> configurations = configurationRepository.findAll();

		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(read(ConfigurationKey.SMTP_SERVER, configurations));
		String portString = read(ConfigurationKey.SMTP_PORT, configurations);
		if (StringUtils.hasText(portString)) {
			mailSender.setPort(Integer.parseInt(portString));
		}
		mailSender.setUsername(read(ConfigurationKey.SMTP_USERNAME, configurations));
		mailSender.setPassword(read(ConfigurationKey.SMTP_PASSWORD, configurations));

		defaultSender = read(ConfigurationKey.SMTP_SENDER, configurations);
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
