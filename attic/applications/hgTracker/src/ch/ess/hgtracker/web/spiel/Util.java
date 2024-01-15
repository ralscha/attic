package ch.ess.hgtracker.web.spiel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.web.HibernateFilter;

public class Util {

  @SuppressWarnings("unchecked")
  public static List getJahrList() {
    Session dbSession = HibernateFilter.sf.openSession();
    Query query = dbSession.createQuery("SELECT DISTINCT YEAR(datum) FROM Spiel ORDER BY 1 DESC");
    List jahr = query.list();
    Iterator it = jahr.iterator();
    List<LabelValueBean> jahrOption = new ArrayList<LabelValueBean>();
    while (it.hasNext()) {
      Integer jahrInt = (Integer)it.next();
      LabelValueBean lvb = new LabelValueBean(jahrInt.toString(), jahrInt.toString());
      jahrOption.add(lvb);
    }
    dbSession.close();
    return (jahrOption);
  }

  public static List<LabelValueBean> getArtList() {
    Session dbSession = HibernateFilter.sf.openSession();
    List<LabelValueBean> artList = getArtList(dbSession);
    dbSession.close();
    return (artList);
  }

  @SuppressWarnings("unchecked")
  public static List<LabelValueBean> getArtList(Session dbSession) {
    //datenabfüllen für art dropdown
    Criteria criteria = dbSession.createCriteria(Art.class);
    criteria.addOrder(Order.asc("spielArt"));
    List<Art> artList = criteria.list();
    Iterator<Art> iter = artList.iterator();
    List<LabelValueBean> artOption = new ArrayList<LabelValueBean>();
    while (iter.hasNext()) {
      Art element = iter.next();
      LabelValueBean lvb = new LabelValueBean(element.getSpielArt(), element.getId().toString());
      artOption.add(lvb);
    }
    return (artOption);
    // ende
  }
}