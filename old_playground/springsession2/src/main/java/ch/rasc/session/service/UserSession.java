package ch.rasc.session.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String user;

	private LocalDateTime expireDateTime;

	private final String sessionId;

	public UserSession(String user) {
		this.sessionId = UUID.randomUUID().toString();
		this.user = user;
		this.expireDateTime = LocalDateTime.now().plusMinutes(15);
	}

	public String getUser() {
		return this.user;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void refreshSession() {
		this.expireDateTime = LocalDateTime.now().plusMinutes(15);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expireDateTime);
	}

}
