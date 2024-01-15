

package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import lottowin.form.*;
import lottowin.resource.*;
import lottowin.db.*;
import com.objectmatter.bsf.*;
import org.log4j.*;
import ch.ess.util.pool.*;

public final class LogonAction extends LottowinBaseAction {

  public LogonAction() {
    super();
    shouldHaveUser = false;
  }

  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    ActionErrors errors = new ActionErrors();

    LogonForm lform = (LogonForm) form;
    String username = lform.getUsername();
    String password = lform.getPassword();
    
    CAT.debug(lform);

    db = PoolManager.requestDatabase();

    User user = (User)db.lookup(User.class, username);

    if (user != null) {
      if (user.getPass().trim().equals(password)) {
        request.removeAttribute(mapping.getAttribute());
        session.setAttribute(lottowin.Constants.USER_KEY, user);
        session.setAttribute(lottowin.Constants.DB_KEY, db);
        session.setAttribute(lottowin.Constants.SESSION_LISTENER_KEY, 
              new DatabaseSessionBinding(db));

        CAT.info("user "+ username + " logged in");
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
    if (!errors.empty()) {
      saveErrors(request, errors);
      return (new ActionForward(mapping.getInput()));
    }

    // Forward control to the specified success URI
    return (mapping.findForward("main"));

  }


}
