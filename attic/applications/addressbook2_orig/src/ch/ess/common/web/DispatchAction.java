package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.StaleObjectStateException;
import net.sf.hibernate.Transaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public abstract class DispatchAction extends BaseAction {

  private static final String REQUEST_PARAMETER = "action";

  private static final String EDIT_ACTION = "edit";
  private static final String ADD_ACTION = "add";
  private static final String STORE_ACTION = "store";
  private static final String STOREADD_ACTION = "storeadd";
  private static final String DELETE_ACTION = "delete";

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    Transaction tx = null;
    ActionForward forward = null;

    String parameter = mapping.getParameter();
    if (parameter == null) {
      parameter = request.getParameter(REQUEST_PARAMETER);
    }

    try {

      if (EDIT_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = edit(mapping, form, request, response);
        tx.commit();
      } else if (ADD_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = add(mapping, form, request, response);
        tx.commit();
      } else if (STORE_ACTION.equals(parameter)) {

        if (!GenericValidator.isBlankOrNull(request.getParameter(DELETE_ACTION))) {
          return mapping.findForward("delete");
        }

        tx = HibernateSession.currentSession().beginTransaction();

        String storeAddAction = request.getParameter(STOREADD_ACTION);
        if (!GenericValidator.isBlankOrNull(storeAddAction)) {
          forward = storeAndAdd(mapping, form, request, response, tx);
        } else {
          forward = store(mapping, form, request, response, tx);
        }

        if (!tx.wasCommitted() && !tx.wasRolledBack()) {
          tx.commit();
        }

      } else if (DELETE_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = delete(mapping, form, request, response);
        tx.commit();
      }

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

    return forward;
  }

  protected ActionForward storeAndAdd(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception {

    ActionForward forward = store(mapping, form, request, response, tx);

    //if there are no errors goto add    
    ActionErrors errors = (ActionErrors)request.getAttribute(Globals.ERROR_KEY);

    if ((errors == null) || (errors.isEmpty())) {
      form.reset(mapping, request);
      forward = add(mapping, form, request, response);
    }

    return forward;
  }

  protected abstract ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception;

  protected abstract ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception;

  protected abstract ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception;

  protected abstract ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception;

  protected Long getId(HttpServletRequest request) throws NumberFormatException {
    String idStr = request.getParameter(Constants.OBJECT_ID);
    
    Long userId = null;
    
    if (idStr != null) {
      userId = new Long(idStr);
    } else {
      //concurrent access
      HttpSession session = request.getSession();
      userId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
    }
    return userId;
  }

  protected ActionForward save(ActionMapping mapping, HttpServletRequest request, Transaction tx, Persistent persist) throws HibernateException {
    HibernateSession.currentSession().saveOrUpdate(persist);
    
    try {
      tx.commit();
      addOneMessage(request, Constants.ACTION_MESSAGE_UPDATE_OK);
      
      return null;
    } catch (StaleObjectStateException sose) {
      // concurrent access
      HibernateSession.rollback(tx);
    
      request.getSession().setAttribute(Constants.CONCURRENT_ID, persist.getId());
      addOneError(request, Constants.ACTION_MESSAGE_CONCURRENT);
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }
    
  }

}