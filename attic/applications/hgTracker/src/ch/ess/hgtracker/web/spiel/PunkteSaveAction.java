package ch.ess.hgtracker.web.spiel;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spiel;

public class PunkteSaveAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (isCancelled(request)) {
      return mapping.findForward("zurueck");
    }
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      Enumeration<String> et = request.getParameterNames();
      Set<Punkte> punkteIds = new HashSet<Punkte>();
      while (et.hasMoreElements()) {
        String key = et.nextElement();
        if (key.startsWith("ries") || (key.startsWith("rangpunkte"))) {
          int pos = 0;
          if (key.startsWith("ries")) {
            pos = 5;
          } else {
            pos = 10;
          }
          String punkteId = key.substring(pos);
          Punkte punkte = (Punkte)dbSession.get(Punkte.class, new Integer(punkteId));
          punkteIds.add(punkte);
          String resultatString = request.getParameter(key);
          Integer resultat;
          if (StringUtils.isNotBlank(resultatString)) {
            resultat = new Integer(resultatString);
          } else {
            resultat = null;
          }
          if (key.startsWith("ries1")) {
            punkte.setRies1(resultat);
          } else if (key.startsWith("ries2")) {
            punkte.setRies2(resultat);
          } else if (key.startsWith("ries3")) {
            punkte.setRies3(resultat);
          } else if (key.startsWith("ries4")) {
            punkte.setRies4(resultat);
          } else if (key.startsWith("ries5")) {
            punkte.setRies5(resultat);
          } else if (key.startsWith("ries6")) {
            punkte.setRies6(resultat);
          } else if (key.startsWith("rangpunkte")) {
            punkte.setRangpunkte(resultat);
          }
        } else if (key.startsWith("punkteId")) {
          int pos = key.indexOf(".");
          String idString = key.substring(8, pos);
          request.setAttribute("punktId", idString);
        }
      }
      Iterator<Punkte> it = punkteIds.iterator();
      while (it.hasNext()) {
        Punkte punkt = it.next();
        int anzahl = 0;
        if (punkt.getRies1() != null) {
          anzahl = anzahl + 1;
        }
        if (punkt.getRies2() != null) {
          anzahl = anzahl + 1;
        }
        if (punkt.getRies3() != null) {
          anzahl = anzahl + 1;
        }
        if (punkt.getRies4() != null) {
          anzahl = anzahl + 1;
        }
        if (punkt.getRies5() != null) {
          anzahl = anzahl + 1;
        }
        if (punkt.getRies6() != null) {
          anzahl = anzahl + 1;
        }
        punkt.setTotalStrich(new Integer(anzahl));
        Spiel spiel = punkt.getSpiel();
        if (spiel.getTotalNrGegner() == null) {
          spiel.setTotalNrGegner(new Integer(0));
        }
      }

      // meldung speichern erfolgreich
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("saveOK"));
      saveMessages(request, messages);
      if (request.getAttribute("punktId") != null) {
        return mapping.findForward("nrEdit");
      }
      return mapping.findForward("tabelle");
    } finally {
      tx.commit();
    }
  }
}