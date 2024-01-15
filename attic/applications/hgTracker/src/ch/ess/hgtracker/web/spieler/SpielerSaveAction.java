package ch.ess.hgtracker.web.spieler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spieler;

public class SpielerSaveAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (isCancelled(request)) {
      return mapping.findForward("list");
    }
    SpielerForm spielerform = (SpielerForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      Spieler spieler;
      if (spielerform.getId() == 0) {
        spieler = new Spieler();
      } else {
        spieler = (Spieler)dbSession.get(Spieler.class, new Integer(spielerform.getId()));
      }
      Club club = (Club)request.getSession().getAttribute("club");
      spieler.setClub(club);
      spieler.setNachname(spielerform.getNachname());
      spieler.setVorname(spielerform.getVorname());
      spieler.setStrasse(spielerform.getStrasse());
      spieler.setPlz(spielerform.getPlz());
      spieler.setOrt(spielerform.getOrt());
      spieler.setJahrgang(new Integer(spielerform.getJahrgang()));
      if (spielerform.getAufgestellt() == null) {
        spieler.setAufgestellt(Boolean.FALSE);
      } else {
        spieler.setAufgestellt(spielerform.getAufgestellt());
      }
      if (StringUtils.isNotEmpty(spielerform.getReihenfolge())) {
        spieler.setReihenfolge(new Integer(spielerform.getReihenfolge()));
      } else {
        spieler.setReihenfolge(null);
      }
      spieler.setEmail(spielerform.getEmail());
      spieler.setTelNr(spielerform.getTelNr());
      spieler.setMobileNr(spielerform.getMobileNr());
      if (spielerform.getAktiv() == null) {
        spieler.setAktiv(Boolean.FALSE);
      } else {
        spieler.setAktiv(spielerform.getAktiv());
      }

      if (spielerform.getId() == 0) {
        dbSession.save(spieler);
      }

      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("saveOK"));
      saveMessages(request, messages);
      return mapping.findForward("list");
    } finally {
      tx.commit();
    }
  }
}
