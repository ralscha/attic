package ch.rasc.java8.slang;

public class Address {

	private final String street;
	private final int number;

	public Address(String street, int number) {
		this.street = street;
		this.number = number;
	}

	public String getStreet() {
		return this.street;
	}

	public int getNumber() {
		return this.number;
	}
}