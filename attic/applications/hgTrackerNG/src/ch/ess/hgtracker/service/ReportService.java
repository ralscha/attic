package ch.ess.hgtracker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.model.Durchschnitt;
import ch.ess.hgtracker.model.JahrItem;
import ch.ess.hgtracker.model.Rangpunkte;

@Component
public class ReportService {

  private final static String DURCHSCHNITT_QUERY_STRING = "select spieler.nachname, spieler.vorname, spieler.jahrgang, "
      + "sum(punkte.ries1), sum(punkte.ries2), sum(punkte.ries3), sum(punkte.ries4), "
      + "sum(punkte.ries5), sum(punkte.ries6), sum(punkte.totalStrich) "
      + "from Punkte punkte inner join punkte.spieler spieler "
      + "inner join punkte.spiel spiel "
      + "where (year(spiel.datum)=?) and (spiel.club=?)"
      + "group by spieler.vorname, spieler.nachname, spieler.jahrgang";

  private final static String TOTAL_MS_QUERY_STRING = "select count(spiel.id) from Spiel spiel "
      + "inner join spiel.art art " + "where (art.meisterschaft = 1) AND (YEAR(spiel.datum) = ?) and (spiel.club=?)";
  private final static String TOTAL_MSGESPIELT_QUERY_STRING = TOTAL_MS_QUERY_STRING
      + " AND (spiel.totalNr IS NOT NULL)";
  private final static String RANGPUNKTE_QUERY_STRING = "select spieler.nachname, spieler.vorname, spieler.jahrgang, "
      + "sum(punkte.ries1), sum(punkte.ries2), sum(punkte.ries3), sum(punkte.ries4), "
      + "sum(punkte.ries5), sum(punkte.ries6), sum(punkte.totalStrich), sum(punkte.rangpunkte) "
      + "from Punkte punkte inner join punkte.spieler spieler "
      + "inner join punkte.spiel spiel inner join spiel.art art "
      + "where (year(spiel.datum)=?) and (spiel.club=?) and (art.meisterschaft=1)"
      + "group by spieler.vorname, spieler.nachname, spieler.jahrgang";

