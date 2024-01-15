package ch.ess.task.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a TextResource
  * 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:58 $ 
  * @hibernate.class  table="taskTextResource"
  */
public class TextResource extends Persistent {

  private String name;
  private Map translations;
  
  /** 
  * @hibernate.property length="100" not-null="true" unique="true"
  */
  public String getName() {
    return name;
  }

  public void setName(String string) {
    name = string;
  }

  /**      
   * @hibernate.map table="taskTextResourceLocale" 
   * @hibernate.collection-key column="textResourceId"    
   * @hibernate.collection-index column="locale" length="10" type="locale"
   * @hibernate.collection-element column="name" length="1000" not-null="true" type="string" 
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
  public boolean canDelete() {
    return false;
  }    
    
  //finder methods  
  public static Iterator find() throws HibernateException {
    return HibernateSession.currentSession().iterate("from TextResource");  
  }
    
  public static List findName(String key) throws HibernateException {
    return HibernateSession.currentSession().find("from TextResource tr where tr.name = ?",
    key, Hibernate.STRING);        
  }
  

  //load
  public static TextResource load(Long textResourceId) throws HibernateException {
    return (TextResource)HibernateSession.currentSession().load(TextResource.class, textResourceId);  
  }

  //delete method  
  public static int delete(Long textResourceId) throws HibernateException {
    return HibernateSession.currentSession().delete("from TextResource as tr where tr.id = ?", textResourceId, Hibernate.LONG);
  }
}
