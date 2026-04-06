package ch.rasc.java8.slang;

public class Person {

	private final String name;
	private final Address address;

	public Person(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public Address getAddress() {
		return this.address;
	}
}