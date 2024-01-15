package ch.ess.hgtracker.web.spiel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ch.ess.hgtracker.db.Spiel;

public class SpielDeleteAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    String spielId = request.getParameter("id");
    Transaction tx = dbSession.beginTransaction();
    try {
      Spiel spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielId));
      dbSession.delete(spiel);

      return mapping.findForward("delete");
    } finally {
      tx.commit();
    }
  }
}