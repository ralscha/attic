package test3;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Car extends BaseObject {
  private String name;
  private Date date;
  private Boolean aktiv;
  private Time abzahlung;
  private String name2;
  private Set<Part> parts = new HashSet<Part>();

  public Time getAbzahlung() {
    return abzahlung;
  }

  public void setAbzahlung(Time abzahlung) {
    this.abzahlung = abzahlung;
  }

  public Boolean getAktiv() {
    return aktiv;
  }

  public void setAktiv(Boolean aktiv) {
    this.aktiv = aktiv;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName2() {
    return name2;
  }

  public void setName2(String name2) {
    this.name2 = name2;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
  @LazyCollection(LazyCollectionOption.EXTRA) 
  @Fetch(FetchMode.JOIN)
  public Set<Part> getParts() {
    return parts;
  }

  public void setParts(Set<Part> parts) {
    this.parts = parts;
  }
  
  

}
