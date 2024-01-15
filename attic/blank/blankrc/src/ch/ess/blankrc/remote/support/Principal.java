package ch.ess.blankrc.remote.support;

import java.util.Set;

/**
 * @author sr
 */
public class Principal {
  private Set permissions;
  private String userName;

  public Set getPermissions() {
    return permissions;
  }

  public void setPermissions(Set permissions) {
    this.permissions = permissions;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public boolean hasPermission(String permission) {
    if (permission == null) {
      return true;
    }

    if (permissions == null) {
      return false;
    }

    return permissions.contains(permission);
  }
}