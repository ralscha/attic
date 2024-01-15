package ch.ess.hgtracker.web.spiel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spiel;

public class SpielListAction extends Action {

  
  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      String webLogin = request.getParameter("webLogin");
      String showAllWeb = request.getParameter("all");
      Club club;
      boolean web = false;
      if (StringUtils.isNotBlank(webLogin)) {
        Criteria criteria = dbSession.createCriteria(Club.class);
        criteria.add(Restrictions.eq("webLogin", webLogin));
        club = (Club)criteria.uniqueResult();
        web = true;
      } else {
        club = (Club)request.getSession().getAttribute("club");
      }
      Criteria criteria = dbSession.createCriteria(Spiel.class);
      criteria.add(Restrictions.eq("club", club));
      criteria.addOrder(Order.desc("datum"));
      SpielListForm slf = (SpielListForm)form;
      if (StringUtils.isNotBlank(slf.getJahr())) {
        Calendar start = new GregorianCalendar(Integer.parseInt(slf.getJahr()), Calendar.JANUARY, 1);
        Calendar ende = new GregorianCalendar(Integer.parseInt(slf.getJahr()), Calendar.DECEMBER, 31);
        criteria.add(Restrictions.ge("datum", start.getTime()));
        criteria.add(Restrictions.le("datum", ende.getTime()));
      }
      if (StringUtils.isNotBlank(slf.getArtIdSuche())) {
        criteria.add(Restrictions.eq("art.id", new Integer(slf.getArtIdSuche())));
      }
      if ((web) && (showAllWeb == null)) {
        criteria.add(Restrictions.isNotNull("totalNr"));
      }
      List<Spiel> spielList = criteria.list();
      List<SpielAnzeige> spielAnzeigeList = new ArrayList<SpielAnzeige>();
      if (spielList.isEmpty()) {
        request.getSession().removeAttribute("spielList");
      } else {
        Iterator<Spiel> it = spielList.iterator();
        while (it.hasNext()) {
          Spiel spiel = it.next();
          SpielAnzeige az = new SpielAnzeige();
          az.setArt(spiel.getArt().getSpielArt());
          az.setDatum(spiel.getDatum());
          Calendar cal = new GregorianCalendar();
          cal.setTime(spiel.getDatum());
          int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
          DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
          String[] weekdays = dfs.getWeekdays();
          az.setWochentag(weekdays[dayOfWeek]);
          az.setGegner(spiel.getGegner());
          az.setSchlagPunkteGegner(spiel.getSchlagPunkteGegner());
          az.setTotalNr(spiel.getTotalNr());
          az.setSchlagPunkte(spiel.getSchlagPunkte());
          az.setTotalNrGegner(spiel.getTotalNrGegner());
          az.setOrt(spiel.getOrt());
          az.setId(spiel.getId());
          spielAnzeigeList.add(az);
        }
        request.getSession().setAttribute("spielList", spielAnzeigeList);
      }
      if (web) {
        if (showAllWeb != null) {
          return mapping.findForward("spielplan");
        }
        return mapping.findForward("web");
      } else {
        return mapping.getInputForward();
      }
    } finally {
      tx.commit();
    }
  }
}
