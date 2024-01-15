package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import lottowin.*;
import lottowin.db.*;
import org.log4j.*;

public final class LogoffAction extends LottowinBaseAction {


  public ActionForward perform(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {

  
    //remove all session objects
    Enumeration enum = session.getAttributeNames();
    while (enum.hasMoreElements()) {
      String attribute = (String) enum.nextElement();
      
      if (CAT.isDebugEnabled())
        CAT.debug("remove session attribute: " + attribute);
      
      if (attribute.equals(lottowin.Constants.USER_KEY)) {
        User user = (User)session.getAttribute(attribute);
        CAT.info("user "+ user.getUserid() + " logged off");
      }

      session.removeAttribute(attribute);
    }

    //invalidate session
    session.invalidate();

    // Forward control to the specified success URI
    return (mapping.findForward("success"));

  
  }


}
