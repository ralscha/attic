package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;

public final class GoCheckAction extends BaseAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    HttpSession session = request.getSession();

    User user = (User) session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
    if (user == null) {
      LOG.warn("user object not in session");
      return (mapping.findForward("logon"));
    }

    LOG.debug("go check action");

    // Forward control to the specified success URI
    return (mapping.findForward("success"));

  }

}
