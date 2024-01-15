package ch.ess.cal.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;

import org.apache.commons.validator.GenericValidator;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;

/** A business entity class representing a Holiday
  * 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @hibernate.class  table="calHoliday"
  */
public class Holiday extends Persistent {

  private String internalKey;
  private String colour;
  private String country;
  private Integer month;
  private int dayOfMonth;
  private int dayOfWeek;
  private int fromYear;
  private int toYear;
  private boolean builtin;
  private boolean active;
  private Map translations;

  /** 
  * @hibernate.property length="50" not-null="true"
  */
  public String getInternalKey() {
    return this.internalKey;
  }

  public void setInternalKey(String name) {
    this.internalKey = name;
  }


  /** 
  * @hibernate.property length="6" not-null="false"
  */
  public String getColour() {
    return this.colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  /** 
  * @hibernate.property length="2" not-null="false"
  */
  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public Integer getMonth() {
    return this.month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public int getDayOfMonth() {
    return this.dayOfMonth;
  }

  public void setDayOfMonth(int dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public int getDayOfWeek() {
    return this.dayOfWeek;
  }

  public void setDayOfWeek(int dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public int getFromYear() {
    return this.fromYear;
  }

  public void setFromYear(int fromYear) {
    this.fromYear = fromYear;
  }

  /** 
  * @hibernate.property not-null="false"
  */
  public int getToYear() {
    return this.toYear;
  }

  public void setToYear(int toYear) {
    this.toYear = toYear;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isBuiltin() {
    return this.builtin;
  }

  public void setBuiltin(boolean builtin) {
    this.builtin = builtin;
  }

  /** 
  * @hibernate.property not-null="true"
  */
  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  /**      
   * @hibernate.map table="calHolidayLocale" 
   * @hibernate.collection-key column="holidayId"    
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
  public boolean canDelete() {
    return false;
  }

  //finder methods  
  public static Iterator find() throws HibernateException {
    return find(null, null);
  }

  public static Iterator find(String country, String show) throws HibernateException {

    Session sess = HibernateSession.currentSession();
    Criteria crit = sess.createCriteria(Holiday.class);


    if (!GenericValidator.isBlankOrNull(country)) {
      crit.add(Expression.eq("country", country));
    }

    if (!GenericValidator.isBlankOrNull(show)) {
      Boolean b = new Boolean(show);
      crit.add(Expression.eq("active", b));
    }

    crit.add(Expression.eq("builtin", Boolean.TRUE));
    crit.addOrder(Order.asc("country"));    

    return crit.list().iterator();

  }

  //load
  public static Holiday load(Long holidayId) throws HibernateException {
    return (Holiday)HibernateSession.currentSession().load(Holiday.class, holidayId);
  }

  //delete method  
  public static int delete(Long holidayId) throws HibernateException {
    return HibernateSession.currentSession().delete("from Holiday as hol where hol.id = ?", holidayId, Hibernate.LONG);
  }

}
