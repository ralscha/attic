package ch.ess.blankrc.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public class BaseObject implements Serializable {

  private Integer id;
  private int version;

  /**   
   * @hibernate.id  generator-class="native" unsaved-value="null"
   */
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return super.hashCode();
  }

  public boolean equals(Object o) {
    if (!(o instanceof BaseObject)) {
      return false;
    }

    BaseObject other = (BaseObject)o;
    if ((other.getId() == null) || (id == null)) {
      return super.equals(o);
    }

    return other.getId().equals(id);

  }

}