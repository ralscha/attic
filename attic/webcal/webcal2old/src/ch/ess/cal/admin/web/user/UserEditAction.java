package ch.ess.cal.admin.web.user;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;

public class UserEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages)
    throws Exception {

    UserForm userForm = (UserForm)form;

    User newUser = new User();
    
    Session sess = null;    
    try {
      sess = HibernateManager.open(request);      
      userForm.setUser(newUser, sess, getAdminUser(request), getResources(request));
    } finally {
      HibernateManager.finallyHandling(sess);
    }    

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    Long userId = new Long(request.getParameter(Constants.OBJECT_ID));
    User user = null;

    try {
      user = (User)sess.load(User.class, userId);
    } catch (ObjectNotFoundException onfe) {
      //do nothing
    }

    if (user != null) {
      sess.delete(user);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_DELETE_OK);
    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    UserForm userForm = (UserForm)form;
    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long userId = null;

    if (idStr != null) {
      userId = new Long(idStr);
    } else {
      //concurrent access
      userId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_CONCURRENT);
    }

    User user = (User)sess.load(User.class, userId);
    userForm.setUser(user, sess, getAdminUser(request), getResources(request));

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess,
    Transaction tx)
    throws Exception {

    UserForm userForm = (UserForm)form;

    if (isCancelled(request)) {
      userForm.setUser(null, null);
      tx.commit();
      return mapping.findForward(Constants.FORWARD_CANCEL);
    }
    
    User user = userForm.getUser(sess);    
    sess.saveOrUpdate(user);

    try {
      tx.commit();
    } catch (StaleObjectStateException sose) {
      // concurrent access
      tx.rollback();
      userForm.setUser(null, getAdminUser(request));
      session.setAttribute(Constants.CONCURRENT_ID, user.getUserId());
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    return mapping.findForward(Constants.FORWARD_SAVEANDBACK);

  }

}