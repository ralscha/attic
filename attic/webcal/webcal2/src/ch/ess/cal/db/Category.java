package ch.ess.cal.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Category
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calCategory" lazy="true"
  */
public class Category extends Persistent {
  
  private String icalKey;
  private String colour;
  private String bwColour;
  private boolean unknown;
  private Set events;
  private Map translations;


  /** 
  * @hibernate.property length="100" not-null="false"
  */
  public String getIcalKey() {
    return this.icalKey;
  }

  public void setIcalKey(String icalKey) {
    this.icalKey = icalKey;
  }
  
  /** 
  * @hibernate.property length="6" not-null="true"
  */
  public String getColour() {
    return this.colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  /** 
  * @hibernate.property length="6" not-null="true"
  */
  public String getBwColour() {
    return this.bwColour;
  }

  public void setBwColour(String bwColour) {
    this.bwColour = bwColour;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isUnknown() {
    return this.unknown;
  }

  public void setUnknown(boolean unknown) {
    this.unknown = unknown;
  }


  /**      
   * @hibernate.set lazy="true" table="calEventCategories"
   * @hibernate.collection-key  column="categoryId"     
   * @hibernate.collection-many-to-many  class="ch.ess.cal.db.Event" column="eventId"
   */
  public Set getEvents() {
    return this.events;
  }

  public void setEvents(Set events) {
    this.events = events;
  }


  /**      
    * @hibernate.map table="calCategoryLocale" 
    * @hibernate.collection-key column="categoryId"    
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
       return (HibernateSession.collectionSize(events) == 0) && !isUnknown();
     } 
     return false;
   }    
    
   //finder methods  
   public static Iterator find() throws HibernateException {
     return HibernateSession.currentSession().iterate("from Category");  
   }

   /*    
   public static Iterator find(String searchText) throws HibernateException {
     searchText = "%" + searchText + "%";   
    
     return HibernateSession.currentSession().iterate("from Category p where p.name like ?",
     searchText, Hibernate.STRING);        
   }
   */
  

   //load
   public static Category load(Long categoryId) throws HibernateException {
     return (Category)HibernateSession.currentSession().load(Category.class, categoryId);  
   }

   //delete method  
   public static int delete(Long categoryId) throws HibernateException {
     return HibernateSession.currentSession().delete("from Category as p where p.id = ?", categoryId, Hibernate.LONG);
   }

}
