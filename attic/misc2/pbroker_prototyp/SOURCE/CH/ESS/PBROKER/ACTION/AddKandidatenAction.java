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

public class AddKandidatenAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		Locale locale = getLocale(request);
		MessageResources messages = getResources(servlet);
		HttpSession session = request.getSession(true);
		
    if (session.getAttribute(Constants.USER_KEY) == null)
      return (mapping.findForward("login"));

    KandidatenBasket basket = (KandidatenBasket)session.getAttribute(Constants.BASKET_KEY);
    if (basket == null) {
      basket = new KandidatenBasket();
      session.setAttribute(Constants.BASKET_KEY, basket);
    }

    String[] kandidatenids = request.getParameterValues("kandidatid");
    int length = 0;

    if (kandidatenids != null)
      length = kandidatenids.length;

    for (int i = 0; i < length; i++) {
      try {
        Kandidaten kandidat = Db.getKandidatenTable().selectOne("KandidatId = " + kandidatenids[i]);
        if (kandidat != null) {

          Connection conn = null;
		      try {
			      conn = SQLManager.getInstance().requestConnection();
			      Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pb_kandidaten_kalender where kandidatid = " + kandidatenids[i]);
            if (rs.next()) {
              kandidat.setVerfuegbar(rs.getString("kalender"));
            }
		      } finally {
			      SQLManager.getInstance().returnConnection(conn);
		      }

          basket.addKandidat(kandidat);
        }
      } catch (SQLException sqle) {
        throw new ServletException(sqle);
      } catch (PoolPropsException ppe) {
        throw new ServletException(ppe);
      }
    }

    String to = request.getParameter("tourl");
    if ((to != null) && (!to.equals(""))) {
      return new ActionForward(to, false);
    } else {
      return (mapping.findForward("menu"));
    }
	
	
  }

}