  private final static String JAHR_QUERY_STRING = "SELECT distinct year(datum) FROM Spiel spiel where spiel.club=? order by 1 DESC";

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Integer> getJahre(Club club) {
    List<Integer> jahreList = new ArrayList<Integer>();

    Query query = sessionFactory.getCurrentSession().createQuery(JAHR_QUERY_STRING);
    query.setEntity(0, club);
    List<Object> resultatList = query.list();
    for (Object object : resultatList) {
      jahreList.add((Integer)object);
    }

    return jahreList;
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<JahrItem> getJahreWithAll(Club club) {
    List<JahrItem> jahreList = new ArrayList<JahrItem>();
    JahrItem ji = new JahrItem();
    ji.setJahr(0);
    ji.setLabel("Alle Jahre");
    jahreList.add(ji);
    
    Query query = sessionFactory.getCurrentSession().createQuery(JAHR_QUERY_STRING);
    query.setEntity(0, club);
    List<Object> resultatList = query.list();
    for (Object object : resultatList) {
      ji = new JahrItem();
      
      ji.setJahr((Integer)object);
      ji.setLabel(String.valueOf(ji.getJahr()));
      jahreList.add(ji);
    }

    return jahreList;
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Art> getArten(Club club) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Art.class);
    criteria.add(Restrictions.eq("club", club));
    criteria.addOrder(Order.desc("meisterschaft"));
    criteria.addOrder(Order.asc("spielArt"));
    List<Art> arten = criteria.list();
    
    Art emptyArt = new Art();
    emptyArt.setSpielArt("Alle Arten");
    arten.add(0, emptyArt);
    
    return arten;
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Art> getArtenOnly(Club club) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Art.class);  
    criteria.add(Restrictions.eq("club", club));
    criteria.addOrder(Order.asc("spielArt"));
    return criteria.list();    
  }  

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Rangpunkte> getRangpunkte(Club club, Integer inputJahr) {
    Calendar today = new GregorianCalendar();
    int jahr = today.get(Calendar.YEAR);

    if (inputJahr != null) {
      jahr = inputJahr.intValue();
    }
    
    // Total der Meisterschaftspielen herausfinden
    Query query = sessionFactory.getCurrentSession().createQuery(TOTAL_MS_QUERY_STRING);
    query.setInteger(0, jahr);
    query.setEntity(1, club);
    Long totalMS = (Long)query.uniqueResult();

    // herausfinden der gespielten Meisterschaften
    query = sessionFactory.getCurrentSession().createQuery(TOTAL_MSGESPIELT_QUERY_STRING);
    query.setInteger(0, jahr);
    query.setEntity(1, club);
    Long totalMSgespielt = (Long)query.uniqueResult();

    int differenzMS = totalMS.intValue() - totalMSgespielt.intValue();
    int punkteTotal = totalMS.intValue() * club.getPunkteMS().intValue();

    List<Rangpunkte> rangpunkteList = new ArrayList<Rangpunkte>();
    
    // Anfang Query String Rangpunkte
    query = sessionFactory.getCurrentSession().createQuery(RANGPUNKTE_QUERY_STRING);
    query.setInteger(0, jahr);
    query.setEntity(1, club);
    List<Object[]> resultatList = query.list();
    if (!resultatList.isEmpty()) {
      Iterator<Object[]> it = resultatList.iterator();
      
      while (it.hasNext()) {
        Object[] row = it.next();
        Rangpunkte rangpunkte = new Rangpunkte();
        rangpunkte.setNachname((String)row[0]);
        rangpunkte.setVorname((String)row[1]);
        rangpunkte.setJahrgang((Integer)row[2]);
        
        rangpunkte.setChartLabel(rangpunkte.getNachname() + " " + rangpunkte.getVorname());
        
        Long ries1 = (Long)row[3];
        Long ries2 = (Long)row[4];
        Long ries3 = (Long)row[5];
        Long ries4 = (Long)row[6];
        Long ries5 = (Long)row[7];
        Long ries6 = (Long)row[8];
        Long totalStrich = (Long)row[9];
        int totalRies = 0;
        if (ries1 != null) {
          totalRies = totalRies + ries1.intValue();
        }
        if (ries2 != null) {
          totalRies = totalRies + ries2.intValue();
        }
        if (ries3 != null) {
          totalRies = totalRies + ries3.intValue();
        }
        if (ries4 != null) {
          totalRies = totalRies + ries4.intValue();
        }
        if (ries5 != null) {
          totalRies = totalRies + ries5.intValue();
        }
        if (ries6 != null) {
          totalRies = totalRies + ries6.intValue();
        }
        rangpunkte.setPunkte(new Integer(totalRies));
        
        if (totalStrich != null) {
          rangpunkte.setStriche(totalStrich.intValue());
        } else {
          rangpunkte.setStriche(0);
        }
        
        BigDecimal schnitt = new BigDecimal(totalRies);
        
        if (totalStrich != null) {
          schnitt = schnitt.divide(new BigDecimal(totalStrich.intValue()), 2, BigDecimal.ROUND_HALF_UP);
        }
        
        rangpunkte.setSchnitt(schnitt);
        Long rangpunkteInt = (Long)row[10];
        if (rangpunkteInt != null) {
          rangpunkte.setRangpunkte(rangpunkteInt.intValue());
        } else {
          rangpunkte.setRangpunkte(0);
        }

        if (differenzMS != 0) {
          rangpunkte.setPunkteProSpiel(new Integer((punkteTotal - totalRies) / differenzMS));
        } else {
          rangpunkte.setPunkteProSpiel(new Integer((punkteTotal - totalRies)));
        }

        rangpunkteList.add(rangpunkte);
      }

    }
    
    return rangpunkteList;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Durchschnitt> getDurchschnitt(Club club, Integer inputJahr) {

    Calendar today = new GregorianCalendar();
    int jahr = today.get(Calendar.YEAR);

    if (inputJahr != null) {
      jahr = inputJahr.intValue();
    }

    List<Durchschnitt> durchschnitteList = new ArrayList<Durchschnitt>();

    Query query = sessionFactory.getCurrentSession().createQuery(DURCHSCHNITT_QUERY_STRING);
    query.setInteger(0, jahr);
    query.setEntity(1, club);
    List<Object[]> resultatList = query.list();
    if (!resultatList.isEmpty()) {
      Iterator<Object[]> it = resultatList.iterator();

      while (it.hasNext()) {
        Object[] row = it.next();
        Durchschnitt durchschnitt = new Durchschnitt();
        durchschnitt.setNachname((String)row[0]);
        durchschnitt.setVorname((String)row[1]);
        durchschnitt.setJahrgang((Integer)row[2]);
        Long ries1 = (Long)row[3];
        Long ries2 = (Long)row[4];
        Long ries3 = (Long)row[5];
        Long ries4 = (Long)row[6];
        Long ries5 = (Long)row[7];
        Long ries6 = (Long)row[8];
        Long totalStrich = (Long)row[9];
        int totalRies = 0;
        if (ries1 != null) {
          totalRies = totalRies + ries1.intValue();
        }
        if (ries2 != null) {
          totalRies = totalRies + ries2.intValue();
        }
        if (ries3 != null) {
          totalRies = totalRies + ries3.intValue();
        }
        if (ries4 != null) {
          totalRies = totalRies + ries4.intValue();
        }
        if (ries5 != null) {
          totalRies = totalRies + ries5.intValue();
        }
        if (ries6 != null) {
          totalRies = totalRies + ries6.intValue();
        }
        durchschnitt.setPunkte(totalRies);
        if (totalStrich != null) {
          durchschnitt.setStriche(totalStrich.intValue());
        } else {
          durchschnitt.setStriche(null);
        }
        BigDecimal schnitt = new BigDecimal(totalRies);
        if (totalStrich != null && totalStrich.intValue() != 0) {
          schnitt = schnitt.divide(new BigDecimal(totalStrich), 2, BigDecimal.ROUND_HALF_UP);
        }
        durchschnitt.setSchnitt(schnitt);
        durchschnitteList.add(durchschnitt);
      }

    }

    return durchschnitteList;

  }

}
