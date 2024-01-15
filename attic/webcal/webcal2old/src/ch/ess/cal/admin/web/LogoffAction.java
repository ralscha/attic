package ch.ess.cal.admin.web;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;

public class LogoffAction extends Action {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    HttpSession session = request.getSession();
    
    session.removeAttribute(Constants.ADMIN_USER_KEY);
    session.removeAttribute(Constants.ADMIN_USER_SESSION);
    session.invalidate();

    
    return (mapping.findForward(Constants.FORWARD_SUCCESS));

  }

}
