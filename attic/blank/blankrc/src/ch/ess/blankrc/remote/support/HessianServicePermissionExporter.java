
package ch.ess.blankrc.remote.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author sr
 */
public class HessianServicePermissionExporter extends HessianServiceExporter {

  private String permission;
  private PrincipalRegistry principalRegistry;

  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (permission != null) {
      String ticket = request.getParameter("ticket");
      if (ticket != null) {

        Principal principal = principalRegistry.getPrincipal(ticket);
        if ((principal != null) && (principal.hasPermission(permission))) {
          return super.handleRequest(request, response);
        }
      }

      response.sendError(401);
      return null;
    }

    return super.handleRequest(request, response);
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public void setPrincipalRegistry(PrincipalRegistry principalDb) {
    this.principalRegistry = principalDb;
  }
}

