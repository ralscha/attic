package ch.ess.hgtracker.web.durchschnitt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Club;

public class DurchschnittAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    
    try {
      
      String queryString = "select spieler.nachname, spieler.vorname, spieler.jahrgang, "
          + "sum(punkte.ries1), sum(punkte.ries2), sum(punkte.ries3), sum(punkte.ries4), "
          + "sum(punkte.ries5), sum(punkte.ries6), sum(punkte.totalStrich) "
          + "from Punkte punkte inner join punkte.spieler spieler " + "inner join punkte.spiel spiel "
          + "where (year(spiel.datum)=?) and (spiel.club=?)"
          + "group by spieler.vorname, spieler.nachname, spieler.jahrgang";
      String webLogin = request.getParameter("webLogin");
      Club club;

      if (StringUtils.isNotBlank(webLogin)) {
        Criteria criteria = dbSession.createCriteria(Club.class);
        criteria.add(Restrictions.eq("webLogin", webLogin));
        club = (Club)criteria.uniqueResult();
      } else {
        club = (Club)request.getSession().getAttribute("club");
      }

      Calendar today = new GregorianCalendar();
      int jahr = today.get(Calendar.YEAR);
      DurchschnittListForm dlf = (DurchschnittListForm)form;
      if (StringUtils.isNotBlank(dlf.getJahr())) {
        jahr = Integer.parseInt(dlf.getJahr());
      }
      Query query = dbSession.createQuery(queryString);
      query.setInteger(0, jahr);
      query.setEntity(1, club);
      List<Object[]> resultatList = query.list();
      if (!resultatList.isEmpty()) {
        Iterator<Object[]> it = resultatList.iterator();
        List<Durchschnitte> durchschnitteList = new ArrayList<Durchschnitte>();
        while (it.hasNext()) {
          Object[] row = it.next();
          Durchschnitte durchschnitte = new Durchschnitte();
          durchschnitte.setNachname((String)row[0]);
          durchschnitte.setVorname((String)row[1]);
          durchschnitte.setJahrgang((Integer)row[2]);
          Integer ries1 = (Integer)row[3];
          Integer ries2 = (Integer)row[4];
          Integer ries3 = (Integer)row[5];
          Integer ries4 = (Integer)row[6];
          Integer ries5 = (Integer)row[7];
          Integer ries6 = (Integer)row[8];
          Integer totalStrich = (Integer)row[9];
          int totalRies = 0;
          if (ries1 != null) {
            totalRies = totalRies + ries1.intValue();
          }
          if (ries2 != null) {
            totalRies = totalRies + ries2.intValue();
          }
          if (ries3 != null) {
            totalRies = totalRies + ries3.intValue();
          }
          if (ries4 != null) {
            totalRies = totalRies + ries4.intValue();
          }
          if (ries5 != null) {
            totalRies = totalRies + ries5.intValue();
          }
          if (ries6 != null) {
            totalRies = totalRies + ries6.intValue();
          }
          durchschnitte.setPunkte(new Integer(totalRies));
          durchschnitte.setStriche(totalStrich);
          BigDecimal schnitt = new BigDecimal(totalRies);
          if (totalStrich.intValue() != 0) {
            schnitt = schnitt.divide(new BigDecimal(totalStrich.intValue()), 2, BigDecimal.ROUND_HALF_UP);
          } 
          durchschnitte.setSchnitt(schnitt);
          durchschnitteList.add(durchschnitte);
        }
        request.getSession().setAttribute("jahr", String.valueOf(jahr));
        request.getSession().setAttribute("durchschnitte", durchschnitteList);
      } else {
        request.getSession().removeAttribute("jahr");
        request.getSession().removeAttribute("durchschnitte");
      }

      if (request.getParameter("mobile") != null) {
        return mapping.findForward("mobile");
      }
      return mapping.findForward("durchschnitt");
    } finally {
      tx.commit();
    }
  }
}