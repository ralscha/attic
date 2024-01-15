package ch.ess.cal.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a ResourceGroup
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calResourceGroup" lazy="true"
  */
public class ResourceGroup extends Persistent {

  private Set resources;
  private Set departments;
  private Set users;
  private Map translations;
   
  /**      
   * @hibernate.set lazy="true" inverse="true"
   * @hibernate.collection-key column="resourceGroupId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Resource"
   */       
  public Set getResources() {
    if (resources == null) {
      resources = new HashSet();
    }
    return this.resources;
  }

  public void setResources(Set resources) {
    this.resources = resources;
  }

  /**      
   * @hibernate.set lazy="true" table="calDepartmentResourceGroups"
   * @hibernate.collection-key column="resourceGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.Department" column="departmentId"
   */      
  public Set getDepartments() {
    if (departments == null) {
      departments = new HashSet();
    }
    return this.departments;
  }

  public void setDepartments(Set departments) {
    this.departments = departments;
  }


  /**      
   * @hibernate.set lazy="true" table="calUserResourceGroups"
   * @hibernate.collection-key column="resourceGroupId"     
   * @hibernate.collection-many-to-many class="ch.ess.cal.db.User" column="userId"
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

  /**      
   * @hibernate.map table="calResourceGroupLocale" 
   * @hibernate.collection-key column="resourceGroupId"    
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
      return (HibernateSession.collectionSize(resources) == 0);
    }
    return false;
  }

  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from ResourceGroup");
  }

  //load
  public static ResourceGroup load(Long resourceGroupId) throws HibernateException {
    return (ResourceGroup)HibernateSession.currentSession().load(ResourceGroup.class, resourceGroupId);
  }

  //delete method  
  public static int delete(Long resourceGroupId) throws HibernateException {
    return HibernateSession.currentSession().delete(
      "from ResourceGroup as rg where rg.id = ?",
      resourceGroupId,
      Hibernate.LONG);
  }

}
