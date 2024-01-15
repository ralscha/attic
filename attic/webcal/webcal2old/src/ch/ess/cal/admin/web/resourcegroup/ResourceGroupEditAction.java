package ch.ess.cal.admin.web.resourcegroup;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;

public class ResourceGroupEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages)
    throws Exception {

    ResourceGroupForm resourceGroupForm = (ResourceGroupForm)form;

    ResourceGroup newResourceGroup = new ResourceGroup();
    resourceGroupForm.setResourceGroup(newResourceGroup);

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

    Long resourceGroupId = new Long(request.getParameter(Constants.OBJECT_ID));
    ResourceGroup resourceGroup = null;

    try {
      resourceGroup = (ResourceGroup)sess.load(ResourceGroup.class, resourceGroupId);
    } catch (ObjectNotFoundException onfe) {
      //do nothing
    }

    if (resourceGroup != null) {
      sess.delete(resourceGroup);
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

    ResourceGroupForm resourceGroupForm = (ResourceGroupForm)form;
    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long resourceGroupId = null;

    if (idStr != null) {
      resourceGroupId = new Long(idStr);
    } else {
      //concurrent access
      resourceGroupId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_CONCURRENT);
    }

    ResourceGroup resourceGroup = (ResourceGroup)sess.load(ResourceGroup.class, resourceGroupId);
    Hibernate.initialize(resourceGroup);
    resourceGroupForm.setResourceGroup(resourceGroup);

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

    ResourceGroupForm resourceGroupForm = (ResourceGroupForm)form;

    if (isCancelled(request)) {
      resourceGroupForm.setResourceGroup(null);
      tx.commit();
      return mapping.findForward(Constants.FORWARD_CANCEL);
    }
    
    ResourceGroup resourceGroup = resourceGroupForm.getResourceGroup(sess);    
    sess.saveOrUpdate(resourceGroup);

    try {
      tx.commit();
    } catch (StaleObjectStateException sose) {
      // concurrent access
      tx.rollback();
      resourceGroupForm.setResourceGroup(null);
      session.setAttribute(Constants.CONCURRENT_ID, resourceGroup.getResourceGroupId());
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    return mapping.findForward(Constants.FORWARD_SAVEANDBACK);

  }

}