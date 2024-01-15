package ch.ess.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Configuration extends BaseObject {

  private String name;
  private String propValue;

  @Column(nullable = false, length = 100, unique = true)
  public String getName() {
    return name;
  }

  @Column(length = 255)
  public String getPropValue() {
    return propValue;
  }

  public void setName(final String string) {
    name = string;
  }

  public void setPropValue(final String string) {
    propValue = string;
  }
  
}
