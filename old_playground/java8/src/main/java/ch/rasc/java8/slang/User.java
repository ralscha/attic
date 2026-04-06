package ch.rasc.java8.slang;

public class User {
	enum Gender {
		MALE, FEMALE
	}

	private final String name;

	private final Gender gender;

	public User(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public Gender getGender() {
		return this.gender;
	}

	@Override
	public String toString() {
		return "User [name=" + this.name + ", gender=" + this.gender + "]";
	}

}
