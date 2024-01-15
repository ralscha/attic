package ch.ess.addressbook.form;

import org.apache.struts.action.ActionForm;

public final class ContactForm implements ActionForm  {
  
	private String firstname = "";
	private String name = "";
	private String company = "";
	private String address = "";
	private String plz = "";
	private String city = "";
	private String country = "";
	private String email = "";
	private String mobilephone = "";
	private String workphone = "";
	private String homephone = "";
	private String key1 = "";
	private String value1 = "";
	private String key2 = "";
	private String value2 = "";
	private String key3 = "";
	private String value3 = "";	
	private String notes = "";

	public String getFirstname() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		if (firstname == null)
			this.firstname = "";
		else
			this.firstname = firstname;	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			this.name = "";
		else
			this.name = name;	
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		if (company == null)
			this.company = "";
		else
			this.company = company;	
	}	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address == null)
			this.address = "";
		else
			this.address = address;	
	}	

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		if (plz == null)
			this.plz = "";
		else
			this.plz = plz;	
	}	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city == null)
			this.city = "";
		else
			this.city = city;	
	}	

	public String getCountry() {
		return country;
	}

	public void setCountry(String plz) {
		if (country == null)
			this.country = "";
		else
			this.country = country;	
	}		

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null)
			this.email = "";
		else
			this.email = email;	
	}
	
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		if (mobilephone == null)
			this.mobilephone = "";
		else
			this.mobilephone = mobilephone;	
	}	
	
	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		if (workphone == null)
			this.workphone = "";
		else
			this.workphone = workphone;	
	}	

	
	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		if (homephone == null)
			this.homephone = "";
		else
			this.homephone = homephone;	
	}	
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		if (notes == null)
			this.notes = "";
		else
			this.notes = notes;	
	}		

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		if (value1 == null)
			this.value1 = "";
		else
			this.value1 = value1;	
	}		

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		if (value2 == null)
			this.value2 = "";
		else
			this.value2 = value2;	
	}	

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		if (value3 == null)
			this.value3 = "";
		else
			this.value3 = value3;	
	}	
	
	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		if (key1 == null)
			this.key1 = "";
		else
			this.key1 = key1;	
	}		

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		if (key2 == null)
			this.key2 = "";
		else
			this.key2 = key2;	
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		if (key3 == null)
			this.key3 = "";
		else
			this.key3 = key3;	
	}	
}
