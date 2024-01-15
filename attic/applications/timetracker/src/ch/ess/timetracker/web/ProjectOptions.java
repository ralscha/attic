package ch.ess.timetracker.web;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;
import ch.ess.timetracker.db.Project;
import ch.ess.timetracker.db.User;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $ 
  */
public class ProjectOptions extends Options {

  public void init() throws HibernateException {
    
    Long customerId = (Long)getRequest().getSession().getAttribute("customerId");
    
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList;
      
      if (customerId == null) {      
        resultList = HibernateSession.currentSession().find("from Project as p order by p.name asc");
                
        if (!getRequest().isUserInRole("admin")) {
          User user = WebUtils.getUser(getRequest());
          ListIterator li = resultList.listIterator();
          while (li.hasNext()) {
            Project p = (Project) li.next();
            if (!user.getCustomers().contains(p.getCustomer())) {
              li.remove();
            }
            
          }
        } 
        
        
      } else {
        Criteria crit = HibernateSession.currentSession().createCriteria(Project.class);
        crit.createCriteria("customer").add(Expression.eq("id", customerId));
        crit.addOrder(Order.asc("name"));
        resultList = crit.list();                
      }
      
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Project p = (Project)it.next();
        getLabelValue().add(new LabelValueBean(p.getName(), p.getId().toString()));
      }
      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }
  }

}
