package ch.ess.cal.admin.web.resource;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;


public class ResourceEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages)
    throws Exception {

    ResourceForm resourceForm = (ResourceForm)form;

    Resource newResource = new Resource();
    
    
    Session sess = null;    
    try {
      sess = HibernateManager.open(request);      
      resourceForm.setResource(newResource, sess, getAdminUser(request), getResources(request));
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

    Long resourceId = new Long(request.getParameter(Constants.OBJECT_ID));
    Resource resource = null;

    try {
      resource = (Resource)sess.load(Resource.class, resourceId);
    } catch (ObjectNotFoundException onfe) {
      //do nothing
    }

    if (resource != null) {
      sess.delete(resource);
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

    ResourceForm resourceForm = (ResourceForm)form;
    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long resourceId = null;

    if (idStr != null) {
      resourceId = new Long(idStr);
    } else {
      //concurrent access
      resourceId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_CONCURRENT);
    }

    Resource resource = (Resource)sess.load(Resource.class, resourceId);        
    
    resourceForm.setResource(resource, sess, getAdminUser(request), getResources(request));

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

    ResourceForm resourceForm = (ResourceForm)form;

    if (isCancelled(request)) {
      resourceForm.setResource(null, getAdminUser(request));
      tx.commit();
      return mapping.findForward(Constants.FORWARD_CANCEL);
    }
    
    Resource resource = resourceForm.getResource(sess);    
    sess.saveOrUpdate(resource);

    try {
      tx.commit();
    } catch (StaleObjectStateException sose) {
      // concurrent access
      tx.rollback();
      resourceForm.setResource(null, getAdminUser(request));
      session.setAttribute(Constants.CONCURRENT_ID, resource.getResourceId());
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    return mapping.findForward(Constants.FORWARD_SAVEANDBACK);

  }

}