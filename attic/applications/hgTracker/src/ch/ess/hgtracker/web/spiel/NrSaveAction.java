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

public class NrSaveAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (isCancelled(request)) {
      return mapping.findForward("tabelle");
    }
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      SpielForm spielform = (SpielForm)form;

      Punkte punkte = (Punkte)dbSession.get(Punkte.class, new Integer(spielform.getPunkteId()));
      punkte.setNr1(spielform.getRies1nr());
      punkte.setNr2(spielform.getRies2nr());
      punkte.setNr3(spielform.getRies3nr());
      punkte.setNr4(spielform.getRies4nr());
      punkte.setNr5(spielform.getRies5nr());
      punkte.setNr6(spielform.getRies6nr());

      int totalNr = 0;
      if (punkte.getNr1() != null && punkte.getNr1().booleanValue()) {
        totalNr++;
      }
      if (punkte.getNr2() != null && punkte.getNr2().booleanValue()) {
        totalNr++;
      }
      if (punkte.getNr3() != null && punkte.getNr3().booleanValue()) {
        totalNr++;
      }
      if (punkte.getNr4() != null && punkte.getNr4().booleanValue()) {
        totalNr++;
      }
      if (punkte.getNr5() != null && punkte.getNr5().booleanValue()) {
        totalNr++;
      }
      if (punkte.getNr6() != null && punkte.getNr6().booleanValue()) {
        totalNr++;
      }

      int totalNrDifferenz = totalNr - spielform.getTotalNrAlt();
      if (punkte.getSpiel().getTotalNrGegner() != null) {
        int dbTotal = punkte.getSpiel().getTotalNrGegner().intValue();
        dbTotal = dbTotal + totalNrDifferenz;
        punkte.getSpiel().setTotalNrGegner(new Integer(dbTotal));
      } else {
        punkte.getSpiel().setTotalNrGegner(new Integer(totalNr));
      }

      return mapping.findForward("tabelle");
    } finally {
      tx.commit();
    }
  }
}
