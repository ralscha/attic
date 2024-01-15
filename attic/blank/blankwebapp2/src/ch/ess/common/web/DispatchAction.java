package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.StaleObjectStateException;
import net.sf.hibernate.Transaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:14 $
 */
public abstract class DispatchAction extends BaseAction {

  private static final String EDIT_ACTION = "edit";
  private static final String ADD_ACTION = "add";
  private static final String STORE_ACTION = "store";
  private static final String STOREADD_ACTION = "storeadd";
  private static final String DELETE_ACTION = "delete";

  public ActionForward execute() throws Exception {

    Transaction tx = null;
    ActionForward forward = null;

    WebContext ctx = WebContext.currentContext();

    String parameter = ctx.getMapping().getParameter();
    HttpServletRequest request = ctx.getRequest();

    try {

      if (EDIT_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = edit();
        tx.commit();
      } else if (ADD_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = add();
        tx.commit();
      } else if (STORE_ACTION.equals(parameter)) {

        if (!GenericValidator.isBlankOrNull(request.getParameter(DELETE_ACTION))) {
          return findForward("delete");
        }

        tx = HibernateSession.currentSession().beginTransaction();
        ctx.setTransaction(tx);

        String storeAddAction = request.getParameter(STOREADD_ACTION);
        if (!GenericValidator.isBlankOrNull(storeAddAction)) {
          forward = storeAndAdd();
        } else {
          forward = store();
        }

        if (!tx.wasCommitted() && !tx.wasRolledBack()) {
          tx.commit();
        }

      } else if (DELETE_ACTION.equals(parameter)) {
        tx = HibernateSession.currentSession().beginTransaction();
        forward = delete();
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

  protected ActionForward storeAndAdd() throws Exception {

    ActionForward forward = store();

    WebContext ctx = WebContext.currentContext();

    //if there are no errors goto add
    HttpServletRequest request = ctx.getRequest();
    ActionErrors errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);

    if ((errors == null) || (errors.isEmpty())) {
      ctx.getForm().reset(ctx.getMapping(), request);
      forward = add();
    }

    return forward;
  }

  protected abstract ActionForward store() throws Exception;

  protected abstract ActionForward delete() throws Exception;

  protected abstract ActionForward edit() throws Exception;

  protected abstract ActionForward add() throws Exception;

  protected Long getId() throws NumberFormatException {
    HttpServletRequest request = WebContext.currentContext().getRequest();

    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long userId = null;

    if (idStr != null) {
      userId = new Long(idStr);
    } else {
      //concurrent access
      HttpSession session = request.getSession();
      userId = (Long) session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
    }
    return userId;
  }

  protected ActionForward save(Persistent persist) throws HibernateException {
    HibernateSession.currentSession().saveOrUpdate(persist);

    WebContext ctx = WebContext.currentContext();
    Transaction tx = ctx.getTransaction();
    try {
      tx.commit();
      addOneMessageSession(Constants.ACTION_MESSAGE_UPDATE_OK);
      return null;
    } catch (StaleObjectStateException sose) {
      // concurrent access
      HibernateSession.rollback(tx);

      ctx.getSession().setAttribute(Constants.CONCURRENT_ID, persist.getId());
      addOneErrorRequest(Constants.ACTION_MESSAGE_CONCURRENT);
      return findForward(Constants.FORWARD_RELOAD);
    }

  }

}