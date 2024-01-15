package ch.rasc.packtsales.config;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.packtsales.entity.AppUser;
import ch.rasc.packtsales.entity.QAppUser;

@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (new JPAQuery(this.entityManager).from(QAppUser.appUser).count() == 0) {

			AppUser user = new AppUser();
			user.setLastname("admin");
			user.setFirstname("admin");
			user.setPasswd(this.passwordEncoder.encode("admin"));
			user.setEmail("admin@company.com");
			user.setStatus(1);
			user.setCreated(new Date());
			this.entityManager.persist(user);
		}
	}

}
