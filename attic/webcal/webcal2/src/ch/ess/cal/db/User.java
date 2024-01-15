package ch.ess.cal.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import net.sf.hibernate.type.Type;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

import com.ibm.icu.util.TimeZone;

/** A business entity class representing an User
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:46 $
  * @hibernate.class  table="calUser" lazy="true"
  */

public class User extends Persistent {

  private String firstName;
  private String name;
  private String shortName;
  private List emails;
  private String userName;
  private String passwordHash;
  private String loginToken;
  private Date loginTokenTime;

  private Locale locale;
  private String timeZone;
  
  private Set userRoles;
  private Set userConfiguration;

  private Set events;
  private Set accessDepartments;
  private Set departments;
  private Set resourceGroups;


  /** 
  * @hibernate.property length="100" not-null="false"
  */
  public String getFirstName() {
    return firstName;
  }

  /** 
  * @hibernate.property length="10" not-null="true"
  */
  public Locale getLocale() {
    return locale;
  }

  /** 
  * @hibernate.property length="100" not-null="true"
  */
  public String getName() {
    return name;
  }

  /** 
  * @hibernate.property length="40" not-null="false"
  */
  public String getPasswordHash() {
    return passwordHash;
  }

  /** 
  * @hibernate.property unique="true" length="100" not-null="true"
  */
  public String getUserName() {
    return userName;
  }


  /** 
  * @hibernate.property length="40" not-null="false"
  */
  public String getLoginToken() {
    return loginToken;
  }

  /** 
  * @hibernate.property type="timestamp" not-null="false"
  */
  public Date getLoginTokenTime() {
    return loginTokenTime;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public String getShortName() {
    return shortName;
  }

  /** 
   * @hibernate.property length="50" not-null="true"
   */
  public String getTimeZone() {
    return timeZone;
  }
  
  
  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public void setLoginToken(String string) {
    loginToken = string;
  }

  public void setLoginTokenTime(Date date) {
    loginTokenTime = date;
  }

  public void setFirstName(String string) {
    firstName = string;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setName(String string) {
    name = string;
  }

  public void setPasswordHash(String string) {
    passwordHash = string;
  }

  public void setUserName(String string) {
    userName = string;
  }

  public void setShortName(String string) {
    shortName = string;
  }
  
  /**      
     * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key  column="userId"     
   * @hibernate.collection-one-to-many  class="ch.ess.cal.db.UserConfiguration"
   */
  public Set getUserConfiguration() {
    return userConfiguration;
  }

  public void setUserConfiguration(Set set) {
    userConfiguration = set;
  }

  /**      
   * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key  column="userId"     
   * @hibernate.collection-one-to-many  class="ch.ess.cal.db.UserRole"
   */
  public Set getUserRoles() {
    if (userRoles == null) {
      userRoles = new HashSet();
    }

    return userRoles;
  }

  public void setUserRoles(Set set) {
    userRoles = set;
  }

  public void addRole(Role role) {
    UserRole userRole = new UserRole();
    userRole.setUserName(getUserName());
    userRole.setRoleName(role.getName());
    userRole.setRole(role);
    userRole.setUser(this);

    getUserRoles().add(userRole);
  }

  /**      
   * @hibernate.list lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-index column="sequence"    
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Email"
   */     
  public List getEmails() {
    if (emails == null) {
      emails = new ArrayList();
    }
    return this.emails;
  }

  public void setEmails(List emails) {
    this.emails = emails;
  }

  /**      
   * @hibernate.set lazy="true" table="calUserEvents"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.Event" column="eventId"
   */
  public Set getEvents() {
    return events;
  }

  public void setEvents(Set events) {
    this.events = events;
  }

  /**      
   * @hibernate.set lazy="true" table="calAccessDepartmentUsers"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.Department" column="departmentId"
   */
  public Set getAccessDepartments() {
    if (accessDepartments == null) {
      accessDepartments = new HashSet();
    }
    return accessDepartments;
  }

  public void setAccessDepartments(Set accessDepartments) {
    this.accessDepartments = accessDepartments;
  }

  /**      
   * @hibernate.set lazy="true" table="calDepartmentUsers"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.Department" column="departmentId"
   */
  public Set getDepartments() {
    if (departments == null) {
      departments = new HashSet();
    }
    return departments;
  }

  public void setDepartments(Set departments) {
    this.departments = departments;
  }

  /**      
   * @hibernate.set lazy="true" table="calUserResourceGroups"
   * @hibernate.collection-key column="userId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.ResourceGroup" column="resourceGroupId"
   */
  public Set getResourceGroups() {
    if (resourceGroups == null) {
      resourceGroups = new HashSet();
    }
    return resourceGroups;
  }

  public void setResourceGroups(Set resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  //load
  public static User load(Long userId) throws HibernateException {
    return (User)HibernateSession.currentSession().load(User.class, userId);
  }

  //delete method

  public static int delete(Long userId) throws HibernateException {
    return HibernateSession.currentSession().delete("from User as u where u.id = ?", userId, Hibernate.LONG);
  }

  //finder methods
  public static Iterator find() throws HibernateException {
    return findWithSearchtext(null, null);
  }
      
  public static Iterator findWithSearchtext(String searchText, String roleId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(User.class);

    if (!GenericValidator.isBlankOrNull(searchText)) {
      searchText = "%" + searchText + "%";
      crit.add(
        Expression.or(
          Expression.or(Expression.like("email", searchText), Expression.like("name", searchText)),
          Expression.like("userName", searchText)));
    }

    if (!GenericValidator.isBlankOrNull(roleId)) {
      crit.createCriteria("userRoles").add(Expression.eq("role.id", new Long(roleId)));
    }

    crit.addOrder(Order.asc("userName"));

    return crit.list().iterator();

  }

  public static User find(String userName, String passwordHash) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find(
        "from User as u where u.userName = ? AND u.passwordHash = ?",
        new Object[] { userName, passwordHash },
        new Type[] { Hibernate.STRING, Hibernate.STRING });

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  public static User find(String userName) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find("from User as u where u.userName = ?", userName, Hibernate.STRING);

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  public static User findExcludeId(String userName, Long id) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(User.class);

    crit.add(Expression.eq("userName", userName));

    if (id != null) {
      crit.add(Expression.not(Expression.eq("id", id)));
    }

    List userList = crit.list();

    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }

  public static User findWithLoginToken(String loginToken) throws HibernateException {
    List userList =
      HibernateSession.currentSession().find("from User as u where u.loginToken = ?", loginToken, Hibernate.STRING);
    if (userList.size() > 0) {
      return (User)userList.get(0);
    } else {
      return null;
    }
  }
  
  
  public TimeZone getTimeZoneObj() {
    return TimeZone.getTimeZone(getTimeZone());  
  }

}
