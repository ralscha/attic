package ch.ess.map;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.validator.Digits;
import org.hibernate.validator.Length;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Entity
@Name("marker")
@Scope(ScopeType.EVENT)
public class Marker {

  private Integer id;
  private BigDecimal lat;
  private BigDecimal lng;
  private String found;
  private String leftStr;
  private String icon;

  @Id
  @GeneratedValue
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Digits(integerDigits = 5, fractionalDigits = 15)
  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  @Digits(integerDigits = 5, fractionalDigits = 15)
  public BigDecimal getLng() {
    return lng;
  }

  public void setLng(BigDecimal lng) {
    this.lng = lng;
  }

  @Length(max = 100)
  public String getFound() {
    return found;
  }

  public void setFound(String found) {
    this.found = found;
  }

  @Length(max = 100)
  public String getLeftStr() {
    return leftStr;
  }

  public void setLeftStr(String left) {
    this.leftStr = left;
  }

  @Length(max = 255)
  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

}
