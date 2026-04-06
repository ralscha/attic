package ch.rasc.cors;

public class User {
	private final int id;

	private final String loginId;

	public User(int id, String loginId) {
		super();
		this.id = id;
		this.loginId = loginId;
	}

	public int getId() {
		return this.id;
	}

	public String getLoginId() {
		return this.loginId;
	}

}
