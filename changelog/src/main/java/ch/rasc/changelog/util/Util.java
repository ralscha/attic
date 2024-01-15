package ch.rasc.changelog.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.rasc.changelog.entity.User;
import ch.rasc.changelog.security.JpaUserDetails;

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

	public static File getIndexBase(Environment environment) {
		String indexBase = environment.getProperty("indexBase");
		if (StringUtils.isBlank(indexBase)) {
			return new File(SystemUtils.getJavaIoTmpDir(), "changelog_index");
		}
		return new File(indexBase);
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
