package ch.ess.base.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.web.login.LoginCookieUtil;

import com.cc.framework.adapter.struts.FWRequestProcessor;
import com.cc.framework.security.Permission;
import com.cc.framework.security.PermissionException;
import com.cc.framework.security.Principal;
import com.cc.framework.security.SecurityUtil;

public class RequestProcessor extends FWRequestProcessor {

  private final Log logger = LogFactory.getLog(getClass());

  private ApplicationContext actionContext = null;
  private LoginCookieUtil loginCookieUtil;

  @Override
  public void init(final ActionServlet actionServlet, final ModuleConfig module) throws ServletException {

    super.init(actionServlet, module);

    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet
        .getServletContext());
    loginCookieUtil = (LoginCookieUtil)ctx.getBean("loginCookieUtil");

    actionContext = (ApplicationContext)actionServlet.getServletContext().getAttribute(
        WebConstants.WEB_APPLICATION_CONTEXT_ATTRIBUTE);

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

    Principal principal = SecurityUtil.getPrincipal(request);
    if (principal != null) {
      for (String element : roleNames) {
        if ("all".equals(element)) {
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

    String beanName = determineActionBeanName(mapping);
    if (actionContext.containsBean(beanName)) {
      return (Action)actionContext.getBean(beanName, Action.class);
    } 
    return super.processActionCreate(request, response, mapping);    

  }

  public static String determineActionBeanName(final ActionMapping mapping) {
    String prefix = mapping.getModuleConfig().getPrefix();
    String path = mapping.getPath();
    return (prefix != null && prefix.length() > 0) ? prefix + path : path;
  }

}