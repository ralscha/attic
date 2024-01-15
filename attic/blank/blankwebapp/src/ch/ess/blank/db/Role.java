package ch.ess.blank.db;

import java.util.Set;

import ch.ess.common.db.Persistent;

/** A business entity class representing a Role
 * 
 * @author  Ralph Schaer
 * @version $Revision: 1.6 $ $Date: 2004/05/22 12:24:36 $ 
 * @hibernate.class  table="blankRole" lazy="true"
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
   * @hibernate.set lazy="true"
   * @hibernate.collection-key  column="roleId"     
   * @hibernate.collection-one-to-many  class="ch.ess.blank.db.UserRole"
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