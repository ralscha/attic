package ch.ess.cal.db;

import java.io.Serializable;

public class EventProperty implements Serializable {

  private String name;
  private String propValue;

  
  /** 
  * @hibernate.property length="100" not-null="true"
  */
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** 
  * @hibernate.property length="255"
  */
  public String getPropValue() {
    return this.propValue;
  }

  public void setPropValue(String propValue) {
    this.propValue = propValue;
  }

}
