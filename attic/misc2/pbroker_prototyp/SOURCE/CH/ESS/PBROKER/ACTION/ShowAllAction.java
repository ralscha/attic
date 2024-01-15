package ch.ess.pbroker.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import ch.ess.pbroker.form.*;
import ch.ess.pbroker.session.*;
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.*;
import com.codestudio.util.*;

public class ShowAllAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		//Locale locale = getLocale(request);
		//MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession();
		
		if (session.getAttribute(Constants.USER_KEY) == null)
		  return (mapping.findForward("login"));


    try {
      List allList = Db.getKandidatenTable().select(null, "KandidatId");
    
      session.setAttribute(ch.ess.pbroker.Constants.KANDIDATEN_KEY, allList);

      return (mapping.findForward("show"));

    } catch (SQLException sqle) {
      throw new ServletException(sqle); 
    } catch (PoolPropsException ppe) {
      throw new ServletException(ppe);
    }
  }

}
