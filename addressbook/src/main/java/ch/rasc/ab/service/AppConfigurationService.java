package ch.rasc.ab.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.ab.dto.ConfigurationDto;
import ch.rasc.ab.entity.Configuration;
import ch.rasc.ab.entity.ConfigurationKey;
import ch.rasc.ab.entity.QConfiguration;

import com.mysema.query.jpa.impl.JPAQuery;

@Service
@Lazy
public class AppConfigurationService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MailService mailService;

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	public void sendTestEmail(String to) {
		mailService.sendSimpleMessage(to, "TEST EMAIL", "THIS IS A TEST MESSAGE");
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional(readOnly = true)
	public ConfigurationDto read() {

		ConfigurationDto dto = new ConfigurationDto();

		Logger logger = (Logger) LogManager.getLogger("ch.rasc.ab");
		String level = logger != null && logger.getLevel() != null ? logger.getLevel()
				.toString() : "ERROR";

		dto.setLogLevel(level);

		List<Configuration> configurations = new JPAQuery(entityManager).from(
				QConfiguration.configuration).list(QConfiguration.configuration);

		dto.setSender(read(ConfigurationKey.SMTP_SENDER, configurations));
		dto.setServer(read(ConfigurationKey.SMTP_SERVER, configurations));
		String port = read(ConfigurationKey.SMTP_PORT, configurations);
		if (port != null) {
			dto.setPort(Integer.parseInt(port));
		}
		else {
			dto.setPort(25);
		}
		dto.setUsername(read(ConfigurationKey.SMTP_USERNAME, configurations));
		dto.setPassword(read(ConfigurationKey.SMTP_PASSWORD, configurations));

		return dto;
	}

	private static String read(ConfigurationKey key, List<Configuration> list) {
		for (Configuration conf : list) {
			if (conf.getConfKey() == key) {
				return conf.getConfValue();
			}
		}
		return null;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void save(ConfigurationDto data) {
		Logger logger = (Logger) LogManager.getLogger("ch.rasc.ab");
		Level level = Level.toLevel(data.getLogLevel());
		if (level != null) {
			logger.setLevel(level);
		}

		update(ConfigurationKey.SMTP_SENDER, data.getSender());
		update(ConfigurationKey.SMTP_SERVER, data.getServer());
		update(ConfigurationKey.SMTP_PORT, String.valueOf(data.getPort()));
		update(ConfigurationKey.SMTP_USERNAME, data.getUsername());
		update(ConfigurationKey.SMTP_PASSWORD, data.getPassword());

		mailService.configure();

	}

	private void update(ConfigurationKey key, String value) {

		Configuration conf = new JPAQuery(entityManager)
				.from(QConfiguration.configuration)
				.where(QConfiguration.configuration.confKey.eq(key))
				.singleResult(QConfiguration.configuration);

		if (StringUtils.isNotBlank(value)) {

			if (conf != null) {
				conf.setConfValue(value);
			}
			else {
				conf = new Configuration();
				conf.setConfKey(key);
				conf.setConfValue(value);
				entityManager.persist(conf);
			}

		}
		else {

			if (conf != null) {
				entityManager.remove(conf);
			}

		}
	}

}
