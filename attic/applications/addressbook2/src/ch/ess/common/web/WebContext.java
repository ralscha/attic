package ch.ess.common.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:27 $ 
  */
public class WebContext {

  public static final ThreadLocal THREAD_VAR = new ThreadLocal();

  private HttpServletRequest request;
  private HttpServletResponse response;
  private ServletContext context;
  private ActionMapping mapping;
  private ActionForm form;
  private Transaction transaction;

  private WebContext(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx,
    ActionMapping map,
    ActionForm f) {
    
    request = req;
    response = res;
    context = ctx;
    mapping = map;
    form = f;    
  }

  public static void init(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx,
    ActionMapping map,
    ActionForm f) {

    WebContext wc = new WebContext(req, res, ctx, map, f);
    THREAD_VAR.set(wc);    
  }

  
  public static WebContext currentContext() {
    return (WebContext)THREAD_VAR.get();
  }
  
  public static void destroy() {
    currentContext().clear();
    THREAD_VAR.set(null);
  }
  
  
  
  public void setTransaction(Transaction tx) {
    transaction = tx;
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

  public Transaction getTransaction() {
    return transaction;
  }

  private void clear() {
    request = null;
    response = null;
    context = null;
    form = null;
    mapping = null;
    transaction = null;
  }

}
