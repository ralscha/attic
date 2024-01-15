package ch.ess.blankrc.model;

import java.util.HashSet;
import java.util.Set;

/** A business entity class representing a Permission
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 *    
 * @hibernate.class  table="Permission" lazy="true"
 */

public class Permission extends BaseObject {

  private String permission;
  private Set userGroup;

  /** 
   * @hibernate.property length="255" not-null="true"
   */
  public String getPermission() {
    return permission;
  }

  public void setPermission(String string) {
    permission = string;
  }

  /**      
   * @hibernate.set lazy="true" table="UserGroupPermissions"
   * @hibernate.collection-key column="permissionId"     
   * @hibernate.collection-many-to-many class="ch.ess.blankrc.model.UserGroup" column="userGroupId"
   */
  public Set getUserGroups() {
    if (userGroup == null) {
      userGroup = new HashSet();
    }
    return this.userGroup;
  }

  public void setUserGroups(Set userGroup) {
    this.userGroup = userGroup;
  }

}