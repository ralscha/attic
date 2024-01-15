package ch.ess.onemany.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  @Version
  private Integer version;

  public Integer getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

}
