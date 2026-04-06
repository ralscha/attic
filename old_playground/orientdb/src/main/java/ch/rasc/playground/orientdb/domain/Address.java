package ch.rasc.playground.orientdb.domain;

import javax.persistence.Id;
import javax.persistence.Version;

public class Address {
	@Id
	private String id;

	@Version
	private Integer version;

	private String type;
	private String street;
	private City city;

	public Address() {
	}

	public Address(String iStreet) {
		this.street = iStreet;
	}

	public Address(String iType, City iCity, String iStreet) {
		this.type = iType;
		this.city = iCity;
		this.street = iStreet;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}