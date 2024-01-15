package ch.rasc.packtsales.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.jpa.impl.JPAQuery;

import ch.rasc.packtsales.entity.AppUser;
import ch.rasc.packtsales.entity.QAppUser;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		AppUser user = new JPAQuery(this.entityManager).from(QAppUser.appUser)
				.where(QAppUser.appUser.email.eq(username))
				.singleResult(QAppUser.appUser);
		if (user != null) {
			return new JpaUserDetails(user);
		}

		throw new UsernameNotFoundException(username);
	}

}
