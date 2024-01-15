package ch.ess.timetracker.web;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;
import ch.ess.timetracker.db.Customer;
import ch.ess.timetracker.db.User;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:19 $ 
  */
public class CustomerOptions extends Options {

  public void init() throws HibernateException {

    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      Iterator resultIt;
            
      if (getRequest().isUserInRole("admin")) {
        List resultList = HibernateSession.currentSession().find("from Customer");
        resultIt = resultList.iterator();
      } else {
        User user = WebUtils.getUser(getRequest());
        Set customers = user.getCustomers();
        resultIt = customers.iterator();
      }
            
      while (resultIt.hasNext()) {
        Customer c = (Customer)resultIt.next();
        getLabelValue().add(new LabelValueBean(c.getName(), c.getId().toString()));
      }
      
      sort();
      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }
  }

}
