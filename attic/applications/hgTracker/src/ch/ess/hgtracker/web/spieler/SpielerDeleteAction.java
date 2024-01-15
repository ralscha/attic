package ch.ess.hgtracker.web.spieler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ch.ess.hgtracker.db.Spieler;

public class SpielerDeleteAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    String spielerId = request.getParameter("id");
    Transaction tx = dbSession.beginTransaction();
    try {
      Spieler spieler = (Spieler)dbSession.get(Spieler.class, new Integer(spielerId));
      if (spieler.getPunkte().size() == 0) {
        dbSession.delete(spieler);
      } else {
        spieler.setAktiv(Boolean.FALSE);
      }
      return mapping.findForward("delete");
    } finally {
      tx.commit();
    }
  }
}