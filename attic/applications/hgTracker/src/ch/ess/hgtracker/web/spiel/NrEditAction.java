package ch.ess.hgtracker.web.spiel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spieler;

public class NrEditAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      SpielForm spielform = (SpielForm)form;
      String punkteId = (String)request.getAttribute("punktId");
      Punkte punkte = (Punkte)dbSession.get(Punkte.class, new Integer(punkteId));
      Spieler spieler = punkte.getSpieler();
      spielform.setName(spieler.getNachname() + " " + spieler.getVorname() + ", " + spieler.getJahrgang());
      if (punkte.getRies1() != null) {
        spielform.setShowries1nr(true);
      } else {
        spielform.setShowries1nr(false);
      }
      if (punkte.getRies2() != null) {
        spielform.setShowries2nr(true);
      } else {
        spielform.setShowries2nr(false);
      }
      if (punkte.getRies3() != null) {
        spielform.setShowries3nr(true);
      } else {
        spielform.setShowries3nr(false);
      }
      if (punkte.getRies4() != null) {
        spielform.setShowries4nr(true);
      } else {
        spielform.setShowries4nr(false);
      }
      if (punkte.getRies5() != null) {
        spielform.setShowries5nr(true);
      } else {
        spielform.setShowries5nr(false);
      }
      if (punkte.getRies6() != null) {
        spielform.setShowries6nr(true);
      } else {
        spielform.setShowries6nr(false);
      }

      int totalnr = 0;
      if (punkte.getNr1() != null && punkte.getNr1().booleanValue()) {
        totalnr++;
      }
      if (punkte.getNr2() != null && punkte.getNr2().booleanValue()) {
        totalnr++;
      }
      if (punkte.getNr3() != null && punkte.getNr3().booleanValue()) {
        totalnr++;
      }
      if (punkte.getNr4() != null && punkte.getNr4().booleanValue()) {
        totalnr++;
      }
      if (punkte.getNr5() != null && punkte.getNr5().booleanValue()) {
        totalnr++;
      }
      if (punkte.getNr6() != null && punkte.getNr6().booleanValue()) {
        totalnr++;
      }

      spielform.setRies1nr(punkte.getNr1());
      spielform.setRies2nr(punkte.getNr2());
      spielform.setRies3nr(punkte.getNr3());
      spielform.setRies4nr(punkte.getNr4());
      spielform.setRies5nr(punkte.getNr5());
      spielform.setRies6nr(punkte.getNr6());
      spielform.setPunkteId(punkte.getId().intValue());
      spielform.setTotalNrAlt(totalnr);

      return mapping.getInputForward();
    } finally {
      tx.commit();
    }
  }
}