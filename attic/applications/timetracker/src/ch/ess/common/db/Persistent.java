package ch.ess.common.db;

import java.io.Serializable;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Lifecycle;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Validatable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:42 $ 
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

  public boolean onSave(Session s) {
    return NO_VETO;
  }

  public boolean onDelete(Session s) {
    return NO_VETO;
  }

  public boolean onUpdate(Session s) {
    return NO_VETO;
  }

  public void onLoad(Session s, Serializable oid) {
    //no action
  }

  public void validate() {
    //no action
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    } else {
      return super.hashCode();
    }

  }

  public boolean equals(Object o) {
    if (!(o instanceof Persistent)) {
      return false;
    }

    Persistent other = (Persistent)o;
    if ((other.getId() == null) || (id == null)) {
      return super.equals(o);
    }

    return other.getId().equals(id);

  }
  
  public boolean canDelete() throws HibernateException {
    return true;
  }

}