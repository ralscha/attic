package ch.ralscha.mycustomers.data;

import javax.persistence.Entity;

@Entity
public class Customer extends AbstractEntity {

  private static final long serialVersionUID = -6905313528454962794L;

  private String firstname;
  private String lastname;
  private String email;
  private String address;
  private boolean male;
  private SizeTypes shirt = SizeTypes.MEDIUM;
  private int subs;

  public Customer() {
  }

  public Customer(String firstname, String lastname, String email, String address, boolean male, SizeTypes shirt, int subs) {
    setFirstname(firstname);
    setLastname(lastname);
    setEmail(email);
    setAddress(address);
    setMale(male);
    setShirt(shirt);
    setSubs(subs);
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isMale() {
    return male;
  }

  public void setMale(boolean male) {
    this.male = male;
  }

  public SizeTypes getShirt() {
    return shirt;
  }

  public void setShirt(SizeTypes shirt) {
    this.shirt = shirt;
  }

  public int getSubs() {
    return subs;
  }

  public void setSubs(int subs) {
    this.subs = subs;
  }

  public void addSubs(MarketingTypes marketingType) {
    this.subs &= marketingType.intValue();
  }

 



}
