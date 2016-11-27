package @packageProject@.entity;

import javax.persistence.Entity;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.security.management.RoleName;

@Entity
public class Role extends AbstractEntity {

  @RoleName
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