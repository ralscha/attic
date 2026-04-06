package ch.rasc.jool;

public class Person {
	private final String firstName;
	private final String lastName;
	private final int age;
	private final double height;
	private final double weight;

	public Person(String firstName, String lastName, int age, double height,
			double weight) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.height = height;
		this.weight = weight;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public int getAge() {
		return this.age;
	}

	public double getHeight() {
		return this.height;
	}

	public double getWeight() {
		return this.weight;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + this.firstName + ", lastName=" + this.lastName
				+ ", age=" + this.age + ", height=" + this.height + ", weight="
				+ this.weight + "]";
	}

}