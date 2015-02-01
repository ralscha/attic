package ch.rasc.e4ds.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.rasc.e4ds.entity.Role;
import ch.rasc.e4ds.entity.User;
import ch.rasc.e4ds.repository.UserRepository;

@Component
class Startup {

	private final UserRepository userRepository;

	private final DataSource dataSource;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public Startup(UserRepository userRepository, DataSource dataSource,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
		init();
	}

	public void init() {

		configureLog();

		if (this.userRepository.count() == 0) {
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
			this.userRepository.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setLocale("de");
			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setRole(Role.USER.name());
			this.userRepository.save(normalUser);
		}

	}

	private void configureLog() {

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger logger = lc.getLogger(Logger.ROOT_LOGGER_NAME);

		if (logger.getAppender("DB") == null) {
			DBAppender appender = new DBAppender();
			appender.setName("DB");
			appender.setContext(lc);

			DataSourceConnectionSource cs = new DataSourceConnectionSource();
			cs.setDataSource(this.dataSource);
			cs.setContext(lc);
			cs.start();

			appender.setConnectionSource(cs);
			appender.start();

			logger.addAppender(appender);
		}

	}

}
