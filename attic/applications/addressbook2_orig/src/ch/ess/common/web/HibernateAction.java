package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.common.db.HibernateSession;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public abstract class HibernateAction extends BaseAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ActionForward forward = null;
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      forward = doAction(mapping, form, request, response);

      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

    return forward;
  }

  public abstract ActionForward doAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception;

}
