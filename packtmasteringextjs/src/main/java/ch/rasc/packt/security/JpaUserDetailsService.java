package ch.rasc.packt.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.packt.entity.QUser;
import ch.rasc.packt.entity.User;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class JpaUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = new JPAQuery(entityManager).from(QUser.user)
				.where(QUser.user.userName.eq(username)).singleResult(QUser.user);
		if (user != null) {
			return new JpaUserDetails(user);
		}

		throw new UsernameNotFoundException(username);
	}

}
