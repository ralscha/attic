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

/** A business entity class representing a Customer
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:17 $ 
  * @hibernate.class  table="ttCustomer" lazy="true"
  */

public class Customer extends Persistent {

  private String name;
  private String description;
  private Set projects;
  private Set users;


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
   * @hibernate.collection-key column="customerId"     
   * @hibernate.collection-one-to-many class="ch.ess.timetracker.db.Project"
   */
  public Set getProjects() {
    if (projects == null) {
      projects = new HashSet();
    }
    return projects;
  }

  public void setProjects(Set set) {
    projects = set;
  }
  
  
  /**      
   * @hibernate.set lazy="true" table="ttUserCustomers"
   * @hibernate.collection-key column="customerId"     
   * @hibernate.collection-many-to-many class="ch.ess.timetracker.db.User" column="userId"
   */      
  public Set getUsers() {
    if (users == null) {
      users = new HashSet();
    }
    return this.users;
  }

  public void setUsers(Set users) {
    this.users = users;
  }  
  
  
  //load
  public static Customer load(Long customerId) throws HibernateException {
    return (Customer)HibernateSession.currentSession().load(Customer.class, customerId);
  }

  //delete method
  public static int delete(Long customerId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Customer as c where c.id = ?", customerId, Hibernate.LONG);
  }

  //finder methods    
  public static Iterator findWithSearchtext(String searchText) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Customer.class);

    if (!GenericValidator.isBlankOrNull(searchText)) {
      searchText = "%" + searchText + "%";
      crit.add(Expression.like("name", searchText));
    }


    crit.addOrder(Order.asc("name"));

    return crit.list().iterator();

  }
  

  public boolean canDelete() throws HibernateException {
    if (getId() != null) {
      return ((HibernateSession.collectionSize(projects) == 0));
    }
    return false;
  }  
  
}
