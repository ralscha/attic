package ch.ess.cal.db;

import net.sf.hibernate.HibernateException;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing an Email
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calEmail" lazy="true"
  */
public class Email extends Persistent {

  private int sequence;
  private String email;
  private boolean defaultEmail;
  private User user;
  private Department department;

  /** 
  * @hibernate.property not-null="true"
  */
  public int getSequence() {
    return this.sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  /** 
  * @hibernate.property length="100" not-null="true"
  */
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isDefaultEmail() {
    return this.defaultEmail;
  }

  public void setDefaultEmail(boolean defaultEmail) {
    this.defaultEmail = defaultEmail;
  }
  
  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.User"
  * @hibernate.column name="userId" not-null="false" index="FK_Email_User"  
  */
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.Department"
  * @hibernate.column name="departmentId" not-null="false" index="FK_Email_Department"  
  */
  public Department getDepartment() {
    return this.department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

   

  //load
  public static Email load(Long emailId) throws HibernateException {
    return (Email)HibernateSession.currentSession().load(Email.class, emailId);  
  }

}
