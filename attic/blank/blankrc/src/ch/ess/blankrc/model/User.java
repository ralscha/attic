package ch.ess.blankrc.model;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/** A business entity class representing an User
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @hibernate.class  table="User" lazy="true"
 * 
 * @hibernate.query name="findWithUserNameAndPassword"
 *  query="from User as u where u.userName = ? AND u.passwordHash = ?"
 * 
 * @hibernate.query name="findWithUserName"
 *  query="from User as u where u.userName = ?"
 */

public class User extends BaseObject {

  private String firstName;
  private String name;
  private String email;
  private String userName;
  private String passwordHash;

  private Locale locale;

  private Set userGroups;

  /** 
   * @hibernate.property length="100" not-null="false"
   */
  public String getFirstName() {
    return firstName;
  }

  /** 
   * @hibernate.property length="10" not-null="true"
   */
  public Locale getLocale() {
    return locale;
  }

  /** 
   * @hibernate.property length="100" not-null="true"
   */
  public String getName() {
    return name;
  }

  /** 
   * @hibernate.property length="40" not-null="false"
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** 
   * @hibernate.property unique="true" length="100" not-null="true"
   */
  public String getUserName() {
    return userName;
  }

  /** 
   * @hibernate.property length="255" not-null="true"
   */
  public String getEmail() {
    return email;
  }


  public void setFirstName(String string) {
    firstName = string;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setName(String string) {
    name = string;
  }

  public void setPasswordHash(String string) {
    passwordHash = string;
  }

  public void setUserName(String string) {
    userName = string;
  }

  public void setEmail(String string) {
    email = string;
  }


  /**      
   * @hibernate.set lazy="false" table="UserUserGroups"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-many-to-many class="ch.ess.blankrc.model.UserGroup" column="userGroupId"
   */
  public Set getUserGroups() {
    if (userGroups == null) {
      userGroups = new HashSet();
    }
    return this.userGroups;
  }

  public void setUserGroups(Set userGroups) {
    this.userGroups = userGroups;
  }

}