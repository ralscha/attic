package test;

import java.io.Serializable;

public class Address implements Serializable {

  private String name;

  private String street;

  private String city;

  private Integer zipcode;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getZipcode() {
    return zipcode;
  }

  public void setZipcode(Integer zip) {
    this.zipcode = zip;
  }
}
