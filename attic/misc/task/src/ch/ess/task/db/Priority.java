package ch.ess.task.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Priority
  * 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * @hibernate.class  table="taskPriority"
  */
public class Priority extends Persistent {

  private Set tasks;
  private Map translations;

  /**      
   * @hibernate.set lazy="true" table="taskTask" cascade="none" inverse="true"
   * @hibernate.collection-key column="priorityId"     
   * @hibernate.collection-one-to-many class="ch.ess.task.db.Task"
   */
  public Set getTasks() {
    return tasks;
  }

  public void setTasks(Set set) {
    tasks = set;
  }



  /**      
   * @hibernate.map table="taskPriorityLocale" 
   * @hibernate.collection-key column="priorityId"    
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
      return (HibernateSession.collectionSize(tasks) == 0);
    }
    return false;
  }
  

  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from Priority");
  }

  public static Iterator find(String searchText) throws HibernateException {
    searchText = "%" + searchText + "%";

    return HibernateSession.currentSession().iterate(
      "from Priority p where p.name like ?",
      searchText,
      Hibernate.STRING);
  }

  //load
  public static Priority load(Long priorityId) throws HibernateException {
    return (Priority)HibernateSession.currentSession().load(Priority.class, priorityId);
  }

  //delete method  
  public static int delete(Long priorityId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Priority as p where p.id = ?", priorityId, Hibernate.LONG);
  }

}
