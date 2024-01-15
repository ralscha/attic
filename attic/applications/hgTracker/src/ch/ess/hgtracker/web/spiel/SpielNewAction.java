package ch.ess.hgtracker.web.spiel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SpielNewAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    SpielForm spielform = (SpielForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {

      spielform.setArt(Util.getArtList(dbSession));
      spielform.setDatum(null);
      spielform.setOrt(null);
      spielform.setGegner(null);
      spielform.setKampfrichter(null);
      spielform.setKampfrichterGegner(null);
      spielform.setTotalNr(null);
      spielform.setSchlagPunkteGegner(null);
      spielform.setArtId(null);
      spielform.setStunden(null);
      spielform.setMinuten(null);
      spielform.setId(0);
      return mapping.getInputForward();
    } finally {
      tx.commit();
    }
  }
}