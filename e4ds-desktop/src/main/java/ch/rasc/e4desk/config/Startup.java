package ch.rasc.e4desk.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.e4desk.entity.QRole;
import ch.rasc.e4desk.entity.QUser;
import ch.rasc.e4desk.entity.Role;
import ch.rasc.e4desk.entity.User;
import ch.rasc.e4desk.service.MailService;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		this.mailService.configure();

		if (new JPAQuery(this.entityManager).from(QUser.user).count() == 0) {
			// admin user
			User adminUser = new User();
			adminUser.setEmail("admin@e4desk.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setLocale("en");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);

			Role adminRole = new JPAQuery(this.entityManager).from(QRole.role)
					.where(QRole.role.name.eq("ROLE_ADMIN")).singleResult(QRole.role);
			adminUser.setRoles(Sets.newHashSet(adminRole));

			this.entityManager.persist(adminUser);

			// normal user
			User normalUser = new User();
			normalUser.setEmail("user@e4desk.ch");
			normalUser.setFirstName("user");
			normalUser.setName("user");
			normalUser.setLocale("de");

			normalUser.setPasswordHash(this.passwordEncoder.encode("user"));
			normalUser.setEnabled(true);

			Role userRole = new JPAQuery(this.entityManager).from(QRole.role)
					.where(QRole.role.name.eq("ROLE_USER")).singleResult(QRole.role);
			normalUser.setRoles(Sets.newHashSet(userRole));

			this.entityManager.persist(normalUser);
		}

	}

}
