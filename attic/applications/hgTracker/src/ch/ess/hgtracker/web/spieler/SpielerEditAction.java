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

public class SpielerEditAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    SpielerForm spielerform = (SpielerForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      Spieler spieler = (Spieler)dbSession.get(Spieler.class, new Integer(spielerform.getId()));
      spielerform.setNachname(spieler.getNachname());
      spielerform.setVorname(spieler.getVorname());
      spielerform.setStrasse(spieler.getStrasse());
      spielerform.setPlz(spieler.getPlz());
      spielerform.setOrt(spieler.getOrt());
      spielerform.setJahrgang(spieler.getJahrgang().toString());
      spielerform.setAufgestellt(spieler.getAufgestellt());
      if (spieler.getReihenfolge() != null) {
        spielerform.setReihenfolge(spieler.getReihenfolge().toString());
      } else {
        spielerform.setReihenfolge(null);
      }
      spielerform.setTelNr(spieler.getTelNr());
      spielerform.setMobileNr(spieler.getMobileNr());
      spielerform.setAktiv(spieler.getAktiv());
      return mapping.getInputForward();
    } finally {
      tx.commit();
    }
  }
}