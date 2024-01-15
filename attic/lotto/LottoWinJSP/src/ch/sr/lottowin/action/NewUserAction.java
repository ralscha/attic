package ch.sr.lottowin.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;

public class NewUserAction extends Action {



  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
          
    saveToken(request);
    return (mapping.findForward("success"));
          
  }

}
