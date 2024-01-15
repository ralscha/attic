package ch.rasc.eds.starter.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.types.dsl.Expressions;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.core.db.DataSourceConnectionSource;
import ch.rasc.eds.starter.entity.QUser;
import ch.rasc.eds.starter.entity.Role;
import ch.rasc.eds.starter.entity.User;
import ch.rasc.edsutil.JPAQueryFactory;

@Component
class Startup {

	private final JPAQueryFactory jpaQueryFactory;

	private final DataSource dataSource;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public Startup(JPAQueryFactory jpaQueryFactory, DataSource dataSource,
			PasswordEncoder passwordEncoder, TransactionTemplate transactionTemplate) {
		this.jpaQueryFactory = jpaQueryFactory;
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;

		transactionTemplate.execute(ts -> {
			init();
			return null;
		});
	}

	private void init() {

		configureLog();

		if (this.jpaQueryFactory.select(Expressions.ONE).from(QUser.user)
				.fetchFirst() == null) {
			// admin user
			User adminUser = new User();
			adminUser.setLoginName("admin@starter.com");
			adminUser.setEmail("admin@starter.com");
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setDeleted(false);
			adminUser.setRole(Role.ADMIN.name());
			this.jpaQueryFactory.getEntityManager().persist(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setLoginName("user@starter.com");
			normalUser.setEmail("user@starter.com");
			normalUser.setFirstName("user");
			normalUser.setLastName("user");
			normalUser.setLocale("de");
			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);
			adminUser.setDeleted(false);
			normalUser.setRole(Role.USER.name());
			this.jpaQueryFactory.getEntityManager().persist(normalUser);
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
