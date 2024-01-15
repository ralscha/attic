package ch.ess.addressbook.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import ch.ess.addressbook.db.*;

public class _Contact {

	private int id;
	private String firstname;
	private String name;
	private String company;
	private String address;
	private String plz;
	private String city;
	private String country;
	private String email;
	private String mobil;
	private String businessphone;
	private String privatephone;
	private String key1;
	private String value1;
	private String key2;
	private String value2;
	private String key3;
	private String value3;

	public _Contact() {
		this.id = 0;
		this.firstname = null;
		this.name = null;
		this.company = null;
		this.address = null;
		this.plz = null;
		this.city = null;
		this.country = null;
		this.email = null;
		this.mobil = null;
		this.businessphone = null;
		this.privatephone = null;
		this.key1 = null;
		this.value1 = null;
		this.key2 = null;
		this.value2 = null;
		this.key3 = null;
		this.value3 = null;
	}

	public _Contact(int id, String firstname, String name, String company, String address, String plz, String city, String country, String email, String mobil, String businessphone, String privatephone, String key1, String value1, String key2, String value2, String key3, String value3) {
		this.id = id;
		this.firstname = firstname;
		this.name = name;
		this.company = company;
		this.address = address;
		this.plz = plz;
		this.city = city;
		this.country = country;
		this.email = email;
		this.mobil = mobil;
		this.businessphone = businessphone;
		this.privatephone = privatephone;
		this.key1 = key1;
		this.value1 = value1;
		this.key2 = key2;
		this.value2 = value2;
		this.key3 = key3;
		this.value3 = value3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getBusinessphone() {
		return businessphone;
	}

	public void setBusinessphone(String businessphone) {
		this.businessphone = businessphone;
	}

	public String getPrivatephone() {
		return privatephone;
	}

	public void setPrivatephone(String privatephone) {
		this.privatephone = privatephone;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}


	public String toString() {
		return "_Contact("+ id + " " + firstname + " " + name + " " + company + " " + address + " " + plz + " " + city + " " + country + " " + email + " " + mobil + " " + businessphone + " " + privatephone + " " + key1 + " " + value1 + " " + key2 + " " + value2 + " " + key3 + " " + value3+")";
	}
}
