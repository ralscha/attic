package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

import com.objectmatter.bsf.*;

public final class LogonAction extends BaseAction {

  private static Category CAT = Category.getInstance(LogonAction.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    HttpSession session = request.getSession();

    ActionErrors errors = new ActionErrors();

    LogonForm lform = (LogonForm) form;
    String username = lform.getUsername();
    String password = lform.getPassword();

    CAT.debug(lform);

    Database db = getDb(request);
    User user = (User) db.lookup(User.class, username);

    if (user != null) {
      if (user.getPass().trim().equals(password)) {
        request.removeAttribute(mapping.getAttribute());
        session.setAttribute(ch.sr.lottowin.Constants.USER_KEY, user);
        CAT.info("user " + username + " logged in");
      } else {
        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("logon.passwort.notmatch"));
        CAT.warn("logon password does not match");
      }
    } else {
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("logon.id.notfound"));
      CAT.warn("logon id not found");
    }

    lform.setPassword("");

    // Report any errors we have discovered back to the original form
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      return (new ActionForward(mapping.getInput()));
    }

    // Forward control to the specified success URI
    return (mapping.findForward("main"));

  }

}
