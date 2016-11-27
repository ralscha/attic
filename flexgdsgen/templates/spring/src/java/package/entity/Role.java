package @packageProject@.entity;

import javax.persistence.Entity;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
public class Role extends AbstractEntity {

  @NotNull
  @Length(max = 50)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}