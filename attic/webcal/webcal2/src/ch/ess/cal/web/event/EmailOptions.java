package ch.ess.cal.web.event;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.util.LabelValueBean;

import ch.ess.cal.db.Department;
import ch.ess.cal.db.Email;
import ch.ess.cal.db.User;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.Options;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 15.10.2003
  */
public class EmailOptions extends Options {

  public void init() throws HibernateException {


    Transaction tx = null;
    try {
      tx = HibernateSession.currentSession().beginTransaction();

      User user = User.find(getRequest().getUserPrincipal().getName());
        
      Set emails = new TreeSet();
      
      List emailList = user.getEmails();      
      for (Iterator it = emailList.iterator(); it.hasNext();) {
        Email d = (Email)it.next();
        emails.add(d.getEmail());
      }
      
      Set departments = user.getDepartments();
      for (Iterator it = departments.iterator(); it.hasNext();) {
        Department dep = (Department)it.next();
        emailList = dep.getEmails();
        for (Iterator it2 = emailList.iterator(); it2.hasNext();) {
          Email d = (Email)it2.next();
          emails.add(d.getEmail());
        }          
        
      }
      
      
      for (Iterator it = emails.iterator(); it.hasNext();) {
        String email = (String )it.next();
        getLabelValue().add(new LabelValueBean(email, email));  
      }
      
      
      //sort();
      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }
  }

}
