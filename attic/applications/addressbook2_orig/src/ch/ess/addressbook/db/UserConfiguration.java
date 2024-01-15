package ch.ess.addressbook.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a UserConfiguration
  * 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @hibernate.class  table="abUserConfiguration"
  */
public class UserConfiguration extends Persistent {

  private String name;
  private String propValue;
  private User user;


  /** 
  * @hibernate.property length="100" not-null="true" unique="false"
  */
  public String getName() {
    return name;
  }

  /** 
  * @hibernate.property length="255"
  */
  public String getPropValue() {
    return propValue;
  }

  public void setName(String string) {
    name = string;
  }

  public void setPropValue(String string) {
    propValue = string;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.addressbook.db.User" column="userId" not-null="true"
  */
  public User getUser() {
    return user;
  }

  public void setUser(User u) {
    user = u;
  }

}
