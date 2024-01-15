package ch.ess.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class TextResource extends TranslateObject {

  private String name;
  private String feature;
  
  @Column(length = 100, nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(final String string) {
    name = string;
  }

  @Column(length = 255)
  public String getFeature() {
    return feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

}
