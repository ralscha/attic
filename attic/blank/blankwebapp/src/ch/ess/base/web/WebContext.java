package ch.ess.base.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:51:14 $
 */
public class WebContext {

  public static final ThreadLocal<WebContext> THREAD_VAR = new ThreadLocal<WebContext>();

  private HttpServletRequest request;
  private HttpServletResponse response;
  private ServletContext context;
  private ActionMapping mapping;
  private ActionForm form;

  private WebContext(HttpServletRequest request, HttpServletResponse response, ServletContext ctx, ActionMapping mapping,
      ActionForm form) {

    this.request = request;
    this.response = response;
    this.context = ctx;
    this.mapping = mapping;
    this.form = form;
  }

  public static void init(HttpServletRequest req, HttpServletResponse res, ServletContext ctx, ActionMapping map,
      ActionForm f) {

    WebContext wc = new WebContext(req, res, ctx, map, f);
    THREAD_VAR.set(wc);
  }

  public static WebContext currentContext() {
    return THREAD_VAR.get();
  }

  public static void destroy() {
    currentContext().clear();
    THREAD_VAR.set(null);
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpSession getSession() {
    return request.getSession();
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public ServletContext getServletContext() {
    return context;
  }

  public ActionForm getForm() {
    return form;
  }

  public ActionMapping getMapping() {
    return mapping;
  }
  
  public ActionForward getInputForward() {
    return mapping.getInputForward();
  }
  
  public ActionForward findForward(String forward) {
    return mapping.findForward(forward);
  }

  public UserPrincipal getUserPrincipal() {
    return (UserPrincipal) getSession().getAttribute(ch.ess.base.Constants.USER_SESSION);
  }

  private void clear() {
    request = null;
    response = null;
    context = null;
    form = null;
    mapping = null;
  }

}