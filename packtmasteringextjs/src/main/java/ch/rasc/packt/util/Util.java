package ch.rasc.packt.util;

import javax.persistence.EntityManager;

import org.springframework.security.core.context.SecurityContextHolder;

import ch.rasc.packt.entity.User;
import ch.rasc.packt.security.JpaUserDetails;

public class Util {

	private Util() {
		// do not instantiate this class
	}

	public static User getLoggedInUser(EntityManager em) {
		Long userId = getLoggedInUserId();
		if (userId != null) {
			return em.find(User.class, userId);
		}
		return null;
	}

	public static Long getLoggedInUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			return ((JpaUserDetails) principal).getUserDbId();
		}
		return null;
	}
}
