package ch.ess.cal.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Department
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calDepartment" lazy="true"
  */
public class Department extends Persistent {

  private List emails;
  private Set configuration;
  private Set users;
  private Set accessUsers;
  private Set resourceGroups;
  private Set events;
  private Map translations;

  /**      
   * @hibernate.list lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="departmentId"
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
   * @hibernate.set lazy="true" cascade="all-delete-orphan" inverse="true"
   * @hibernate.collection-key column="departmentId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.DepartmentConfiguration"
   */
  public Set getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Set configuration) {
    this.configuration = configuration;
  }

  /**      
   * @hibernate.set lazy="true" table="calDepartmentUsers"
   * @hibernate.collection-key column="departmentId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.User" column="userId"
   */
  public Set getUsers() {
    if (users == null) {
      users = new HashSet();
    }      
    return users;
  }

  public void setUsers(Set users) {
    this.users = users;
  }

  /**      
   * @hibernate.set lazy="true" table="calAccessDepartmentUsers"
   * @hibernate.collection-key column="departmentId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.User" column="userId"
   */
  public Set getAccessUsers() {
    if (accessUsers == null) {
      accessUsers = new HashSet();
    }
    return this.accessUsers;
  }

  public void setAccessUsers(Set accessUsers) {
    this.accessUsers = accessUsers;
  }

  /**      
   * @hibernate.set lazy="true" table="calDepartmentResourceGroups"
   * @hibernate.collection-key column="departmentId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.ResourceGroup" column="resourceGroupId"
   */
  public Set getResourceGroups() {
    if (resourceGroups == null) {
      resourceGroups = new HashSet();
    }
    return this.resourceGroups;
  }

  public void setResourceGroups(Set resourceGroups) {
    this.resourceGroups = resourceGroups;
  }

  /**      
   * @hibernate.set lazy="true" inverse="true"
   * @hibernate.collection-key  column="departmentId"     
   * @hibernate.collection-one-to-many  class="ch.ess.cal.db.Event"
   */
  public Set getEvents() {
    return this.events;
  }

  public void setEvents(Set events) {
    this.events = events;
  }

  /**      
   * @hibernate.map table="calDepartmentLocale" 
   * @hibernate.collection-key column="departmentId"    
   * @hibernate.collection-index column="locale" length="10" type="locale"
   * @hibernate.collection-element column="name" length="100" not-null="true" type="string" 
   */
  public Map getTranslations() {
    if (translations == null) {
      translations = new HashMap();
      translations.put(Constants.GERMAN_LOCALE, null);
      translations.put(Constants.ENGLISH_LOCALE, null);
    }
    return translations;
  }

  public void setTranslations(Map map) {
    translations = map;
  }

  //can delete
  public boolean canDelete() throws HibernateException {
    if (getId() != null) {
      return (HibernateSession.collectionSize(events) == 0);
    }
    return false;
  }

  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from Department");
  }

  //load
  public static Department load(Long departmentId) throws HibernateException {
    return (Department)HibernateSession.currentSession().load(Department.class, departmentId);
  }

  //delete method  
  public static int delete(Long departmentId) throws HibernateException {
    return HibernateSession.currentSession().delete(
      "from Department as d where d.id = ?",
      departmentId,
      Hibernate.LONG);
  }
}
