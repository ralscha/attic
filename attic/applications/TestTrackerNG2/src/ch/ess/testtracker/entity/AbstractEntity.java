package ch.ess.testtracker.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Integer version;

  @Column(name = "entityUid", unique = true, nullable = false, updatable = false, length = 36)
  private String uid;

  public Long getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

  @Override
  public boolean equals(Object o) {
    return (o == this || (o instanceof AbstractEntity && uid().equals(((AbstractEntity)o).uid())));
  }

  @Override
  public int hashCode() {
    return uid().hashCode();
  }

  public String uid() {
    if (uid == null) {
      uid = UUID.randomUUID().toString();
    }
    return uid;
  }

}
