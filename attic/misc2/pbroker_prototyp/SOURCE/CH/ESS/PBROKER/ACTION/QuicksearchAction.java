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
import ch.ess.pbroker.db.support.*;
import com.codestudio.util.*;

public class QuicksearchAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		//Locale locale = getLocale(request);
		//MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession(true);
		
    User user = (User)session.getAttribute(Constants.USER_KEY);
    if (user == null)
      return (mapping.findForward("login"));

    session.removeAttribute(ch.ess.pbroker.Constants.KANDIDATEN_KEY);

    String searchStr = request.getParameter("searchstring");
    if (searchStr == null)
      searchStr = "";

    SearchCriterion sc = (SearchCriterion)session.getAttribute(Constants.SEARCH_CRITERION_KEY);
    Rekrutierung rekrutierung = (Rekrutierung)session.getAttribute(Constants.REKRUTIERUNG_KEY);

    boolean isNew = false;
    if (sc == null) {
      sc = new SearchCriterion();
      isNew = true;
    }

    if (rekrutierung == null) {
      rekrutierung = new Rekrutierung(user.getBenutzer()); 
      session.setAttribute(Constants.REKRUTIERUNG_KEY, rekrutierung);
    }

	  Connection conn = null;
		try {
			conn = SQLManager.getInstance().requestConnection();

			CallableStatement call = conn.prepareCall( " {call pb_quick_search(?)} " );
      call.setString(1, searchStr);
      ResultSet rs = call.executeQuery();
  
      List resultList = new ArrayList();

      while(rs.next()) {
        Kandidaten kandidat = KandidatenTable.makeObject(rs);
        kandidat.setVerfuegbar(rs.getString("kalender"));
        resultList.add(kandidat);
      }                     

      if (resultList.size() > 0) {
        session.setAttribute(ch.ess.pbroker.Constants.KANDIDATEN_KEY, resultList);
        
        sc.reset();
        sc.setQuicksearch(searchStr);
        rekrutierung.setSkills(searchStr);

        if (isNew) 
          session.setAttribute(Constants.SEARCH_CRITERION_KEY, sc);

        return (mapping.findForward("show"));

      } else {
        request.setAttribute(ch.ess.pbroker.Constants.NOTFOUND_KEY, 
            "keine Kandidatenprofile mit diesem Suchbegriff gefunden: " + searchStr );
        return (mapping.findForward("notfound"));
      }
    } catch (SQLException sqle) {
      throw new ServletException(sqle); 
    } catch (PoolPropsException ppe) {
      throw new ServletException(ppe);
    } finally {
      try {
        SQLManager.getInstance().returnConnection(conn);
      } catch (PoolPropsException ppe) {
        throw new ServletException(ppe);
      }
    }

		
  }

}
