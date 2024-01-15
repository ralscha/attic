package ch.rasc.ab.config;

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

import ch.rasc.ab.entity.QUser;
import ch.rasc.ab.entity.Role;
import ch.rasc.ab.entity.User;
import ch.rasc.ab.service.MailService;

import com.mysema.query.jpa.impl.JPAQuery;

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

		mailService.configure();

		if (new JPAQuery(entityManager).from(QUser.user).count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setRole(Role.ADMIN.name());
			entityManager.persist(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setLocale("de");
			normalUser.setPasswordHash(passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setRole(Role.USER.name());
			entityManager.persist(normalUser);
		}

	}

	private void configureLog() {
		Logger logger = (Logger) LogManager.getRootLogger();
		if (logger.getAppenders().containsKey("databaseAppender")) {
			// already configured
			return;
		}

		DataSourceConnectionSource connectionSource = DataSourceConnectionSource
				.createConnectionSource("java:comp/env/jdbc/ab");
		List<ColumnConfig> columnConfigs = new ArrayList<>();
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "eventDate", null, null, "true", null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "level", "%level", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "logger", "%logger", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "source", "%location", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "message", "%message", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "marker", "%marker", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "thread", "%thread", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "exception", "%rEx{full}", null, null, null, null));
		columnConfigs.add(ColumnConfig
				.createColumnConfig(logger.getContext().getConfiguration(), "userName",
						"%mdc{userName}", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "ip", "%mdc{ip}", null, null, null, null));
		columnConfigs.add(ColumnConfig.createColumnConfig(logger.getContext()
				.getConfiguration(), "userAgent", "%mdc{userAgent}", null, null, null,
				null));

		JdbcAppender appender = JdbcAppender.createAppender("databaseAppender", null,
				null, connectionSource, null, "LogEvent",
				columnConfigs.toArray(new ColumnConfig[columnConfigs.size()]));
		logger.addAppender(appender);
		appender.start();

		if (environment.acceptsProfiles("production")) {
			logger.removeAppender(logger.getAppenders().get("stdout"));
		}

	}

}
