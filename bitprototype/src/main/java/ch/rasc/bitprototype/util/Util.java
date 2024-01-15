package ch.rasc.bitprototype.util;

import javax.persistence.EntityManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.rasc.bitprototype.entity.Role;
import ch.rasc.bitprototype.entity.User;
import ch.rasc.bitprototype.security.JpaUserDetails;

public class Util {

	private Util() {
		// do not instantiate this class
	}

	public static void signin(User user) {
		JpaUserDetails principal = new JpaUserDetails(user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, null, principal.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(token);
	}

	public static boolean userInRole(Role role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}

		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return false;
		}

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.name().equals(auth.getAuthority())) {
				return true;
			}
		}

		return false;
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
