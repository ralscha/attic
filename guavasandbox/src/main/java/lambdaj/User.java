package lambdaj;

public class User {
	private final String userName;

	private final String email;

	private final int age;

	public User(String userName, String email, int age) {
		this.userName = userName;
		this.email = email;
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + ", age=" + age + "]";
	}

}
