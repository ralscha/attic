package ch.ess.cal.common;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.resource.*;

public abstract class HibernateAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws Exception {

    ActionForward forward = null;
    Session sess = null;
    Transaction tx = null;
    try {
      sess = HibernateManager.open(request);
      tx = sess.beginTransaction(); 

      ActionMessages messages = new ActionMessages();
      
      forward = execute(mapping, form, request, response, messages, sess);
      
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      
      tx.commit();
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    }

    return forward;
  }

  public abstract ActionForward execute(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response,
                                        ActionMessages messages,
                                        Session sess) throws Exception;

}
