package ch.ess.hgtracker.service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Punkte;
import ch.ess.hgtracker.db.Spiel;
import ch.ess.hgtracker.db.Spieler;
import ch.ess.hgtracker.model.Aufstellung;
import ch.ess.hgtracker.model.JahrItem;
import ch.ess.hgtracker.model.PunkteAnzeige;
import ch.ess.hgtracker.model.PunkteResult;
import ch.ess.hgtracker.model.SpielAnzeige;

@Component
public class SpielService {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Spiel> getAll(Club club, JahrItem jahrItem, Art art) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spiel.class);
    criteria.add(Restrictions.eq("club", club));
    criteria.addOrder(Order.desc("datum"));

    if (jahrItem != null && jahrItem.getJahr() > 0) {
      Calendar start = new GregorianCalendar(jahrItem.getJahr(), Calendar.JANUARY, 1);
      Calendar ende = new GregorianCalendar(jahrItem.getJahr(), Calendar.DECEMBER, 31);
      criteria.add(Restrictions.ge("datum", start.getTime()));
      criteria.add(Restrictions.le("datum", ende.getTime()));
    }
    if (art != null && art.getId() != null && art.getId() > 0) {
      criteria.add(Restrictions.eq("art", art));
    }

    DateFormatSymbols dfs = new DateFormatSymbols(Locale.GERMAN);
    List<Spiel> spielList = criteria.list();

    for (Spiel spiel : spielList) {

      Calendar cal = new GregorianCalendar();
      cal.setTime(spiel.getDatum());

      int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

      String[] weekdays = dfs.getWeekdays();
      spiel.setWochentag(weekdays[dayOfWeek]);

      spiel.setArtName(spiel.getArt().getSpielArt());

      spiel.setHasAufstellung(spiel.getPunkte().size() > 0);
    }

    return spielList;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<SpielAnzeige> getSpielAnzeige(Club club, Integer jahr, Integer artId) {

    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spiel.class);
    criteria.add(Restrictions.eq("club", club));
    criteria.addOrder(Order.desc("datum"));

    if (jahr != null && jahr > 0) {
      Calendar start = new GregorianCalendar(jahr, Calendar.JANUARY, 1);
      Calendar ende = new GregorianCalendar(jahr, Calendar.DECEMBER, 31);
      criteria.add(Restrictions.ge("datum", start.getTime()));
      criteria.add(Restrictions.le("datum", ende.getTime()));
    }
    if (artId != null && artId > 0) {
      criteria.add(Restrictions.eq("art.id", artId));
    }

    criteria.add(Restrictions.isNotNull("totalNr"));

    List<Spiel> spielList = criteria.list();
    List<SpielAnzeige> spielAnzeigeList = new ArrayList<SpielAnzeige>();

    for (Spiel spiel : spielList) {
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
    
    return spielAnzeigeList;

  }

  @Transactional(readOnly = true)
  public Spiel getSpiel(int spielId) {
    Spiel spiel = (Spiel)sessionFactory.getCurrentSession().load(Spiel.class, spielId);
    spiel.getArt();
    return spiel;
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public PunkteResult getPunkte(Spiel spiel) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Punkte.class);
    criteria.add(Restrictions.eq("spiel", spiel));
    criteria.addOrder(Order.asc("reihenfolge"));
    List<Punkte> punkte = criteria.list();

    int total1 = 0;
    int total2 = 0;
    int total3 = 0;
    int total4 = 0;
    int total5 = 0;
    int total6 = 0;
    int grossesTotal = 0;

    List<PunkteAnzeige> punkteAnzeigeList = new ArrayList<PunkteAnzeige>();
    List<PunkteAnzeige> punkteUeberzaehligAnzeigeList = new ArrayList<PunkteAnzeige>();

    for (Punkte punkt : punkte) {

      PunkteAnzeige paz = new PunkteAnzeige();
      boolean ueberzaehlig = false;
      if (punkt.getUeberzaehligeSpieler() != null) {
        ueberzaehlig = punkt.getUeberzaehligeSpieler().booleanValue();
      }
      if (punkt.getRies1() != null) {
        paz.setRies1(punkt.getRies1().toString());
        if (!ueberzaehlig) {
          total1 = total1 + punkt.getRies1().intValue();
        }
      } else {
        paz.setRies1(null);
      }
      if (punkt.getRies2() != null) {
        paz.setRies2(punkt.getRies2().toString());
        if (!ueberzaehlig) {
          total2 = total2 + punkt.getRies2().intValue();
        }
      } else {
        paz.setRies2(null);
      }
      if (punkt.getRies3() != null) {
        paz.setRies3(punkt.getRies3().toString());
        if (!ueberzaehlig) {
          total3 = total3 + punkt.getRies3().intValue();
        }
      } else {
        paz.setRies3(null);
      }
      if (punkt.getRies4() != null) {
        paz.setRies4(punkt.getRies4().toString());
        if (!ueberzaehlig) {
          total4 = total4 + punkt.getRies4().intValue();
        }
      } else {
        paz.setRies4(null);
      }
      if (punkt.getRies5() != null) {
        paz.setRies5(punkt.getRies5().toString());
        if (!ueberzaehlig) {
          total5 = total5 + punkt.getRies5().intValue();
        }
      } else {
        paz.setRies5(null);
      }
      if (punkt.getRies6() != null) {
        paz.setRies6(punkt.getRies6().toString());
        if (!ueberzaehlig) {
          total6 = total6 + punkt.getRies6().intValue();
        }
      } else {
        paz.setRies6(null);
      }
      paz.setNr1(punkt.getNr1());
      paz.setNr2(punkt.getNr2());
      paz.setNr3(punkt.getNr3());
      paz.setNr4(punkt.getNr4());
      paz.setNr5(punkt.getNr5());
      paz.setNr6(punkt.getNr6());
      paz.setNachname(punkt.getSpieler().getNachname());
      paz.setVorname(punkt.getSpieler().getVorname());
      paz.setJahrgang(punkt.getSpieler().getJahrgang());
      paz.setReihenfolge(punkt.getReihenfolge());
      paz.setId(punkt.getId());

      paz.setRangpunkte(punkt.getRangpunkte());

      int total = 0;
      if (punkt.getRies1() != null) {
        total = total + punkt.getRies1().intValue();
      }
      if (punkt.getRies2() != null) {
        total = total + punkt.getRies2().intValue();
      }
      if (punkt.getRies3() != null) {
        total = total + punkt.getRies3().intValue();
      }
      if (punkt.getRies4() != null) {
        total = total + punkt.getRies4().intValue();
      }
      if (punkt.getRies5() != null) {
        total = total + punkt.getRies5().intValue();
      }
      if (punkt.getRies6() != null) {
        total = total + punkt.getRies6().intValue();
      }
      paz.setTotal(total);

      if (punkt.getUeberzaehligeSpieler()) {
        punkteUeberzaehligAnzeigeList.add(paz);
      } else {
        punkteAnzeigeList.add(paz);
      }

    }
    grossesTotal = total1 + total2 + total3 + total4 + total5 + total6;

    PunkteResult result = new PunkteResult();
    result.setPunkteAnzeige(punkteAnzeigeList);
    result.setUeberzaehligePunkteAnzeige(punkteUeberzaehligAnzeigeList);

    result.setTotal(grossesTotal);
    result.setTotal1(total1);
    result.setTotal2(total2);
    result.setTotal3(total3);
    result.setTotal4(total4);
    result.setTotal5(total5);
    result.setTotal6(total6);

    result.setMeisterschaft(spiel.getArt().getMeisterschaft());

    return result;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Aufstellung> getAufgestellte(Club club, Spiel spiel) {

    List<Aufstellung> aufstellungList = new ArrayList<Aufstellung>();

    sessionFactory.getCurrentSession().refresh(spiel);

    if (spiel.getPunkte().isEmpty()) {

      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spieler.class);
      criteria.add(Restrictions.eq("club", club));
      criteria.add(Restrictions.eq("aufgestellt", Boolean.TRUE));
      criteria.add(Restrictions.eq("aktiv", Boolean.TRUE));
      //criteria.addOrder(Order.asc("reihenfolge"));
      List<Spieler> spielerList = criteria.list();

      for (Spieler spieler : spielerList) {
        Aufstellung auf = new Aufstellung();
        auf.setSpielerId(spieler.getId());
        auf.setJahrgang(spieler.getJahrgang());
        auf.setNachname(spieler.getNachname());
        auf.setVorname(spieler.getVorname());
        auf.setUeberzaehlig(false);
        auf.setPunktId(null);
        if (spieler.getReihenfolge() != null) {
          auf.setReihenfolge(spieler.getReihenfolge());
        } else {
          auf.setReihenfolge(999999);
        }
        aufstellungList.add(auf);
      }
    } else {
      Set<Punkte> punkte = spiel.getPunkte();
      Set<Integer> spielerIds = new HashSet<Integer>();

      for (Punkte punkt : punkte) {

        Aufstellung auf = new Aufstellung();
        Spieler spieler = punkt.getSpieler();
        spielerIds.add(spieler.getId());
        auf.setSpielerId(spieler.getId());
        auf.setJahrgang(spieler.getJahrgang());
        auf.setNachname(spieler.getNachname());
        auf.setVorname(spieler.getVorname());
        auf.setUeberzaehlig(punkt.getUeberzaehligeSpieler());
        auf.setPunktId(punkt.getId());

        if (punkt.getReihenfolge() != null) {
          auf.setReihenfolge(punkt.getReihenfolge());
        } else {
          auf.setReihenfolge(999999);
        }
        aufstellungList.add(auf);
      }

      //Restliche Spieler auflisten
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Spieler.class);
      criteria.add(Restrictions.eq("club", club));
      criteria.add(Restrictions.eq("aufgestellt", Boolean.TRUE));
      criteria.add(Restrictions.eq("aktiv", Boolean.TRUE));
      criteria.add(Restrictions.not(Restrictions.in("id", spielerIds)));

      List<Spieler> spielerList = criteria.list();

      for (Spieler spieler : spielerList) {
        Aufstellung auf = new Aufstellung();
        auf.setSpielerId(spieler.getId());
        auf.setJahrgang(spieler.getJahrgang());
        auf.setNachname(spieler.getNachname());
        auf.setVorname(spieler.getVorname());
        auf.setUeberzaehlig(false);
        auf.setPunktId(null);
        auf.setReihenfolge(999999);
        aufstellungList.add(auf);
      }

    }

    Collections.sort(aufstellungList);
    return aufstellungList;
  }

