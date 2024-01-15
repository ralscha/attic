package ch.ess.task.db;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Task
  * 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * @hibernate.class  table="taskTask"
  */
public class Task extends Persistent {

  private String name;
  private String description;
  private Project project;
  private Status status;
  private Priority priority;
  private User assignedTo;
  private User createdBy;
  private Date created;
  private Date resolved;
  private String resolution;
  private Integer complete;
  

  /** 
  * @hibernate.property length="255" not-null="true"
  */
  public String getName() {
    return name;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public Date getCreated() {
    return created;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.User" column="byUserId" not-null="true"   
  */
  public User getCreatedBy() {
    return createdBy;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.User" column="toUserId" not-null="false"   
  */
  public User getAssignedTo() {
    return assignedTo;
  }

  /** 
  * @hibernate.property length="1000" not-null="false"
  */
  public String getDescription() {
    return description;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.Priority" column="priorityId" not-null="true"   
  */
  public Priority getPriority() {
    return priority;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.Project" column="projectId" not-null="true"   
  */
  public Project getProject() {
    return project;
  }

  /** 
  * @hibernate.property length="1000" not-null="false"
  */
  public String getResolution() {
    return resolution;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Date getResolved() {
    return resolved;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.task.db.Status" column="statusId" not-null="true"   
  */
  public Status getStatus() {
    return status;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public Integer getComplete() {
    return complete;
  }


  public void setName(String string) {
    name = string;
  }


  public void setCreated(Date date) {
    created = date;
  }

  public void setCreatedBy(User user) {
    createdBy = user;
  }

  public void setAssignedTo(User user) {
    assignedTo = user;
  }

  public void setDescription(String string) {
    description = string;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setResolution(String string) {
    resolution = string;
  }

  public void setResolved(Date date) {
    resolved = date;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setComplete(Integer integer) {
    complete = integer;
  }

  //finder methods  
  public static Iterator find(String projectId, String priorityId, String userId, String statusId) throws HibernateException {
    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Task.class);
    addExpressions(crit, projectId, priorityId, userId, statusId); 
    return crit.list().iterator();    
  }
    
  public static Iterator findWithIds(List ids, String projectId, String priorityId, String userId, String statusId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Task.class);
    crit.add(Expression.in("id", ids));
    addExpressions(crit, projectId, priorityId, userId, statusId); 
    return crit.list().iterator();    
  }    
   
  private static void addExpressions(Criteria crit, String projectId, String priorityId, String userId, String statusId) throws NumberFormatException {
    if (!GenericValidator.isBlankOrNull(projectId)) {
      crit.add(Expression.eq("project.id", new Long(projectId)));
    }
    if (!GenericValidator.isBlankOrNull(priorityId)) {      
      crit.add(Expression.eq("priority.id", new Long(priorityId)));
    }
    if (!GenericValidator.isBlankOrNull(userId)) {      
      crit.add(Expression.eq("assignedTo.id", new Long(userId)));
    }    
    if (!GenericValidator.isBlankOrNull(statusId)) {      
      crit.add(Expression.eq("status.id", new Long(statusId)));
    }       
  }   
        

  //load
  public static Task load(Long taskId) throws HibernateException {
    return (Task)HibernateSession.currentSession().load(Task.class, taskId);  
  }

  //delete method  
  public static int delete(Long taskId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Task as t where t.id = ?", taskId, Hibernate.LONG);
  }




}
