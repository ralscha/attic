package ch.ess.test.state;

import java.util.*;

public class User {
  private Set permissions;
  
  public Set getPermissions() {
    return permissions;
  }

  public void addPermissions(String permission) {
    if (permissions != null) {
      permissions = new HashSet();
    }
    permissions.add(permission);
  }
  
  public boolean hasPermission(String permission) {
    return ((permissions != null) && (permissions.contains(permission))); 
  }

}
