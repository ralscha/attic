package ch.ess.base.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** A business entity class representing a link between Permission and UserGroup
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */

@Entity
public class UserGroupPermission extends BaseObject {

  private UserGroup userGroup;
  private Permission permission;

  @ManyToOne
  @JoinColumn(name = "permissionId", nullable = false)
  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  @ManyToOne
  @JoinColumn(name = "userGroupId", nullable = false)
  public UserGroup getUserGroup() {
    return userGroup;
  }

  public void setUserGroup(UserGroup userGroup) {
    this.userGroup = userGroup;
  }
  
  



}