//  private Integer getInteger(Map<String, Object> map, String propName) {
//
//    Object o = map.get(propName);
//    if (o == null) {
//      return null;
//    }
//
//    if (o instanceof String) {
//      String value = (String)o;
//      if (value.trim().equals("")) {
//        return null;
//      }
//      return new Integer(value);
//    }
//
//    return (Integer)o;
//
//  }

  
  private Integer getInteger(String value) {
    if (StringUtils.isNotBlank(value) && StringUtils.isNumeric(value)) {
      return new Integer(value);
    }
    return null;
  }
  
  @Transactional
  public void savePunkte(Spiel spiel, List<PunkteAnzeige> punkteList, List<PunkteAnzeige> ueberzaehligeList) {

    int totalNr = 0;
    int totalSchlag = 0;

    for (PunkteAnzeige pa : punkteList) {
      int punkteId = pa.getId();

      int anzahlStriche = 0;

      Integer ries1 = getInteger(pa.getRies1());
      Integer ries2 = getInteger(pa.getRies2());
      Integer ries3 = getInteger(pa.getRies3());
      Integer ries4 = getInteger(pa.getRies4());
      Integer ries5 = getInteger(pa.getRies5());
      Integer ries6 = getInteger(pa.getRies6());

      if (ries1 != null) {
        anzahlStriche++;
        totalSchlag += ries1;
      }
      if (ries2 != null) {
        anzahlStriche++;
        totalSchlag += ries2;
      }
      if (ries3 != null) {
        anzahlStriche++;
        totalSchlag += ries3;
      }
      if (ries4 != null) {
        anzahlStriche++;
        totalSchlag += ries4;
      }
      if (ries5 != null) {
        anzahlStriche++;
        totalSchlag += ries5;
      }
      if (ries6 != null) {
        anzahlStriche++;
        totalSchlag += ries6;
      }

      Boolean nr1 = pa.getNr1();
      Boolean nr2 = pa.getNr2();
      Boolean nr3 = pa.getNr3();
      Boolean nr4 = pa.getNr4();
      Boolean nr5 = pa.getNr5();
      Boolean nr6 = pa.getNr6();

      if (nr1 != null && nr1.booleanValue()) {
        totalNr++;
      }
      if (nr2 != null && nr2.booleanValue()) {
        totalNr++;
      }
      if (nr3 != null && nr3.booleanValue()) {
        totalNr++;
      }
      if (nr4 != null && nr4.booleanValue()) {
        totalNr++;
      }
      if (nr5 != null && nr5.booleanValue()) {
        totalNr++;
      }
      if (nr6 != null && nr6.booleanValue()) {
        totalNr++;
      }

      Punkte punkt = (Punkte)sessionFactory.getCurrentSession().load(Punkte.class, punkteId);
      punkt.setRies1(ries1);
      punkt.setRies2(ries2);
      punkt.setRies3(ries3);
      punkt.setRies4(ries4);
      punkt.setRies5(ries5);
      punkt.setRies6(ries6);

      punkt.setNr1(nr1);
      punkt.setNr2(nr2);
      punkt.setNr3(nr3);
      punkt.setNr4(nr4);
      punkt.setNr5(nr5);
      punkt.setNr6(nr6);

      punkt.setTotalStrich(anzahlStriche);

      punkt.setRangpunkte(pa.getRangpunkte());

    }

    spiel.setSchlagPunkte(totalSchlag);
    spiel.setTotalNrGegner(totalNr);
    sessionFactory.getCurrentSession().merge(spiel);

    for (PunkteAnzeige pa : ueberzaehligeList) {
      int punkteId = pa.getId();

      int anzahlStriche = 0;

      Integer ries1 = getInteger(pa.getRies1());
      Integer ries2 = getInteger(pa.getRies2());
      Integer ries3 = getInteger(pa.getRies3());
      Integer ries4 = getInteger(pa.getRies4());
      Integer ries5 = getInteger(pa.getRies5());
      Integer ries6 = getInteger(pa.getRies6());

      if (ries1 != null) {
        anzahlStriche++;
      }
      if (ries2 != null) {
        anzahlStriche++;
      }
      if (ries3 != null) {
        anzahlStriche++;
      }
      if (ries4 != null) {
        anzahlStriche++;
      }
      if (ries5 != null) {
        anzahlStriche++;
      }
      if (ries6 != null) {
        anzahlStriche++;
      }

      Punkte punkt = (Punkte)sessionFactory.getCurrentSession().load(Punkte.class, punkteId);
      punkt.setRies1(ries1);
      punkt.setRies2(ries2);
      punkt.setRies3(ries3);
      punkt.setRies4(ries4);
      punkt.setRies5(ries5);
      punkt.setRies6(ries6);

      punkt.setNr1(false);
      punkt.setNr2(false);
      punkt.setNr3(false);
      punkt.setNr4(false);
      punkt.setNr5(false);
      punkt.setNr6(false);

      punkt.setTotalStrich(anzahlStriche);

      punkt.setRangpunkte(pa.getRangpunkte());
    }

  }

  @Transactional
  public boolean saveAufstellung(Spiel spiel, List<Map<String, Object>> aufstellungList) {
    boolean wasNewInsert = true;

    for (Map<String, Object> aufstellungObj : aufstellungList) {

      Object reihenfolgeObj = aufstellungObj.get("reihenfolge");

      Integer reihenfolge;
      if (reihenfolgeObj instanceof String) {
        String value = (String)reihenfolgeObj;
        if (value.trim().equals("")) {
          reihenfolge = 999999;
        } else {
          reihenfolge = new Integer(reihenfolgeObj.toString());
        }
      } else {
        reihenfolge = (Integer)reihenfolgeObj;
      }

      Integer punktId = (Integer)aufstellungObj.get("punktId");
      boolean ueberzaehlung = (Boolean)aufstellungObj.get("ueberzaehlig");

      if (punktId != null) {
        //Bestehender Eintrag

        Punkte punkt = (Punkte)sessionFactory.getCurrentSession().load(Punkte.class, punktId);

        if (reihenfolge != null && reihenfolge != 999999) {
          //update

          punkt.setUeberzaehligeSpieler(ueberzaehlung);
          punkt.setReihenfolge(reihenfolge);
          //sessionFactory.getCurrentSession().save(punkt);

        } else {
          //delete punkte
          sessionFactory.getCurrentSession().delete(punkt);
        }

        wasNewInsert = false;

      } else {

        if (reihenfolge != null && reihenfolge != 999999) {
          //Neuer Eintrag
          Punkte punkt = new Punkte();
          punkt.setSpiel(spiel);

          Integer spielerId = (Integer)aufstellungObj.get("spielerId");
          Spieler spieler = (Spieler)sessionFactory.getCurrentSession().load(Spieler.class, spielerId);

          punkt.setSpieler(spieler);
          punkt.setUeberzaehligeSpieler(ueberzaehlung);
          punkt.setReihenfolge(reihenfolge);
          sessionFactory.getCurrentSession().save(punkt);
        }

      }
    }

    return wasNewInsert;
  }

  @Transactional
  public void deleteSpiel(int id) {
    Spiel Spiel = (Spiel)sessionFactory.getCurrentSession().load(Spiel.class, id);
    sessionFactory.getCurrentSession().delete(Spiel);
  }

  @Transactional
  public void update(Spiel spiel) {
    sessionFactory.getCurrentSession().merge(spiel);
  }

  @Transactional
  public void insert(Spiel spiel) {
    update(spiel);
  }

}
