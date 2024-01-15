package ch.ess.hgtracker.web.spiel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ch.ess.hgtracker.db.Spiel;

public class SpielEditAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    SpielForm spielform = (SpielForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {

      spielform.setArt(Util.getArtList(dbSession));

      Spiel spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielform.getId()));
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      spielform.setDatum(sdf.format(spiel.getDatum()));
      Calendar cal = new GregorianCalendar();
      cal.setTime(spiel.getDatum());
      if (cal.get(Calendar.HOUR_OF_DAY) > 0) {
        spielform.setStunden(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        if (cal.get(Calendar.MINUTE) < 10) {
          DecimalFormat df = new DecimalFormat("00");
          spielform.setMinuten(df.format(cal.get(Calendar.MINUTE)));
        } else {
          spielform.setMinuten(String.valueOf(cal.get(Calendar.MINUTE)));
        }
      } else {
        spielform.setStunden(null);
        spielform.setMinuten(null);
      }
      spielform.setOrt(spiel.getOrt());
      spielform.setGegner(spiel.getGegner());
      spielform.setKampfrichter(spiel.getKampfrichter());
      spielform.setKampfrichterGegner(spiel.getKampfrichterGegner());
      if (spiel.getTotalNr() != null) {
        spielform.setTotalNr(spiel.getTotalNr().toString());
      } else {
        spielform.setTotalNr(null);
      }
      if (spiel.getSchlagPunkteGegner() != null) {
        spielform.setSchlagPunkteGegner(spiel.getSchlagPunkteGegner().toString());
      } else {
        spielform.setSchlagPunkteGegner(null);
      }
      spielform.setArtId(spiel.getArt().getId().toString());
      return mapping.getInputForward();
    } finally {
      tx.commit();
    }
  }
}