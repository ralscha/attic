package ch.rasc.springplayground.tx;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Address extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -2961520776208650065L;

	public enum Type {
		WORK, HOME
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;

	@Length(max = 255)
	private String street;

	@Length(max = 50)
	private String postalCode;

	@Length(max = 255)
	private String city;

	@Length(max = 2)
	private String country;

	@ManyToOne
	private Customer customer;

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Address [type=" + this.type + ", street=" + this.street + ", postalCode="
				+ this.postalCode + ", city=" + this.city + ", country=" + this.country
				+ ", customer=" + this.customer + "]";
	}

}
