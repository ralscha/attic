//$Id: Hotel.java,v 1.13 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

@SuppressWarnings("all")
@Entity
@Name("hotel")
public class Hotel implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Length(max = 50)
  @NotNull
  private String name;

  @Length(max = 100)
  @NotNull
  private String address;

  @Length(max = 40)
  @NotNull
  private String city;

  @Length(max = 10)
  private String state;

  @Length(min = 2, max = 6)
  @NotNull
  private String zip;

  @Length(min = 2, max = 40)
  @NotNull
  private String country;

  @Column(precision = 6, scale = 2)
  private BigDecimal price;
  
  @Length(max = 255)
  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Hotel(" + name + "," + address + "," + city + "," + zip + ")";
  }
}
