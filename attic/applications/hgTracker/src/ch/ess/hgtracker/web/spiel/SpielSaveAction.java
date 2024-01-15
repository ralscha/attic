package ch.ess.hgtracker.web.spiel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spiel;
import ch.ess.hgtracker.db.Spieler;

public class SpielSaveAction extends Action {

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
      // start abspeichern des spieles
      Spiel spiel;
      if (spielform.getId() == 0) {
        spiel = new Spiel();
      } else {
        spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielform.getId()));
      }
      Club club = (Club)request.getSession().getAttribute("club");
      spiel.setClub(club);
      SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
      Date datum = sdf.parse(spielform.getDatum());
      if (StringUtils.isNotBlank(spielform.getStunden())) {
        int stunden = Integer.parseInt(spielform.getStunden());
        int minuten = 0;
        if (StringUtils.isNotBlank(spielform.getMinuten())) {
          minuten = Integer.parseInt(spielform.getMinuten());
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(datum);
        cal.set(Calendar.HOUR_OF_DAY, stunden);
        cal.set(Calendar.MINUTE, minuten);
        cal.set(Calendar.SECOND, 0);
        datum = cal.getTime();
      }
      spiel.setDatum(datum);
      spiel.setOrt(spielform.getOrt());
      spiel.setGegner(spielform.getGegner());
      spiel.setKampfrichter(spielform.getKampfrichter());
      spiel.setKampfrichterGegner(spielform.getKampfrichterGegner());
      if (StringUtils.isNotBlank(spielform.getTotalNr())) {
        spiel.setTotalNr(new Integer(spielform.getTotalNr()));
      } else {
        spiel.setTotalNr(null);
      }
      if (StringUtils.isNotBlank(spielform.getSchlagPunkteGegner())) {
        spiel.setSchlagPunkteGegner(new Integer(spielform.getSchlagPunkteGegner()));
      } else {
        spiel.setSchlagPunkteGegner(null);
      }
      Art art = (Art)dbSession.get(Art.class, new Integer(spielform.getArtId()));
      spiel.setArt(art);
      spielform.setSpielArt(art.getSpielArt());
      if (spielform.getId() == 0) {

        dbSession.save(spiel);
        spielform.setId(spiel.getId().intValue());
      }
      // ende des abspeichern des spieles

      // anfang erstellen der lieste für die aufstellungs seite spieledit2.jsp
      Criteria criteria = dbSession.createCriteria(Spieler.class);
      criteria.add(Restrictions.eq("club", club));
      criteria.add(Restrictions.eq("aufgestellt", Boolean.TRUE));
      criteria.add(Restrictions.eq("aktiv", Boolean.TRUE));
      criteria.addOrder(Order.asc("reihenfolge"));
      List<Spieler> spielerList = criteria.list();
      request.getSession().setAttribute("aufgestellteSpielerList", spielerList);
      // ende erstellen der lieste für die aufstellungs seite spieledit2.jsp

      // meldung speichern erfolgreich
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("saveOK"));
      saveMessages(request, messages);
      if (request.getParameter("save") != null) {
        return mapping.findForward("save");
      }
      if (spiel.getPunkte() == null || spiel.getPunkte().isEmpty()) {
        return mapping.findForward("weiter");
      } else {
        return mapping.findForward("tabelle");
      }
    } finally {
      tx.commit();
    }
  }
}
