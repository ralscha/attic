package ch.rasc.bitprototype.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.DataSourceConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.bitprototype.entity.QUser;
import ch.rasc.bitprototype.entity.Role;
import ch.rasc.bitprototype.entity.User;
import ch.rasc.bitprototype.service.MailService;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MailService mailService;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		configureLog();

		this.mailService.configure();

		if (new JPAQuery(this.entityManager).from(QUser.user).count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setRole(Role.ADMIN.name());
			this.entityManager.persist(adminUser);

			// bedarf
			User normalUser = new User();
			normalUser.setUserName("bedarf");
			normalUser.setEmail("bedarf@test.ch");
			normalUser.setFirstName("bedarf");
			normalUser.setName("bedarf");
			normalUser.setLocale("de");

			normalUser.setPasswordHash(this.passwordEncoder.encode("bedarf"));
			normalUser.setEnabled(true);

			normalUser.setRole(Role.BEDARF.name());

			this.entityManager.persist(normalUser);

			// einkauf
			normalUser = new User();
			normalUser.setUserName("einkauf");
			normalUser.setEmail("einkauf@test.ch");
			normalUser.setFirstName("einkauf");
			normalUser.setName("einkauf");
			normalUser.setLocale("de");

			normalUser.setPasswordHash(this.passwordEncoder.encode("einkauf"));
			normalUser.setEnabled(true);

			normalUser.setRole(Role.EINKAUF.name());

			this.entityManager.persist(normalUser);

			// lieferant
			normalUser = new User();
			normalUser.setUserName("lieferant");
			normalUser.setEmail("lieferant@test.ch");
			normalUser.setFirstName("lieferant");
			normalUser.setName("lieferant");
			normalUser.setLocale("de");

			normalUser.setPasswordHash(this.passwordEncoder.encode("lieferant"));
			normalUser.setEnabled(true);

			normalUser.setRole(Role.LIEFERANT.name());

			this.entityManager.persist(normalUser);

		}

	}

	private void configureLog() {
		Logger logger = (Logger) LogManager.getRootLogger();
		if (logger.getAppenders().containsKey("databaseAppender")) {
			// already configured
			return;
		}

		DataSourceConnectionSource connectionSource = DataSourceConnectionSource
				.createConnectionSource("java:comp/env/jdbc/bitp");
		List<ColumnConfig> columnConfigs = new ArrayList<>();
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"eventDate", null, null, "true", null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"level", "%level", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"logger", "%logger", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"source", "%location", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"message", "%message", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"marker", "%marker", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"thread", "%thread", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"exception", "%rEx{full}", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"userName", "%mdc{userName}", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"ip", "%mdc{ip}", null, null, null, null));
		columnConfigs.add(
				ColumnConfig.createColumnConfig(logger.getContext().getConfiguration(),
						"userAgent", "%mdc{userAgent}", null, null, null, null));

		JdbcAppender appender = JdbcAppender.createAppender("databaseAppender", null,
				null, connectionSource, null, "LogEvent",
				columnConfigs.toArray(new ColumnConfig[columnConfigs.size()]));
		logger.addAppender(appender);
		appender.start();

		if (this.environment.acceptsProfiles("production")) {
			logger.removeAppender(logger.getAppenders().get("stdout"));
		}

	}

}
