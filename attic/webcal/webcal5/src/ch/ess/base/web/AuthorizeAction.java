package ch.ess.base.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.chain.commands.AbstractAuthorizeAction;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.util.MessageResources;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.web.login.LoginCookieUtil;

import com.cc.framework.security.Permission;
import com.cc.framework.security.PermissionException;
import com.cc.framework.security.Principal;
import com.cc.framework.security.SecurityUtil;

public class AuthorizeAction extends AbstractAuthorizeAction {

  @Override
  public boolean execute(ActionContext actionCtx) throws Exception {
    // Retrieve ActionConfig
    ActionConfig actionConfig = actionCtx.getActionConfig();

    // Is this action protected by role requirements?
    if (!isAuthorizationRequired(actionConfig)) {
      return (false);
    }

    boolean throwEx;

    try {
      throwEx = !(isAuthorized(actionCtx, actionConfig.getRoleNames(), actionConfig));
    } catch (Exception ex) {
      throwEx = true;
      LogFactory.getLog(AuthorizeAction.class).error("Unable to complete authorization process", ex);
    }

    if (throwEx) {
      return true;
    } 
    return (false);
   
  }

  @Override
  protected boolean isAuthorized(ActionContext context, String[] roles, ActionConfig mapping) throws Exception {

    ServletActionContext servletActionContext = (ServletActionContext)context;
    HttpServletRequest request = servletActionContext.getRequest();
    HttpServletResponse response = servletActionContext.getResponse();

    String roleString = mapping.getRoles();

    if (StringUtils.isBlank(roleString)) {
      return true;
    }

    Principal principal = SecurityUtil.getPrincipal(request);
    if (principal != null) {
      for (String element : roles) {
        if ("all".equals(element)) {
          return true;
        }
      }

      try {
        Permission permissions = Permission.parse(roleString);
        if (permissions.isGranted(principal)) {
          return true;
        }
      } catch (PermissionException e) {
        LogFactory.getLog(getClass()).error("processRoles", e);
      }

    }

    sendToLoginPage(request, response);
    return false;

  }

  @Override
  protected String getErrorMessage(ActionContext context, ActionConfig actionConfig) {
    ServletActionContext servletActionContext = (ServletActionContext)context;

    // Retrieve internal message resources
    ActionServlet servlet = servletActionContext.getActionServlet();
    MessageResources resources = servlet.getInternal();

    return resources.getMessage("notAuthorized", actionConfig.getPath());
  }

  private void sendToLoginPage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

    String requestURI = request.getRequestURI();
    String queryString = request.getQueryString();

    if (queryString != null) {
      requestURI = requestURI + "?" + queryString;
    }

    String encodedURI = URLEncoder.encode(requestURI, "UTF-8");

    String toUrl = null;

    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession()
        .getServletContext());
    LoginCookieUtil loginCookieUtil = (LoginCookieUtil)ctx.getBean("loginCookieUtil");

    if (loginCookieUtil.lookForLoginCookie(request) != null) {
      toUrl = "/loginDirect.do?to=";
    } else {
      toUrl = "/loginTo.do?to=";
    }
    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + toUrl + encodedURI));
  }
}
