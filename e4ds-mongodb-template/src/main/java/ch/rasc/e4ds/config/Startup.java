package ch.rasc.e4ds.config;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;
import ch.rasc.e4ds.domain.User;

import com.google.common.collect.Sets;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Environment environment;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (mongoTemplate.count(new Query(), User.class) == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(passwordEncoder.encodePassword("admin", null));
			adminUser.setEnabled(true);
			adminUser.setLocale("en");
			adminUser.setRoles(Sets.newHashSet("ROLE_ADMIN"));
			mongoTemplate.save(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setPasswordHash(passwordEncoder.encodePassword("user", null));
			normalUser.setEnabled(true);
			normalUser.setLocale("de");
			normalUser.setRoles(Sets.newHashSet("ROLE_USER"));
			mongoTemplate.save(normalUser);
		}

	}

	@PostConstruct
	public void setupLog() {
		boolean development = environment.acceptsProfiles("development");

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		lc.reset();

		ConsoleAppender<ILoggingEvent> consoleAppender = null;
		if (development) {
			PatternLayoutEncoder encoder = new PatternLayoutEncoder();
			encoder.setContext(lc);
			encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
			encoder.start();

			consoleAppender = new ConsoleAppender<>();
			consoleAppender.setContext(lc);
			consoleAppender.setEncoder(encoder);
			consoleAppender.start();
		}

		MongoDBAppender appender = new MongoDBAppender(mongoTemplate);
		appender.setContext(lc);
		appender.start();

		Logger appLogger = lc.getLogger("ch.rasc.e4ds");
		appLogger.setLevel(Level.WARN);

		Logger rootLogger = lc.getLogger("root");
		rootLogger.setLevel(Level.WARN);
		if (development) {
			rootLogger.addAppender(consoleAppender);
		}
		rootLogger.addAppender(appender);
		lc.start();

		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);

	}

}
