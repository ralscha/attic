package ch.ess.timetracker.db;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Project
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:17 $ 
  * @hibernate.class  table="ttProject" lazy="true"
  */

public class Project extends Persistent {

  private String name;
  private String description;
  private Set tasks;
  private Customer customer;
  
  /** 
  * @hibernate.property length="255" not-null="true"
  */
  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  /** 
  * @hibernate.property length="255" not-null="false"
  */
  public String getDescription() {
    return description;
  }

  public void setDescription(String string) {
    description = string;
  }

  /**      
   * @hibernate.set cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="projectId"     
   * @hibernate.collection-one-to-many class="ch.ess.timetracker.db.Task"
   */
  public Set getTasks() {
    if (tasks == null) {
      tasks = new HashSet();
    }
    
    return tasks;
  }

  public void setTasks(Set set) {
    tasks = set;
  }
  
  /** 
   * @hibernate.many-to-one class="ch.ess.timetracker.db.Customer"
   * @hibernate.column name="customerId" not-null="true" index="FK_Project_Customer"  
   */  
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer c) {
    customer = c;
  }
  
  
  //load
  public static Project load(Long projectId) throws HibernateException {
    return (Project)HibernateSession.currentSession().load(Project.class, projectId);
  }

  //delete method
  public static int delete(Long projectId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Project as p where p.id = ?", projectId, Hibernate.LONG);
  }

  //finder methods    
  public static Iterator findWithSearchtext(String searchText, String customerId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Project.class);

    if (!GenericValidator.isBlankOrNull(searchText)) {
      searchText = "%" + searchText + "%";
      crit.add(Expression.like("name", searchText));
    }

    if (!GenericValidator.isBlankOrNull(customerId)) {      
      crit.createCriteria("customer").add(Expression.eq("id", new Long(customerId)));
    }

    crit.addOrder(Order.asc("name"));

    return crit.list().iterator();

  }
  
  public boolean canDelete() throws HibernateException {
    if (getId() != null) {
      return ((HibernateSession.collectionSize(tasks) == 0));
    }
    return false;
  }    
  
  
}
