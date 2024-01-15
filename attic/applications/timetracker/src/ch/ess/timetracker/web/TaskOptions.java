package ch.ess.timetracker.web;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.struts.util.LabelValueBean;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;
import ch.ess.timetracker.db.Task;
import ch.ess.timetracker.db.User;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:20 $ 
  */
public class TaskOptions extends Options {

  public void init() throws HibernateException {

    Long projectId = (Long)getRequest().getSession().getAttribute("projectId");
    Long customerId = (Long)getRequest().getSession().getAttribute("customerId");
    
    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      List resultList;
              
      if (projectId != null) {
        Criteria crit = HibernateSession.currentSession().createCriteria(Task.class);
        crit.createCriteria("project").add(Expression.eq("id", projectId));
        crit.addOrder(Order.asc("name"));
        resultList = crit.list();                
      } else if (customerId != null) {
        Criteria crit = HibernateSession.currentSession().createCriteria(Task.class);
        crit.createCriteria("project").createCriteria("customer").add(Expression.eq("id", customerId));
        crit.addOrder(Order.asc("name"));
        resultList = crit.list();                        
      } else {
        
        if (getRequest().isUserInRole("admin")) {
          resultList = HibernateSession.currentSession().find("from Task as t order by p.name asc");
        } else {
          User user = WebUtils.getUser(getRequest());
          Criteria crit = HibernateSession.currentSession().createCriteria(Task.class);
          crit.createCriteria("project").add(Expression.in("customer", user.getCustomers()));
          crit.addOrder(Order.asc("name"));
          resultList = crit.list();                                  
        }
          
        
      }
      
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Task t = (Task)it.next();
        getLabelValue().add(new LabelValueBean(t.getName(), t.getId().toString()));
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
