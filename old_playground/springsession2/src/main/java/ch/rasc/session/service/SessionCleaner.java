package ch.rasc.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SessionCleaner {

	private final LoginManager loginManager;

	@Autowired
	public SessionCleaner(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	@Scheduled(fixedDelay = 1000 * 60)
	public void destroySessions() {
		for (UserSession userSession : this.loginManager.getSessions()) {

			if (userSession.isExpired()) {
				System.out.println("remove : " + userSession.getSessionId());
				this.loginManager.removeSession(userSession.getSessionId());
			}

		}
	}

}
