package ch.ess.addressbook.common;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.addressbook.resource.*;

public abstract class ListHibernateAction extends ListBaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    ActionForward forward = null;
    Session sess = null;
    try {
      sess = HibernateManager.open(request);
      forward = execute(mapping, form, request, response, sess);
      HibernateManager.commit(sess);
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(sess);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    }

    return forward;
  }

  public abstract ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Session hSession)
    throws Exception;

}
