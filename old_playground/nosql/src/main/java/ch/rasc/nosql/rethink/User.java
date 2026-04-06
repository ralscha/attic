package ch.rasc.nosql.rethink;

public class User {
	private String name;
	private String email;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [name=" + this.name + ", email=" + this.email + "]";
	}

}
