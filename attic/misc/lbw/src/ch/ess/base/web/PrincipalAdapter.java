package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.lang.StringUtils;

import com.cc.framework.security.SecurityUtil;

public class PrincipalAdapter implements PermissionsAdapter {

  private HttpServletRequest request;

  public PrincipalAdapter(HttpServletRequest request) {
    this.request = request;
  }

  /**
   * If the menu is allowed, this should return true.
   * 
   * @return whether or not the menu is allowed.
   */
  public boolean isAllowed(MenuComponent menu) {

    if (menu.getRoles() == null) {
      return true; // no roles define, allow everyone
    }

    HttpSession session = request.getSession();
    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(session);

    if (userPrincipal != null) {
      // Get the list of roles this menu allows
      String[] allowedRoles = StringUtils.split(menu.getRoles(), ",");
      
      for (String role : allowedRoles) {
        if (role.startsWith("$")) {
          role = role.substring(1);
        }
        if (userPrincipal.hasRight(role)) {
          return true;
        }
        
      }
    }

    return false;
  }

}