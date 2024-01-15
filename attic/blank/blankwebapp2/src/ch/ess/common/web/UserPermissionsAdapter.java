package ch.ess.common.web;

/**
 * @author sr
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.lang.StringUtils;

/**
 * This class used container-managed security to check access to menus. The
 * roles are set in menu-config.xml. tst
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible </a>
 */
public class UserPermissionsAdapter implements PermissionsAdapter {

  private HttpServletRequest request;

  public UserPermissionsAdapter(HttpServletRequest request) {
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
    UserSession theUser = (UserSession) session.getAttribute(ch.ess.common.Constants.USER_SESSION);

    // Get the list of roles this menu allows
    String[] allowedRoles = StringUtils.split(menu.getRoles(), ",");
    for (int i = 0; i < allowedRoles.length; i++) {
      if (theUser.hasPermission(allowedRoles[i])) {
        return true;
      }
    }

    return false;
  }

}