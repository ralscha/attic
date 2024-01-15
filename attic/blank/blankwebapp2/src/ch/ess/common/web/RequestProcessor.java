package ch.ess.common.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;

import ch.ess.blank.web.WebUtils;

public class RequestProcessor extends TilesRequestProcessor {

  private void sendToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String requestURI = request.getRequestURI();
    String queryString = request.getQueryString();

    if (queryString != null) {
      requestURI = requestURI + "?" + queryString;
    }

    String encodedURI = URLEncoder.encode(requestURI, "UTF-8");

    String toUrl = null;
    if (WebUtils.lookForLoginCookie(request) != null) {
      toUrl = "/loginCookie.do?to=";
    } else {
      toUrl = "/loginTo.do?to=";
    }
    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + toUrl + encodedURI));
  }

  protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping)
      throws IOException, ServletException {

    HttpSession session = request.getSession();
    String roles[] = mapping.getRoleNames();

    if ((roles == null) || (roles.length < 1)) {
      return true;
    }

    UserSession theUser = (UserSession) session.getAttribute(ch.ess.common.Constants.USER_SESSION);

    if (theUser != null) {
      for (int i = 0; i < roles.length; i++) {

        String permission = roles[i];

        if ("all".equals(permission)) {
          return true;
        }

        if (theUser.hasPermission(permission)) {
          return true;
        }
      }
    }

    sendToLoginPage(request, response);
    return false;

  }

}