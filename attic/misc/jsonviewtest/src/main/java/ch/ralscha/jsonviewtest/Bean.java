package ch.ralscha.jsonviewtest;

import org.codehaus.jackson.map.annotate.JsonView;

public class Bean {
	private String name;
	private Address address;
	private String ssn;

	@JsonView(Views.Public.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(Views.ExtendedPublic.class)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@JsonView(Views.Internal.class)
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}