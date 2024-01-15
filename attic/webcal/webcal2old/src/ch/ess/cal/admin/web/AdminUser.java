package ch.ess.cal.admin.web;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import ch.ess.cal.db.Permission;
import ch.ess.cal.db.User;
import ch.ess.cal.db.UserGroup;


public class AdminUser {

  private Long userId;
  private Set permissions;  
  private Locale locale;

  private AdminUser(User user) {
    userId = user.getUserId();
    setPermissions(user);    
  }


  public static AdminUser createUser(User user) {

    if (user == null) {
      return null;
    }

    AdminUser newUser = new AdminUser(user);    
    newUser.locale = user.getLocale();
  
    return newUser;
  }

  private void setPermissions(User user) {
    
    UserGroup group = user.getUserGroup();
    if (group != null) {
      Set perms = group.getPermissions();
      if (perms != null) {
        permissions = new HashSet();
        for (Iterator iter = perms.iterator(); iter.hasNext();) {
          Permission perm = (Permission)iter.next();
          permissions.add(perm.getName());
        }
      }
    }
    
  }

  public Long getUserId() {
    return userId;
  }

  public Locale getLocale() {
    return locale;
  }

 
  public boolean hasPermission(String permission) {

    if (permissions != null) {
      return permissions.contains(permission);
    }

    return false;
  }
  
  



}