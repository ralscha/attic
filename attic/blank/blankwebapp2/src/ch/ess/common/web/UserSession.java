package ch.ess.common.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import ch.ess.blank.db.Permission;
import ch.ess.blank.db.User;
import ch.ess.blank.db.UserGroup;

/**
 * 
 * @version $Revision: 1.4 $ $Date: 2004/05/22 16:51:14 $
 * @author $Author: sr $
 */
public class UserSession {

  private Set permissions;
  private Long userId;
  private boolean cookieLogin;

  public static UserSession getUserSession(User user, boolean cookieLogin) {

    Set permissionsSet = new HashSet();

    Set userGroups = user.getUserGroups();
    for (Iterator it = userGroups.iterator(); it.hasNext();) {
      UserGroup ug = (UserGroup) it.next();
      Set permissions = ug.getPermissions();
      for (Iterator it2 = permissions.iterator(); it2.hasNext();) {
        Permission permission = (Permission) it2.next();
        permissionsSet.add(permission.getPermission());
      }
    }

    UserSession userSession = new UserSession(user.getId(), permissionsSet, cookieLogin);
    return userSession;
  }

  private UserSession(Long userId, Set permissions, boolean cookieLogin) {
    this.userId = userId;
    this.permissions = permissions;
    this.cookieLogin = cookieLogin;
  }

  public boolean hasPermission(String permission) {

    if (permissions != null) {

      if ((permission != null) && (permission.indexOf(',') != -1)) {
        StringTokenizer st = new StringTokenizer(permission, ",");
        while (st.hasMoreTokens()) {
          if (permissions.contains(st.nextToken())) {
            return true;
          }
        }

      } else {
        return permissions.contains(permission);
      }
    }

    return false;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean isCookieLogin() {
    return cookieLogin;
  }

}