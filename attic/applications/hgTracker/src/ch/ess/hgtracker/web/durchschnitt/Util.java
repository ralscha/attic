package ch.ess.hgtracker.web.durchschnitt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;
import ch.ess.hgtracker.web.HibernateFilter;

public class Util {

  @SuppressWarnings("unchecked")
  public static List<LabelValueBean> getJahrList() {
    Session dbSession = HibernateFilter.sf.openSession();
    Query query = dbSession.createQuery("SELECT DISTINCT YEAR(datum) FROM Spiel ORDER BY 1 DESC");
    List<Integer> jahr = query.list();
    Iterator<Integer> it = jahr.iterator();
    List<LabelValueBean> jahrOption = new ArrayList<LabelValueBean>();
    while (it.hasNext()) {
      Integer jahrInt = it.next();
      LabelValueBean lvb = new LabelValueBean(jahrInt.toString(), jahrInt.toString());
      jahrOption.add(lvb);
    }
    dbSession.close();
    return (jahrOption);
  }

}