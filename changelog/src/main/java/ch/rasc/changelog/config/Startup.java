package ch.rasc.changelog.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.rasc.changelog.entity.Configuration;
import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.CustomerBuild;
import ch.rasc.changelog.entity.QConfiguration;
import ch.rasc.changelog.entity.QCustomer;
import ch.rasc.changelog.entity.QRole;
import ch.rasc.changelog.entity.QUser;
import ch.rasc.changelog.entity.Role;
import ch.rasc.changelog.entity.User;
import ch.rasc.changelog.service.Roles;
import ch.rasc.changelog.util.Util;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		configureLog();

		if (new JPAQuery(this.entityManager).from(QUser.user).count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setUserName("admin");
			adminUser.setEmail("test@test.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setLocale("en");
			adminUser.setCreateDate(new Date());

			Role adminRole = new JPAQuery(this.entityManager).from(QRole.role)
					.where(QRole.role.name.eq(Roles.ROLE_ADMIN.name()))
					.singleResult(QRole.role);
			adminUser.setRoles(Sets.newHashSet(adminRole));

			this.entityManager.persist(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setUserName("user");
			normalUser.setEmail("user@test.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");

			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			normalUser.setLocale("de");
			normalUser.setCreateDate(new Date());

			Role userRole = new JPAQuery(this.entityManager).from(QRole.role)
					.where(QRole.role.name.eq(Roles.ROLE_USER.name()))
					.singleResult(QRole.role);
			normalUser.setRoles(Sets.newHashSet(userRole));

			this.entityManager.persist(normalUser);
		}

		if (new JPAQuery(this.entityManager).from(QCustomer.customer).count() == 0) {
			Calendar initialDate = new GregorianCalendar(2008, Calendar.FEBRUARY, 1);

			String[] names = { "Customer1", "Customer2", "Customer3" };
			String[] shortNames = { "c1", "c2", "c3" };
			String[] builds = { "5.0.0", "5.0.0", "5.0.0" };

			for (int i = 0; i < names.length; i++) {
				Customer customer = new Customer();
				customer.setLongName(names[i]);
				customer.setShortName(shortNames[i]);

				if (!builds[i].equals("")) {
					CustomerBuild build = new CustomerBuild();
					build.setCustomer(customer);
					build.setVersionDate(initialDate.getTime());
					build.setVersionNumber(builds[i]);
					customer.getBuilds().add(build);
				}

				this.entityManager.persist(customer);
			}
		}

		File indexBase = Util.getIndexBase(this.environment);
		Path indexedFile = indexBase.toPath().resolve("indexed");
		if (!Files.exists(indexedFile)) {
			FullTextEntityManager fullTextEntityManager = Search
					.getFullTextEntityManager(this.entityManager);
			try {
				fullTextEntityManager.createIndexer().startAndWait();
				Files.createFile(indexedFile);
			}
			catch (IOException | InterruptedException e) {
				LoggerFactory.getLogger(Startup.class).error("full text indexing error",
						e);
			}

		}

		configureAppLog();
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

	private void configureAppLog() {
		List<Configuration> dbConfigurations = new JPAQuery(this.entityManager)
				.from(QConfiguration.configuration).list(QConfiguration.configuration);
		Configuration dbConfiguration = null;
		if (dbConfigurations.size() > 0) {
			dbConfiguration = dbConfigurations.get(0);
			String levelString = dbConfiguration.getValue("logLevel");
			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			ch.qos.logback.classic.Logger logger = lc.getLogger("ch.rasc.changelog");
			Level level = Level.toLevel(levelString);
			if (level != null) {
				logger.setLevel(level);
			}
		}
	}

}
