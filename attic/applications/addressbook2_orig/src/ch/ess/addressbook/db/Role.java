package ch.ess.addressbook.db;

import java.util.Set;

import ch.ess.common.db.Persistent;

/** A business entity class representing a Role
  * 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * @hibernate.class  table="abRole" proxy="ch.ess.addressbook.db.Role"
  */

public class Role extends Persistent {

  private String name;
  private String description;
  private Set userRoles;

  /** 
  * @hibernate.property length="255" not-null="true"
  */
  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  /**      
     * @hibernate.set lazy="true" table="abUserRole"     
     * @hibernate.collection-key  column="roleId"     
     * @hibernate.collection-one-to-many  class="ch.ess.addressbook.db.UserRole"
     */
  public Set getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set set) {
    userRoles = set;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getDescription() {
    return description;
  }

  public void setDescription(String string) {
    description = string;
  }

}