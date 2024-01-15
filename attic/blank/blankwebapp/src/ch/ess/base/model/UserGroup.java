package ch.ess.base.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

/** A business entity class representing a UserGroup
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */

@Entity
public class UserGroup extends BaseObject {

  private String groupName;
  private Set<UserUserGroup> userUserGroups = new HashSet<UserUserGroup>();
  private Set<UserGroupPermission> userGroupPermissions = new HashSet<UserGroupPermission>();

  @Column(nullable = false, length = 255)
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(final String groupName) {
    this.groupName = groupName;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroup")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserUserGroup> getUserUserGroups() {
    return userUserGroups;
  }

  public void setUserUserGroups(Set<UserUserGroup> userUserGroups) {
    this.userUserGroups = userUserGroups;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroup")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserGroupPermission> getUserGroupPermissions() {
    return userGroupPermissions;
  }

  public void setUserGroupPermissions(Set<UserGroupPermission> userGroupPermissions) {
    this.userGroupPermissions = userGroupPermissions;
  }
  
  



}