package ch.ess.onemany.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class One extends AbstractEntity {

  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "one")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  @LazyCollection(LazyCollectionOption.EXTRA)
  @OrderBy("name desc")
  private Set<Many> many = new HashSet<Many>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Many> getMany() {
    return many;
  }

  public void setMany(Set<Many> many) {
    this.many = many;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final One other = (One)obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  
  

}
