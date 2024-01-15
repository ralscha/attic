package ch.ess.cal.admin.web;

import java.util.*;

import net.sf.hibernate.*;

import org.apache.commons.logging.*;
import org.apache.struts.util.*;

import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;

public class DepartmentOptions extends Options {

  private static final Log logger = LogFactory.getLog(DepartmentOptions.class);

  public DepartmentOptions(Session sess, Locale locale, MessageResources messages) {
    super(sess, locale, messages);
    init();
  }

  public void init() {
    Transaction tx = null;
    try {
      tx = getSession().beginTransaction();
      
      List resultList = getSession().find("from Department as d order by d.name asc");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Department d = (Department)it.next();
        getLabelValue().add(new LabelValueBean(d.getName(), d.getDepartmentId().toString()));  
      }      
      
      tx.commit();

    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      logger.error("init", e);
    }
  }

}
