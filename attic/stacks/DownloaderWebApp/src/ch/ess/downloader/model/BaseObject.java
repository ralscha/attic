package ch.ess.downloader.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.sun.istack.internal.NotNull;

@MappedSuperclass
public abstract class BaseObject implements Serializable {

  protected Long id;
  protected int version;

  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  @Version
  @NotNull
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
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }

}
