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
import common.net.*;

public class OffertanfrageAction extends ActionBase {

	public ActionForward perform(ActionServlet servlet, ActionMapping mapping, ActionForm form,
 			              HttpServletRequest request, HttpServletResponse response)
 	                    throws IOException, ServletException {

	
		// Extract attributes and parameters we will need
		
    HttpSession session = request.getSession(true);
		
    User user = (User)session.getAttribute(Constants.USER_KEY);
    if (user == null)
      return (mapping.findForward("login"));

    String anfragenrstr = request.getParameter("anfrage");
    String mode = request.getParameter("mode");

    if ("s".equals(mode)) {
      if (anfragenrstr != null) {
        request.setAttribute("anfrage", new Integer(anfragenrstr));
      }
      request.setAttribute("finish", "1");
      deleteAllSessionObjects(session);
      return (mapping.findForward("next"));   
    }

    if (mode != null) {

      int anfragenr;
      if (anfragenrstr == null) {
        anfragenr = (int)System.currentTimeMillis();
      } else {
        anfragenr = Integer.parseInt(anfragenrstr);
      }
      request.setAttribute("anfrage", new Integer(anfragenr));

      try {
        Anfragen anfrage = Db.getAnfragenTable().selectOne("AnfrageId = " + anfragenr);
        if (anfrage == null) {

          anfrage = new Anfragen();
          anfrage.setAnfrageid(anfragenr);
          anfrage.setAktiv(true);
          anfrage.setAnfragerid(user.getBenutzer().getBenutzerid());
          anfrage.setAnfragedate(new java.sql.Timestamp(new java.util.Date().getTime()));

          Db.getAnfragenTable().insert(anfrage);

          Rekrutierung rekrutierung = (Rekrutierung)session.getAttribute(Constants.REKRUTIERUNG_KEY);
          if (rekrutierung != null) {
            Rekrutierungsangaben rek = new Rekrutierungsangaben();
            rek.setAnfrageid(anfragenr);
            rek.setPensum(rekrutierung.getPensum());
            rek.setProjekt(rekrutierung.getProjekt());
            rek.setTaetigkeitsgebiet(rekrutierung.getTaetigkeitsgebiete());
            rek.setSkills(rekrutierung.getSkills());
            rek.setVon(rekrutierung.getVontermin());
            rek.setBis(rekrutierung.getBistermin());
            rek.setAnsprechspartner(rekrutierung.getAnsprech());
            rek.setAnsprechspartnertel(rekrutierung.getAnsprechtel());
            rek.setBemerkung(rekrutierung.getBemerkung());
            rek.setAnsprechspartneremail(rekrutierung.getAnsprechemail());
            rek.setAufgaben(rekrutierung.getAufgaben());
            rek.setOffertebis(rekrutierung.getOffertebis());
            rek.setOe(rekrutierung.getOe());

            Db.getRekrutierungsangabenTable().insert(rek);
          }

          if ("a".equals(mode)) {
            request.setAttribute("allanfrage", "1");
            List lieferList = Db.getLieferantenTable().select();
            for (int i = 0; i < lieferList.size(); i++) {
              Lieferanten lieferant = (Lieferanten)lieferList.get(i);

              Anfragelieferanten al = new Anfragelieferanten();
              al.setLieferantid(lieferant.getLieferantid());
              al.setAnfrageid(anfragenr);
              al.setAllgemein(true);

              Anfragelieferanten altest = Db.getAnfragelieferantenTable().selectOne("LieferantId = " + lieferant.getLieferantid() + " AND AnfrageId = " + anfragenr);
              if (altest == null)
                Db.getAnfragelieferantenTable().insert(al);
            }
          } else if ("k".equals(mode)) {
            request.setAttribute("konkreteanfrage", "1");
            Map lieferantenMap = (Map)session.getAttribute("pbroker.offertanfrage");

            Iterator keys = lieferantenMap.keySet().iterator(); 
            while(keys.hasNext()) { 
              String lieferant = (String)keys.next();
              List kandidatenList = (List)lieferantenMap.get(lieferant);
              for (int i = 0; i < kandidatenList.size(); i++) {
                Kandidaten kandidat = (Kandidaten)kandidatenList.get(i);
                
                Anfragekandidaten ak = new Anfragekandidaten();
                ak.setKandidatid(kandidat.getKandidatid());
                ak.setAnfrageid(anfragenr);

                Db.getAnfragekandidatenTable().insert(ak);
              }
            }
                
          }

        } else {

          if ("a".equals(mode)) {           
            List lieferList = Db.getLieferantenTable().select();
            for (int i = 0; i < lieferList.size(); i++) {
              Lieferanten lieferant = (Lieferanten)lieferList.get(i);

              Anfragelieferanten al = new Anfragelieferanten();
              al.setLieferantid(lieferant.getLieferantid());
              al.setAnfrageid(anfragenr);
              al.setAllgemein(true);

              Anfragelieferanten altest = Db.getAnfragelieferantenTable().selectOne("LieferantId = " + lieferant.getLieferantid() + " AND AnfrageId = " + anfragenr);
              if (altest == null)
                Db.getAnfragelieferantenTable().insert(al);
            }
          } else if ("k".equals(mode)) {
            Map lieferantenMap = (Map)session.getAttribute("pbroker.offertanfrage");

            Iterator keys = lieferantenMap.keySet().iterator(); 
            while(keys.hasNext()) { 
              String lieferant = (String)keys.next();
              List kandidatenList = (List)lieferantenMap.get(lieferant);
              for (int i = 0; i < kandidatenList.size(); i++) {
                Kandidaten kandidat = (Kandidaten)kandidatenList.get(i);
                
                Anfragekandidaten ak = new Anfragekandidaten();
                ak.setKandidatid(kandidat.getKandidatid());
                ak.setAnfrageid(anfragenr);

                Db.getAnfragekandidatenTable().insert(ak);
              }
            }
          }

          //Zum zweiten ok alle Anfragen erledigt
          request.setAttribute("finish", "1");
          deleteAllSessionObjects(session);
        }
      } catch (Exception e) {
        throw new ServletException(e);
      }

      //sendMails();

    }

    return (mapping.findForward("next"));   

 	}

  private void sendMails() {
    Thread runner = new Thread(new Runnable() {
            public void run() {
              MailSender ms = new MailSender("mail.ess.ch");              
              StringBuffer msg = new StringBuffer();
              for (int i = 0; i < 100; i++)
                msg.append("RALPH");

              for (int i = 0; i < 70; i++) {
                ms.sendMail("schaer@ess.ch", "sr@ess.ch", "DELETEME", msg.toString());
              }
            }
        });

    runner.start();
  
  }

  private void deleteAllSessionObjects(HttpSession session) {
    session.removeAttribute(Constants.BASKET_KEY);
    session.removeAttribute(Constants.OFFERTANFRAGE_KEY);
    session.removeAttribute(Constants.KANDIDATEN_KEY);
    session.removeAttribute(Constants.REKRUTIERUNGS_KEY);
    session.removeAttribute(Constants.SEARCH_CRITERION_KEY);
  }
}
