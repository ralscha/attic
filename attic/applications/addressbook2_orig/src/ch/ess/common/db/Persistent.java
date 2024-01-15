package ch.ess.common.db;

import java.io.Serializable;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Lifecycle;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Validatable;
import net.sf.hibernate.ValidationFailure;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class Persistent implements Lifecycle, Validatable, Serializable {

  private Long id;
  private int version;

  /**   
   * @hibernate.id  generator-class="native" unsaved-value="null"
   */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**   
   * @hibernate.version
   */
  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public void persist() throws HibernateException {
    HibernateSession.currentSession().saveOrUpdate(this);
  }

  public void delete() throws HibernateException {
    HibernateSession.currentSession().delete(this);
  }

  public void refresh() throws HibernateException {
    HibernateSession.currentSession().load(this, id);
  }

  public void lock() throws HibernateException {
    HibernateSession.currentSession().lock(this, LockMode.UPGRADE);
  }

  public boolean onSave(Session s) throws CallbackException {
    return NO_VETO;
  }

  public boolean onDelete(Session s) throws CallbackException {
    return NO_VETO;
  }

  public boolean onUpdate(Session s) throws CallbackException {
    return NO_VETO;
  }

  public void onLoad(Session s, Serializable id) {

  }

  public void validate() throws ValidationFailure {
  }

  /*
  public boolean equals(Object other) {
    if (!(other instanceof Persistent)) {
      return false;
    }
    
    Persistent castOther = (Persistent)other;
    return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
  }
    
  
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }
  */

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}