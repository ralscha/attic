package ch.rasc.ab.service;

public class LoginStatus {

	private final boolean loggedIn;

	private final String username;

	public LoginStatus(boolean loggedIn, String username) {
		this.loggedIn = loggedIn;
		this.username = username;
	}

	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	public String getUsername() {
		return this.username;
	}
}