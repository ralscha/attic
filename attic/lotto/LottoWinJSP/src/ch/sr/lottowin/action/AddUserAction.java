package ch.sr.lottowin.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.sr.lottowin.db.*;
import ch.sr.lottowin.form.*;

import com.objectmatter.bsf.*;

public final class AddUserAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    if (!isTokenValid(request)) {
      LOG.debug("command token not valid");
      request.removeAttribute(mapping.getAttribute());
      return (mapping.findForward("logon"));
    }
        
    // Validate the request parameters specified by the user
    ActionErrors errors = new ActionErrors();

    UserForm userForm = (UserForm) form;
    LOG.debug(userForm);

    Database db = getDb(request);
    User user = (User) db.lookup(User.class, userForm.getUserID());
    if (user == null) {
      User newUser = (User)db.create(User.class);
      newUser.setUserid(userForm.getUserID());
      newUser.setName(userForm.getLastName());
      newUser.setFirstname(userForm.getFirstName());
      newUser.setPass(userForm.getPassword());
      newUser.setLanguage("deCH");
      newUser.setEmail(userForm.getEmail());

      LOG.debug(newUser);

      try {
        db.insert(newUser);
      } catch (Exception e) {
        LOG.error("inser(newUser)", e);
        throw new ServletException(e);
      }

    } else {
      LOG.warn("User " + userForm.getUserID() + " already exists");
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("user.user.exists"));
    }

    // Report any errors we have discovered back to the original form
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      return (new ActionForward(mapping.getInput()));
    }

        
    resetToken(request);
    // Forward control to the specified success URI
    return (mapping.findForward("success"));

  }

}
