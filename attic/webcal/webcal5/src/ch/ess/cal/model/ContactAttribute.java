package ch.ess.cal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import ch.ess.base.model.BaseObject;

@Entity
@Proxy(lazy = false)
public class ContactAttribute extends BaseObject {
  private String field;
  private String value;
  private Contact contact;

  @ManyToOne
  @JoinColumn(name = "contactId", nullable = false)
  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact contact) {
    this.contact = contact;
  }

  @Column(nullable = false, length = 255)
  public String getField() {
    return field;
  }

  public void setField(String key) {
    this.field = key;
  }

  @Column(nullable = false, length = 1000)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
