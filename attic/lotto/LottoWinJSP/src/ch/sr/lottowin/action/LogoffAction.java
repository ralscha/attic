package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;

public final class LogoffAction extends BaseAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    HttpSession session = request.getSession();

    User user = (User)session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
    if (user != null) {
      LOG.info("user " + user.getUserid() + " logged off");
    }

    //invalidate session
    session.invalidate();

    // Forward control to the specified success URI
    return (mapping.findForward("success"));

  }

}
