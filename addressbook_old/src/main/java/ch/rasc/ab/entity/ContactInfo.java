package ch.rasc.ab.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Model(value = "Addressbook.model.ContactInfo",
		readMethod = "contactInfoService.readContactInfo",
		createMethod = "contactInfoService.create",
		updateMethod = "contactInfoService.update",
		destroyMethod = "contactInfoService.destroy", paging = true,
		disablePagingParameters = true)
public class ContactInfo extends AbstractPersistable {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contactId")
	private Contact contact;

	@Transient
	private Long contactId;

	@Size(max = 32)
	@NotEmpty(message = "Please enter a Info Type")
	private String infoType;

	@Size(max = 100)
	private String phone;

	@Size(max = 255)
	@Email(message = "Please enter a valid Email")
	private String email;

	@Size(max = 255)
	private String address;

	@Size(max = 100)
	private String city;

	@Size(max = 32)
	private String state;

	@Size(max = 32)
	private String zip;

	@Size(max = 100)
	private String country;

	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getContactId() {
		return this.contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

}
