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

/** A business entity class representing a Task
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/01/05 07:58:17 $ 
  * @hibernate.class  table="ttTask" lazy="true"
  */

public class Task extends Persistent {

  private String name;
  private String description;
  private Set taskTimes;
  private Project project;
  
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
   * @hibernate.collection-key column="taskId"     
   * @hibernate.collection-one-to-many class="ch.ess.timetracker.db.TaskTime"
   */
  public Set getTaskTimes() {
    if (taskTimes == null) {
      taskTimes = new HashSet();
    }
    return taskTimes;
  }

  public void setTaskTimes(Set set) {
    taskTimes = set;
  }
  
  /** 
   * @hibernate.many-to-one class="ch.ess.timetracker.db.Project"
   * @hibernate.column name="projectId" not-null="true" index="FK_Task_Project"  
   */  
  public Project getProject() {
    return project;
  }

  public void setProject(Project p) {
    project = p;
  }
  
  
  //load
  public static Task load(Long taskId) throws HibernateException {
    return (Task)HibernateSession.currentSession().load(Task.class, taskId);
  }

  //delete method
  public static int delete(Long taskId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Task as t where t.id = ?", taskId, Hibernate.LONG);
  }

  //finder methods    
  public static Iterator findWithSearchtext(String searchText, String customerId, String projectId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Task.class);

    if (!GenericValidator.isBlankOrNull(searchText)) {
      searchText = "%" + searchText + "%";
      crit.add(Expression.like("name", searchText));
    }
    
    if (!GenericValidator.isBlankOrNull(projectId)) {      
      crit.createCriteria("project").add(Expression.eq("id", new Long(projectId)));
    } else if (!GenericValidator.isBlankOrNull(customerId)) {      
      crit.createCriteria("project").createCriteria("customer").add(Expression.eq("id", new Long(customerId)));
    } 
    
    

    crit.addOrder(Order.asc("name"));
    return crit.list().iterator();

  }
  
  
  public boolean canDelete() throws HibernateException {
    if (getId() != null) {
      return ((HibernateSession.collectionSize(taskTimes) == 0));
    }
    return false;
  }
}
