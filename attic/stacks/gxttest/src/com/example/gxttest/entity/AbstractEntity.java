package com.example.gxttest.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;
import org.hibernate.search.annotations.DocumentId;

@MappedSuperclass
@EntityListeners(AbstractEntity.AbstractEntityListener.class)
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue 
  @DocumentId
  private Integer id;
  
  @Column(name="entityUid", unique=true, nullable=false, updatable=false, length=36)
  private String uid;
  
  @Version
  private Integer version;
  
  public Integer getId() {
    return id;
  }
  
  public Integer getVersion() {
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
  
  public static class AbstractEntityListener {
      
    @PrePersist
    public void onPrePersist(AbstractEntity abstractEntity) {
      abstractEntity.uid();
    }
  }
  
  String uid() {
    if (uid == null) {
      uid = UUID.uuid();
    }
    return uid;
  }
}
