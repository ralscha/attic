package ch.ess.cal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public class BaseObject<T extends BaseObject> implements Serializable {

  private Integer id;
  private int version;

  @Id(generate = GeneratorType.AUTO)
  @Column(nullable = false, primaryKey = true)
  public Integer getId() {
    return id;
  }

  protected void setId(final Integer id) {
    this.id = id;
  }

  @Version
  @Column(nullable = false)
  public int getVersion() {
    return version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof BaseObject)) {
      return false;
    }

    BaseObject other = (BaseObject)obj;
    if ((other.getId() == null) || (id == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(id);

  }

}
