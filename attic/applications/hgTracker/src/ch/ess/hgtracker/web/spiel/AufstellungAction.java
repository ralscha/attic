package ch.ess.hgtracker.web.spiel;

import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spiel;
import ch.ess.hgtracker.db.Spieler;

public class AufstellungAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (isCancelled(request)) {
      return mapping.findForward("zurueck");
    }
    SpielForm spielform = (SpielForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {

      Spiel spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielform.getId()));
      Enumeration<String> et = request.getParameterNames();
      while (et.hasMoreElements()) {
        String key = et.nextElement();

        // reihenfolge:die reihenfolge wird in die tabelle pubkte eingtragen,
        // für den ausgewählten spieler und spiel,
        // falls noch kein eintrag vorhanden ist wird diser erstellt
        if (key.startsWith("reihenfolge")) {
          String spielerId = key.substring(11);
          Spieler spieler = (Spieler)dbSession.get(Spieler.class, new Integer(spielerId));
          Punkte punkte = getPunkte(dbSession, spiel, spieler);
          String value = request.getParameter(key);
          if (StringUtils.isNotBlank(value)) {
            punkte.setReihenfolge(new Integer(value));
            dbSession.saveOrUpdate(punkte);
          }
        } else if (key.startsWith("uez")) {
          String spielerId = key.substring(3);
          Spieler spieler = (Spieler)dbSession.get(Spieler.class, new Integer(spielerId));
          Punkte punkte = getPunkte(dbSession, spiel, spieler);
          punkte.setUeberzaehligeSpieler(Boolean.TRUE);
          dbSession.saveOrUpdate(punkte);
        }
      }

      return mapping.findForward("weiter");
    } finally {
      tx.commit();
    }
  }

  @SuppressWarnings("unchecked")
  private Punkte getPunkte(Session dbSession, Spiel spiel, Spieler spieler) {
    Criteria criteria = dbSession.createCriteria(Punkte.class);
    criteria.add(Restrictions.eq("spiel", spiel));
    criteria.add(Restrictions.eq("spieler", spieler));
    List<Punkte> punkteListe = criteria.list();
    if (punkteListe.isEmpty()) {
      Punkte punkte = new Punkte();
      punkte.setSpiel(spiel);
      punkte.setSpieler(spieler);
      punkte.setUeberzaehligeSpieler(Boolean.FALSE);
      return punkte;
    } else {
      Punkte punkte = punkteListe.get(0);
      return punkte;
    }
  }
}
