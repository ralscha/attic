package ch.rasc.ab.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.ab.entity.AccessLog;
import ch.rasc.ab.entity.User;
import ch.rasc.ab.security.JpaUserDetails;
import ch.rasc.ab.util.Util;

@Service
public class SecurityService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	@Transactional
	public User getLoggedOnUser(HttpServletRequest request, HttpSession session) {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (principal instanceof JpaUserDetails) {

			User user = entityManager.find(User.class,
					((JpaUserDetails) principal).getUserDbId());

			AccessLog accessLog = new AccessLog();
			accessLog.setUserName(user.getUserName());
			accessLog.setSessionId(session.getId());
			accessLog.setLogIn(DateTime.now());
			accessLog.setUserAgent(request.getHeader("User-Agent"));

			entityManager.persist(accessLog);

			return user;

		}
		return null;
	}

	@ExtDirectMethod
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public boolean switchUser(Long userId) {
		User switchToUser = entityManager.find(User.class, userId);
		if (switchToUser != null) {
			Util.signin(switchToUser);
			return true;
		}

		return false;
	}

}
