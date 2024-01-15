package ch.ess.pbroker.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import ch.ess.pbroker.form.*;
import ch.ess.pbroker.session.*;
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.*;
import com.codestudio.util.*;

public class DeleteBasketKandidatAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		//Locale locale = getLocale(request);
		//MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession(true);
		
    if (session.getAttribute(Constants.USER_KEY) == null)
      return (mapping.findForward("login"));

    
    KandidatenBasket basket = (KandidatenBasket)session.getAttribute(Constants.BASKET_KEY);
    if (basket != null) {
      try {
        String idstr = request.getParameter("id");
        int id = Integer.parseInt(idstr);
        basket.removeKandidat(id);
      } catch (NumberFormatException pe) { }
    }

    String from = request.getParameter("from");
    if (from != null) {
      from = URLDecoder.decode(from);
      return new ActionForward(from, false);
    } else {
      return (mapping.findForward("basket"));
    }
		
  }

}
