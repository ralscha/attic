package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import lottowin.form.*;
import lottowin.resource.*;
import lottowin.db.*;
import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import javax.swing.table.*;
import org.log4j.*;

public final class GoCheckAction extends Action {

  private static Category CAT = Category.getInstance(GoCheckAction.class.getName());

  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {


    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {

      User user = (User)session.getAttribute(lottowin.Constants.USER_KEY);
      if (user == null) {
        CAT.warn("user object not in session");
        return (mapping.findForward("logon"));
      }

      // remove old attributes 
      session.removeAttribute("update");
      session.removeAttribute("page");
	    session.removeAttribute("model");

      CAT.debug("go check action");

      // Forward control to the specified success URI
      return (mapping.findForward("success"));

    } finally {
      NDC.pop();
    }

  }

}
