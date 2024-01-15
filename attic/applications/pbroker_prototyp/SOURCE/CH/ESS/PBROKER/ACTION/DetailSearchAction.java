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

public class DetailSearchAction extends ActionBase {

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

    boolean isNew = false;
    SearchCriterion sc = (SearchCriterion)session.getAttribute(Constants.SEARCH_CRITERION_KEY);
    Rekrutierung rekrutierung = (Rekrutierung)session.getAttribute(Constants.REKRUTIERUNG_KEY);

    if (sc == null) {
      isNew = true;
      sc = new SearchCriterion();
    }
    sc.setQuicksearch(null);

    if (rekrutierung == null) {
      rekrutierung = new Rekrutierung(user.getBenutzer()); 
      session.setAttribute(Constants.REKRUTIERUNG_KEY, rekrutierung);
    }

    Connection conn = null;
    try {
		  try {


        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
          String param = (String)enumeration.nextElement();
          if (param.startsWith("skill")) {
            String value = request.getParameter(param);
            if (value.startsWith("kann")) {
              String tmp = value.substring(4);
              try {
                int id = Integer.parseInt(tmp);
                sc.removeSkill(id);
                sc.addKannSkill(id);
              } catch (NumberFormatException nfe) {}
            } else if (value.startsWith("muss")) {
              String tmp = value.substring(4);
              try {
                int id = Integer.parseInt(tmp);
                sc.removeSkill(id);
                sc.addMussSkill(id);
              } catch (NumberFormatException nfe) {}
            } else if (value.startsWith("na")) {
              String tmp = value.substring(2);
              try {
                int id = Integer.parseInt(tmp);
                sc.removeSkill(id);
              } catch (NumberFormatException nfe) {}          
            }
          }      
        }

        if (isNew)
          session.setAttribute(Constants.SEARCH_CRITERION_KEY, sc);


        //User clicked search
        if (request.getParameter("search") != null) {
			    conn = SQLManager.getInstance().requestConnection();

          copyToRekrutierung(sc, rekrutierung);

			    CallableStatement call = conn.prepareCall( " {call pb_extended_search(?,?,?,?,?,?)} " );
			    String searchMussSkillsString = sc.getMussSkillsString();
			    String searchKannSkillsString = sc.getKannSkillsString();
			    if (!searchMussSkillsString.equals("")) {
          call.setString(1, searchMussSkillsString); // must
             } else {
           	  call.setNull(1, Types.VARCHAR); // must
             }
             if (!searchKannSkillsString.equals("")) {
          call.setString(2, searchKannSkillsString); // wish
           } else {
              call.setNull(2, Types.VARCHAR);
           }

          call.setNull(3, Types.TIMESTAMP);
          call.setNull(4, Types.TIMESTAMP);
          call.setNull(5, Types.DECIMAL);
          call.setNull(6, Types.INTEGER);

          ResultSet rs = call.executeQuery();
    
          List resultList = new ArrayList();

          while(rs.next()) {
            Kandidaten kandidat = KandidatenTable.makeObject(rs);
            kandidat.setScore(rs.getString("score"));
            kandidat.setVerfuegbar(rs.getString("kalender"));
            resultList.add(kandidat);
          }                     
  
          session.setAttribute(ch.ess.pbroker.Constants.KANDIDATEN_KEY, resultList);
          return (mapping.findForward("show"));

        } else {
          return (mapping.findForward("search"));
        }

      } catch (SQLException sqle) {
        throw new ServletException(sqle); 
      } finally {
        if (conn != null)
          SQLManager.getInstance().returnConnection(conn);
      }
    } catch (PoolPropsException ppe) {
      throw new ServletException(ppe);
    }
		
  }

  private void copyToRekrutierung(SearchCriterion sc, Rekrutierung rek) throws SQLException, PoolPropsException {
    String kann = sc.getKannDescription();
    String muss = sc.getMussDescription();
    String tg = sc.getTaetigkeitsgebiete();

    boolean addkann = false;

    StringBuffer sb = new StringBuffer();
    if ((kann != null) && (!kann.trim().equals("")) ) {
      sb.append("Kann: " + kann);
      addkann = true;
    }
  
    if ((muss != null) && (!muss.trim().equals("")) ) {
      if (addkann)
        sb.append("\n");

      sb.append("Muss: " + muss);
    }

    rek.setTaetigkeitsgebiete(tg);

    rek.setSkills(sb.toString());               
  }

}
