package ch.ess.cal.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Resource
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calResource" lazy="true"
  */
public class Resource extends Persistent {

  private ResourceGroup resourceGroup;
  private Set events;
  private Map translations;
  
 
  /** 
  * @hibernate.many-to-one class="ch.ess.cal.db.ResourceGroup"
  * @hibernate.column name="resourceGroupId" not-null="true" index="FK_Resource_ResourceGroup"  
  */                   
  public ResourceGroup getResourceGroup() {
    return this.resourceGroup;
  }

  public void setResourceGroup(ResourceGroup resourceGroup) {
    this.resourceGroup = resourceGroup;
  }
 
  /**      
   * @hibernate.set lazy="true" inverse="true"
   * @hibernate.collection-key  column="resourceId"     
   * @hibernate.collection-one-to-many class="ch.ess.cal.db.Event"
   */      
  public java.util.Set getEvents() {
    return this.events;
  }

  public void setEvents(java.util.Set events) {
    this.events = events;
  }

  /**      
   * @hibernate.map table="calResourceLocale" 
   * @hibernate.collection-key column="resourceId"    
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
      return ((HibernateSession.collectionSize(events) == 0));
    }
    return false;
  }

  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from Resource");
  }
  
  public static Iterator find(String resourceGroupId) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Resource.class);

    if (!GenericValidator.isBlankOrNull(resourceGroupId)) {
      crit.createCriteria("resourceGroup").add(Expression.eq("id", new Long(resourceGroupId)));
    }

    return crit.list().iterator();

  }  

  //load
  public static Resource load(Long resourceId) throws HibernateException {
    return (Resource)HibernateSession.currentSession().load(Resource.class, resourceId);
  }

  //delete method  
  public static int delete(Long resourceId) throws HibernateException {
    return HibernateSession.currentSession().delete(
      "from Resource as r where r.id = ?",
      resourceId,
      Hibernate.LONG);
  }
}
