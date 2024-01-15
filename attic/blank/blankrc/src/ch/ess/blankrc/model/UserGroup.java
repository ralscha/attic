package ch.ess.blankrc.model;

import java.util.HashSet;
import java.util.Set;

/** A business entity class representing a UserGroup
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 *    
 * @hibernate.class  table="UserGroup" lazy="true"
 */

public class UserGroup extends BaseObject {

  private String groupName;
  private Set permissions;
  private Set users;

  /** 
   * @hibernate.property length="255" not-null="true"
   */
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /**      
   * @hibernate.set lazy="true" table="UserGroupPermissions"
   * @hibernate.collection-key column="userGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.blankrc.model.Permission" column="permissionId"
   */
  public Set getPermissions() {
    if (permissions == null) {
      permissions = new HashSet();
    }
    return this.permissions;
  }

  public void setPermissions(Set permissions) {
    this.permissions = permissions;
  }

  /**      
   * @hibernate.set lazy="true" table="UserUserGroups"
   * @hibernate.collection-key column="userGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.blankrc.model.User" column="userId"
   */
  public Set getUsers() {
    if (users == null) {
      users = new HashSet();
    }
    return this.users;
  }

  public void setUsers(Set users) {
    this.users = users;
  }

}