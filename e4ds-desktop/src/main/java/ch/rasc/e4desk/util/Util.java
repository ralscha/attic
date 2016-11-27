package ch.rasc.e4desk.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.rasc.e4desk.entity.User;
import ch.rasc.e4desk.security.JpaUserDetails;

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

	public static JpaUserDetails getLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {
			return (JpaUserDetails) principal;
		}
		return null;
	}

	public static boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}

		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return false;
		}

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority())) {
				return true;
			}
		}

		return false;
	}

}
