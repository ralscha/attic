package ch.ess.cal.common;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.*;
import org.apache.struts.action.*;

import ch.ess.cal.resource.*;




/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.3 $ $Date: 2003/04/28 06:20:05 $
 * @author $Author: sr $
 */
public abstract class DispatchAction extends BaseAction {

  private final static String REQUEST_PARAMETER = "action";


  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
                               HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    Session sess = null;
    Transaction tx = null;
    ActionForward forward = null;

    String parameter = mapping.getParameter();
    if (parameter == null) {
      parameter = request.getParameter(REQUEST_PARAMETER);    
    }
    
    try {
      
      ActionMessages messages = new ActionMessages();
      
      if ("edit".equals(parameter)) {
        sess = HibernateManager.open(request);          
        tx = sess.beginTransaction(); 
        forward = edit(mapping, form, request, session, messages, sess);
        tx.commit();
      } else if ("add".equals(parameter)) {
        forward = add(mapping, form, request, session, messages);       
      } else if ("store".equals(parameter)) {
        sess = HibernateManager.open(request);
        tx = sess.beginTransaction();
        String storeAddAction = request.getParameter("storeadd");
        if ((storeAddAction != null) && (!"".equals(storeAddAction.trim()))) {
          forward = storeAndAdd(mapping, form, request, session, messages, sess, tx);
        } else {
          forward = store(mapping, form, request, session, messages, sess, tx);
        }
        if (!tx.wasCommitted()) {
          tx.commit();
        }
      } else if ("delete".equals(parameter)) {
        sess = HibernateManager.open(request);
        tx = sess.beginTransaction(); 
        forward = delete(mapping, form, request, session, messages, sess);
        tx.commit();          
      }
            
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }      
      
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    }       
      

    return forward;
  }


  protected ActionForward storeAndAdd(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, HttpSession session,
                                      ActionMessages messages,
                                      Session sess, Transaction tx)
                               throws Exception {

    ActionForward forward = store(mapping, form, request, session, messages, sess, tx);

    //if there are no errors goto add    
    ActionErrors errors = (ActionErrors)request.getAttribute(Globals.ERROR_KEY);

    if ((errors == null) || (errors.isEmpty())) {
      form.reset(mapping, request);
      forward = add(mapping, form, request, session, messages);
    }

    return forward;
  }


  protected abstract ActionForward store(ActionMapping mapping, ActionForm form, 
                                         HttpServletRequest request, HttpSession session,
                                         ActionMessages messages,
                                         Session sess, Transaction tx)
                                  throws Exception;


  protected abstract ActionForward delete(ActionMapping mapping, ActionForm form, 
                                          HttpServletRequest request, HttpSession session,
                                          ActionMessages messages,  
                                          Session sess)
                                   throws Exception;


  protected abstract ActionForward edit(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, HttpSession session,
                                        ActionMessages messages,
                                        Session sess)
                                 throws Exception;


  protected abstract ActionForward add(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, HttpSession session,
                                       ActionMessages messages)
                                throws Exception;




  

  


  
}