package ch.rasc.cartracker.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ch.rasc.cartracker.entity.QStaff;
import ch.rasc.cartracker.entity.Staff;

import com.mysema.query.jpa.impl.JPAQuery;

@Component
public class StaffUserDetailsService implements UserDetailsService {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Staff staff = new JPAQuery(entityManager).from(QStaff.staff)
				.where(QStaff.staff.username.eq(username)).singleResult(QStaff.staff);
		if (staff != null) {
			return new StaffUserDetails(staff);
		}

		throw new UsernameNotFoundException(username);
	}

}
