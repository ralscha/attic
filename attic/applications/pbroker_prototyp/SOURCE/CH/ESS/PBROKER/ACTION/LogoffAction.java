package ch.ess.pbroker.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import ch.ess.pbroker.*;
import com.codestudio.util.*;

public final class LogoffAction extends ActionBase {

    public ActionForward perform(ActionServlet servlet,
				 ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
    	throws IOException, ServletException {

      HttpSession session = request.getSession();

      session.removeAttribute(Constants.ERROR_KEY);
      session.removeAttribute(Constants.USER_KEY);
      session.removeAttribute(Constants.KANDIDATEN_KEY);
      session.removeAttribute(Constants.BASKET_KEY);

      session.invalidate();

      // Forward control to the specified success URI
      return (mapping.findForward("login"));

    }


}
