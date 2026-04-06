package ch.rasc.session.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginManager {

	private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpServletResponse response,
			HttpSession session) {

		if ("admin".equals(username) && "admin".equals(password)) {
			session.setAttribute("user", 1);
			UserSession userSession = new UserSession("admin");
			this.sessions.put(userSession.getSessionId(), userSession);

			session.setAttribute("userSession", userSession);

			return userSession.getSessionId();
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	public boolean isSessionValid(String sessionid) {
		UserSession userSession = this.sessions.get(sessionid);
		if (userSession != null) {
			userSession.refreshSession();
			return true;
		}
		return false;
	}

	public Collection<UserSession> getSessions() {
		return Collections.unmodifiableCollection(this.sessions.values());
	}

	public void removeSession(String sessionid) {
		this.sessions.remove(sessionid);
	}

}
