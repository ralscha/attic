package ch.ess.timetracker.db;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
  * @hibernate.class  table="ttTaskTime" lazy="true"
  */

public class TaskTime extends Persistent {

  private String description;
  private String comment;
  private Date taskTimeDate;
  private BigDecimal workInHour;
  private BigDecimal hourRate;
  private BigDecimal cost;
  
  private Task task;
  private User user;
  

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
  * @hibernate.property length="255" not-null="false"
  */
  public String getComment() {
    return comment;
  }

  public void setComment(String string) {
    comment = string;
  }

  /** 
   * @hibernate.property not-null="true"
   */  
  public Date getTaskTimeDate() {
    return taskTimeDate;
  }
  
  public void setTaskTimeDate(Date taskTimeDate) {
    this.taskTimeDate = taskTimeDate;
  }
  
  /** 
   * @hibernate.property length="3" not-null="true"
   */  
  public BigDecimal getWorkInHour() {
    return workInHour;
  }
  
  public void setWorkInHour(BigDecimal workInHour) {
    this.workInHour = workInHour;
  }
  
  /** 
   * @hibernate.property length="3" not-null="false"
   */ 
  public BigDecimal getHourRate() {
    return hourRate;
  }
  
  public void setHourRate(BigDecimal hourRate) {
    this.hourRate = hourRate;
  }
  
  /** 
   * @hibernate.property length="3" not-null="false"
   */   
  public BigDecimal getCost() {
    return cost;
  }
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }  
  
  /** 
   * @hibernate.many-to-one class="ch.ess.timetracker.db.Task"
   * @hibernate.column name="taskId" not-null="true" index="FK_TaskTime_Task"  
   */  
  public Task getTask() {
    return task;
  }

  public void setTask(Task t) {
    task = t;
  }

  
  /** 
   * @hibernate.many-to-one class="ch.ess.timetracker.db.User"
   * @hibernate.column name="userId" not-null="true" index="FK_TaskTime_User"  
   */  
  public User getUser() {
    return user;
  }

  public void setUser(User u) {
    user = u;
  }
  
  
  
  //load
  public static TaskTime load(Long taskTimeId) throws HibernateException {
    return (TaskTime)HibernateSession.currentSession().load(TaskTime.class, taskTimeId);
  }

  //delete method
  public static int delete(Long taskTimeId) throws HibernateException {
    return HibernateSession.currentSession().delete("from TaskTime as tt where tt.id = ?", taskTimeId, Hibernate.LONG);
  }

  
  public static Iterator find(Date from, Date to, String customerId, String projectId, String taskId, Collection customers, Long userId) throws HibernateException {
    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(TaskTime.class);

    if (userId != null) {
      crit.createCriteria("user").add(Expression.eq("id", userId));
    }
    
    if (!GenericValidator.isBlankOrNull(taskId)) {
      crit.createCriteria("task").add(Expression.eq("id", new Long(taskId)));
    } else if (!GenericValidator.isBlankOrNull(projectId)) {      
      crit.createCriteria("task").createCriteria("project").add(Expression.eq("id", new Long(projectId)));
    } else if (!GenericValidator.isBlankOrNull(customerId)) {      
      crit.createCriteria("task").createCriteria("project").createCriteria("customer").add(Expression.eq("id", new Long(customerId)));
    } else {  
      if (customers != null) {
        crit.createCriteria("task").createCriteria("project").add(Expression.in("customer", customers));
      }
    }    
   
    if (from != null) {
      crit.add(Expression.ge("taskTimeDate", from));
    }
    
    if (to != null) {
      crit.add(Expression.le("taskTimeDate", to));
    }
    
    crit.addOrder(Order.asc("taskTimeDate"));
    return crit.list().iterator();
  }
  
}
