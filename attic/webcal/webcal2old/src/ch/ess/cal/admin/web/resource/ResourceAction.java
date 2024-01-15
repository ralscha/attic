package ch.ess.cal.admin.web.resource;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;

public class ResourceAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session hSession)
    throws Exception {
    
    ResourceListForm rListForm = (ResourceListForm)form;
    rListForm.init(hSession, getAdminUser(request), getResources(request));
    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

}
