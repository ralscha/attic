package ch.rasc.ab.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.ab.entity.AppUser;

@Component
public class InitialDataLoad implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (new JPAQuery(this.entityManager).from(QAppUser.appUser).notExists()) {
			// admin user
			AppUser adminUser = new AppUser();
			adminUser.setUserName("admin");
			adminUser.setEmail("admin@admin.ch");
			adminUser.setFirstName("admin");
			adminUser.setName("admin");
			adminUser.setPasswordHash(this.passwordEncoder.encode("admin"));
			adminUser.setEnabled(true);
			adminUser.setAdmin(true);
			this.entityManager.persist(adminUser);

			// ordinary user
			AppUser ordinaryUser = new AppUser();
			ordinaryUser.setUserName("user");
			ordinaryUser.setEmail("user@user.ch");
			ordinaryUser.setFirstName("user");
			ordinaryUser.setName("user");
			ordinaryUser.setPasswordHash(this.passwordEncoder.encode("user"));
			ordinaryUser.setEnabled(true);
			ordinaryUser.setAdmin(false);
			this.entityManager.persist(ordinaryUser);
		}

	}

}
