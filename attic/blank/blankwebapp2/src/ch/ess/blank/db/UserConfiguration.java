package ch.ess.blank.db;

import ch.ess.common.db.Persistent;

/** A business entity class representing a UserConfiguration
 * 
 * @author  Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:14 $ 
 * @hibernate.class  table="UserConfiguration"
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
   * @hibernate.many-to-one class="ch.ess.blank.db.User"
   * @hibernate.column name="userId" not-null="true" index="FK_UserConfiguration_User"  
   */
  public User getUser() {
    return user;
  }

  public void setUser(User u) {
    user = u;
  }

}