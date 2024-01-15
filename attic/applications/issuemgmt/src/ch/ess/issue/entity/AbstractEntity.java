package ch.ess.issue.entity;


import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  @Version
  private Integer version;

  @Column(name = "entityUid", unique = true, nullable = false, updatable = false, length = 36)
  private String uid;

  public Integer getId() {
    return id;
  }

  public int getVersion() {
    return version;
  }

//  @Override
//  public String toString() {
//    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
//  }

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
