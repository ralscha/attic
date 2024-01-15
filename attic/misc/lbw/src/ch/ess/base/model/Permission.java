package ch.ess.base.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Permission extends BaseObject {

  private String permission;
  private Set<UserGroupPermission> userGroupPermissions = new HashSet<UserGroupPermission>();

  @Column(nullable = false, length = 255)
  public String getPermission() {
    return permission;
  }

  public void setPermission(final String string) {
    permission = string;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "permission")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserGroupPermission> getUserGroupPermissions() {
    return userGroupPermissions;
  }

  public void setUserGroupPermissions(Set<UserGroupPermission> userGroupPermissions) {
    this.userGroupPermissions = userGroupPermissions;
  }

}