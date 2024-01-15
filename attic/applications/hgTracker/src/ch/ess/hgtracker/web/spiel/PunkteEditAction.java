package ch.ess.hgtracker.web.spiel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spiel;

public class PunkteEditAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      Spiel spiel;
      String spielId = request.getParameter("spielId");
      boolean web = false;
      SpielForm spielform = (SpielForm)form;
      if (StringUtils.isNotBlank(spielId)) {
        spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielId));
        web = true;
        spielform.setSpielArt(spiel.getArt().getSpielArt());
        spielform.setGegner(spiel.getGegner());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        spielform.setDatum(sdf.format(spiel.getDatum()));
        spielform.setKampfrichter(spiel.getKampfrichter());
        spielform.setKampfrichterGegner(spiel.getKampfrichterGegner());
        spielform.setTotalNr(spiel.getTotalNrGegner().toString());
        spielform.setTotalNrHeim(spiel.getTotalNr().toString());
        spielform.setSchlagPunkteGegner(spiel.getSchlagPunkteGegner().toString());
      } else {
        spiel = (Spiel)dbSession.get(Spiel.class, new Integer(spielform.getId()));
      }
      // anfang querry spieler in punkte
      Criteria criteria = dbSession.createCriteria(Punkte.class);
      criteria.add(Restrictions.eq("spiel", spiel));
      criteria.addOrder(Order.asc("reihenfolge"));
      List<Punkte> punkteList = criteria.list();
      // ende querry spieler in punkte

      List<PunkteAnzeige> punkteAnzeigeList = new ArrayList<PunkteAnzeige>();
      List<PunkteAnzeige> punkteUeberzaehligAnzeigeList = new ArrayList<PunkteAnzeige>();
      Iterator<Punkte> it = punkteList.iterator();
      int total1 = 0;
      int total2 = 0;
      int total3 = 0;
      int total4 = 0;
      int total5 = 0;
      int total6 = 0;
      int grossesTotal = 0;
      while (it.hasNext()) {
        Punkte punkte = it.next();
        PunkteAnzeige paz = new PunkteAnzeige();
        boolean ueberzaehlig = false;
        if (punkte.getUeberzaehligeSpieler() != null) {
          ueberzaehlig = punkte.getUeberzaehligeSpieler().booleanValue();
        }
        if (punkte.getRies1() != null) {
          paz.setRies1(punkte.getRies1().toString());
          if (!ueberzaehlig) {
            total1 = total1 + punkte.getRies1().intValue();
          }
        } else {
          paz.setRies1("");
        }
        if (punkte.getRies2() != null) {
          paz.setRies2(punkte.getRies2().toString());
          if (!ueberzaehlig) {
            total2 = total2 + punkte.getRies2().intValue();
          }
        } else {
          paz.setRies2("");
        }
        if (punkte.getRies3() != null) {
          paz.setRies3(punkte.getRies3().toString());
          if (!ueberzaehlig) {
            total3 = total3 + punkte.getRies3().intValue();
          }
        } else {
          paz.setRies3("");
        }
        if (punkte.getRies4() != null) {
          paz.setRies4(punkte.getRies4().toString());
          if (!ueberzaehlig) {
            total4 = total4 + punkte.getRies4().intValue();
          }
        } else {
          paz.setRies4("");
        }
        if (punkte.getRies5() != null) {
          paz.setRies5(punkte.getRies5().toString());
          if (!ueberzaehlig) {
            total5 = total5 + punkte.getRies5().intValue();
          }
        } else {
          paz.setRies5("");
        }
        if (punkte.getRies6() != null) {
          paz.setRies6(punkte.getRies6().toString());
          if (!ueberzaehlig) {
            total6 = total6 + punkte.getRies6().intValue();
          }
        } else {
          paz.setRies6("");
        }
        paz.setNr1(punkte.getNr1());
        paz.setNr2(punkte.getNr2());
        paz.setNr3(punkte.getNr3());
        paz.setNr4(punkte.getNr4());
        paz.setNr5(punkte.getNr5());
        paz.setNr6(punkte.getNr6());
        paz.setNachname(punkte.getSpieler().getNachname());
        paz.setVorname(punkte.getSpieler().getVorname());
        paz.setJahrgang(punkte.getSpieler().getJahrgang().toString());
        paz.setReihenfolge(punkte.getReihenfolge().toString());
        paz.setId(punkte.getId().intValue());
        if (punkte.getRangpunkte() != null) {
          paz.setRangpunkte(punkte.getRangpunkte().toString());
        } else {
          paz.setRangpunkte(null);
        }
        int total = 0;
        if (punkte.getRies1() != null) {
          total = total + punkte.getRies1().intValue();
        }
        if (punkte.getRies2() != null) {
          total = total + punkte.getRies2().intValue();
        }
        if (punkte.getRies3() != null) {
          total = total + punkte.getRies3().intValue();
        }
        if (punkte.getRies4() != null) {
          total = total + punkte.getRies4().intValue();
        }
        if (punkte.getRies5() != null) {
          total = total + punkte.getRies5().intValue();
        }
        if (punkte.getRies6() != null) {
          total = total + punkte.getRies6().intValue();
        }
        paz.setTotal(total);
        if (punkte.getUeberzaehligeSpieler().booleanValue()) {
          punkteUeberzaehligAnzeigeList.add(paz);
        } else {
          punkteAnzeigeList.add(paz);
        }
      }
      grossesTotal = total1 + total2 + total3 + total4 + total5 + total6;
      spiel.setSchlagPunkte(new Integer(grossesTotal));
      Totale totalObj = new Totale();
      totalObj.setTotal(String.valueOf(grossesTotal));
      totalObj.setTotal1(String.valueOf(total1));
      totalObj.setTotal2(String.valueOf(total2));
      totalObj.setTotal3(String.valueOf(total3));
      totalObj.setTotal4(String.valueOf(total4));
      totalObj.setTotal5(String.valueOf(total5));
      totalObj.setTotal6(String.valueOf(total6));
      request.getSession().setAttribute("meisterschaft", spiel.getArt().getMeisterschaft());
      request.getSession().setAttribute("total", totalObj);
      request.getSession().setAttribute("punkteAnzeigeList", punkteAnzeigeList);
      request.getSession().setAttribute("punkteUeberzaehligAnzeigeList", punkteUeberzaehligAnzeigeList);
      if (web) {
        return mapping.findForward("punkteWeb");
      } else {
        return mapping.getInputForward();
      }
    } finally {
      tx.commit();
    }
  }
}
