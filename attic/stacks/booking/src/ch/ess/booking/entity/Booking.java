//$Id: Booking.java,v 1.15 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.jboss.seam.annotations.Name;

@SuppressWarnings("all")
@Entity
@Name("booking")
public class Booking implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @NotNull
  private User user;

  @ManyToOne
  @NotNull
  private Hotel hotel;

  @NotNull
  @Basic
  @Temporal(TemporalType.DATE)
  private Date checkinDate;

  @Basic
  @Temporal(TemporalType.DATE)
  @NotNull
  private Date checkoutDate;

  @NotNull(message = "Credit card number is required")
  @Length(min = 16, max = 16, message = "Credit card number must 16 digits long")
  @Pattern(regex = "^\\d*$", message = "Credit card number must be numeric")
  private String creditCard;

  @NotNull(message = "Credit card name is required")
  @Length(min = 3, max = 70, message = "Credit card name must be greater then 3 and lessthen 70")
  private String creditCardName;
  private int creditCardExpiryMonth;
  private int creditCardExpiryYear;
  private boolean smoking;
  private int beds;

  @NotNull(message = "Address is required")
  private String address1;
  private String address2;

  @NotNull(message = "State is required")
  private String state;

  @NotNull(message = "zip is required")
  private String zip;

  @NotNull(message = "A phone number is required")
  private String phone;
  private String email;

  public Booking() {
      //default constructor
  }

  public Booking(Hotel hotel, User user) {
    this.hotel = hotel;
    this.user = user;
  }

  public BigDecimal getTotal() {
    return hotel.getPrice().multiply(new BigDecimal(getNights()));
  }

  public int getNights() {
    return (int)(checkoutDate.getTime() - checkinDate.getTime()) / 1000 / 60 / 60 / 24;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCheckinDate() {
    return checkinDate;
  }

  public void setCheckinDate(Date datetime) {
    this.checkinDate = datetime;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getCheckoutDate() {
    return checkoutDate;
  }

  public void setCheckoutDate(Date checkoutDate) {
    this.checkoutDate = checkoutDate;
  }

  public String getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(String creditCard) {
    this.creditCard = creditCard;
  }

  public String getDescription() {
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
    return hotel == null ? null : hotel.getName() + ", " + df.format(getCheckinDate()) + " to " + df.format(getCheckoutDate());
  }

  public boolean isSmoking() {
    return smoking;
  }

  public void setSmoking(boolean smoking) {
    this.smoking = smoking;
  }

  public int getBeds() {
    return beds;
  }

  public void setBeds(int beds) {
    this.beds = beds;
  }

  public String getCreditCardName() {
    return creditCardName;
  }

  public void setCreditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
  }

  public int getCreditCardExpiryMonth() {
    return creditCardExpiryMonth;
  }

  public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
    this.creditCardExpiryMonth = creditCardExpiryMonth;
  }

  public int getCreditCardExpiryYear() {
    return creditCardExpiryYear;
  }

  public void setCreditCardExpiryYear(int creditCardExpiryYear) {
    this.creditCardExpiryYear = creditCardExpiryYear;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Booking(" + user + "," + hotel + ")";
  }

}
