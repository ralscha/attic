package ch.ralscha.mycustomers.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import org.hibernate.validator.Length;

@MappedSuperclass
@EntityListeners(AbstractEntity.AbstractEntityListener.class)
public abstract class AbstractEntity implements Serializable {

  private static final long serialVersionUID = 2322159378376997461L;

  @Id
  @Length(max = 36)
  @Column(name = "entityUid")
  private String uid;

  @Override
  public boolean equals(Object o) {
    return (o == this || (o instanceof AbstractEntity && getUid().equals(((AbstractEntity)o).getUid())));
  }

  @Override
  public int hashCode() {
    return getUid().hashCode();
  }

  public static class AbstractEntityListener {

    @PrePersist
    public void onPrePersist(AbstractEntity abstractEntity) {
      abstractEntity.getUid();
    }
  }

  public String getUid() {
    if (uid == null) {
      uid = UUID.uuid();
    }
    return uid;
  }

}
