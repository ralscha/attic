package ch.rasc.packt.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.packt.dto.LoginStatus;
import ch.rasc.packt.entity.User;
import ch.rasc.packt.security.JpaUserDetails;

@Service
public class LoginService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailService;

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod
	public LoginStatus getStatus() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !auth.getName().equals("anonymousUser")
				&& auth.isAuthenticated()) {
			return new LoginStatus(true, null);
		}

		return new LoginStatus(false, null);
	}

	@ExtDirectMethod
	public void poke(@SuppressWarnings("unused") HttpSession session) {
		// keep session alive
	}

	// The session parameter is needed so that a session is created
	@ExtDirectMethod
	public LoginStatus login(@SuppressWarnings("unused") HttpSession session,
			String username, String password) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		UserDetails userDetail;
		try {
			userDetail = userDetailService.loadUserByUsername(username);
		}
		catch (UsernameNotFoundException notFound) {
			return new LoginStatus(false, null);
		}
		catch (Exception anotherProblem) {
			return new LoginStatus(false, null);
		}

		token.setDetails(userDetail);

		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			return new LoginStatus(auth.isAuthenticated(), auth.getName());
		}
		catch (AuthenticationException e) {
			return new LoginStatus(false, null);
		}
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional(readOnly = true)
	public User getLoggedOnUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {

			User user = entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());
			return user;

		}
		return null;
	}

}