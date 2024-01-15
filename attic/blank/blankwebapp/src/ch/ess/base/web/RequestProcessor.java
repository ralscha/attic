package ch.ess.base.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.Constants;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:20 $
 */
public class RequestProcessor extends org.apache.struts.action.RequestProcessor {


  private ApplicationContext ctx = null;
  private LoginCookieUtil loginCookieUtil;

  @Override
  public void init(final ActionServlet actionServlet, final ModuleConfig module) throws ServletException {

    super.init(actionServlet, module);

    ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
    loginCookieUtil = (LoginCookieUtil)ctx.getBean("loginCookieUtil");
  }

  private void sendToLoginPage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

    String requestURI = request.getRequestURI();
    String queryString = request.getQueryString();

    if (queryString != null) {
      requestURI = requestURI + "?" + queryString;
    }

    String encodedURI = URLEncoder.encode(requestURI, "UTF-8");

    String toUrl = null;
    if (loginCookieUtil.lookForLoginCookie(request) != null) {
      toUrl = "/loginDirect.do?to=";
    } else {
      toUrl = "/loginTo.do?to=";
    }
    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + toUrl + encodedURI));
  }

  @Override
  protected boolean processRoles(final HttpServletRequest request, final HttpServletResponse response,
      final ActionMapping mapping) throws IOException {

    String roles = mapping.getRoles();
    String[] roleNames = mapping.getRoleNames();

    if (StringUtils.isBlank(roles)) {
      return true;
    }

    UserPrincipal userPrincipal = (UserPrincipal)request.getSession().getAttribute(Constants.USER_SESSION);
    
    if (userPrincipal != null) {
      for (String role : roleNames) {
        if ("all".equals(role)) {
          return true;
        }
      }

      if (hasRole(userPrincipal, roleNames)) {
        return true;
      }

    }

    sendToLoginPage(request, response);
    return false;

  }
  
  private boolean hasRole(UserPrincipal userPrincipal, String[] roles) {
    for (String role : roles) {
      if (userPrincipal.hasRole(role)) {
        return true;
      }
    }
    return false;
  }
  

  @Override
  protected Action processActionCreate(final HttpServletRequest request, final HttpServletResponse response,
      final ActionMapping mapping) throws IOException {

    try {
      String beanName = determineActionBeanName(mapping);
      return (Action)ctx.getBean(beanName, Action.class);
    } catch (NoSuchBeanDefinitionException ex) {
      //logger.info("NoSuchBeanDefinitionException: " + ex.getBeanName());
      return super.processActionCreate(request, response, mapping);
    }

  }

  public static String determineActionBeanName(final ActionMapping mapping) {
    String prefix = mapping.getModuleConfig().getPrefix();
    String path = mapping.getPath();
    String beanName = (prefix != null && prefix.length() > 0) ? prefix + path : path;
    return beanName;
  }

}