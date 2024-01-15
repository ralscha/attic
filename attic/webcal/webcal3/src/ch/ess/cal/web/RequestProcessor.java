package ch.ess.cal.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cc.framework.adapter.struts.FWRequestProcessor;
import com.cc.framework.security.Permission;
import com.cc.framework.security.PermissionException;
import com.cc.framework.security.Principal;
import com.cc.framework.security.SecurityUtil;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 */
public class RequestProcessor extends FWRequestProcessor {

  private final Log logger = LogFactory.getLog(getClass());

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

    if (GenericValidator.isBlankOrNull(roles)) {
      return true;
    }

    Principal principal = SecurityUtil.getPrincipal(request);
    if (principal != null) {
      for (int i = 0; i < roleNames.length; i++) {
        if ("all".equals(roleNames[i])) {
          return true;
        }
      }

      try {
        Permission permissions = Permission.parse(roles);
        if (permissions.isGranted(principal)) {
          return true;
        }
      } catch (PermissionException e) {
        logger.error("processRoles", e);
      }

    }

    sendToLoginPage(request, response);
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
