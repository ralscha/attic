

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
import ch.ess.taglib.misc.*;
import ch.ess.util.pool.*;
import org.log4j.*;

public final class AddUserAction extends Action {

  private static Category CAT = Category.getInstance(AddUserAction.class.getName());

  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {

    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {


      if (!CommandToken.isValid(request)) {
        CAT.debug("command token not valid");
        request.removeAttribute(mapping.getAttribute());
        return (mapping.findForward("logon"));
      }

      // Validate the request parameters specified by the user
      ActionErrors errors = new ActionErrors();

      UserForm userForm = (UserForm)form;
      CAT.debug(userForm);

      Database db = null;

      try {
        db = PoolManager.requestDatabase();

        User user = (User)db.lookup(User.class, userForm.getUserID());
        if (user == null) {
          User newUser = (User)db.create(User.class); 
          newUser.setUserid(userForm.getUserID());
          newUser.setName(userForm.getLastName());
          newUser.setFirstname(userForm.getFirstName());
          newUser.setPass(userForm.getPassword());
          newUser.setLanguage("deCH");
          newUser.setEmail(userForm.getEmail());
        
          CAT.debug(newUser);      
        
          try {
            db.insert(newUser);
          } catch (Exception e) {
            CAT.error("inser(newUser)", e);
            throw new ServletException(e);
          }

        } else {
          CAT.warn("User " + userForm.getUserID() + " already exists");
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("user.user.exists"));
        }
      } finally {
        PoolManager.returnDatabase(db);
      }
     
    
      // Report any errors we have discovered back to the original form
      if (!errors.empty()) {
        saveErrors(request, errors);
        return (new ActionForward(mapping.getInput()));
      }
      
      // Forward control to the specified success URI
      return (mapping.findForward("success"));

    } finally {
      NDC.pop();
    }

  }


}
