package ch.ess.testtracker.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
public class Authority extends AbstractEntity {

  @ManyToOne
  @NotNull
  private Principal principal;

  @NotNull
  @Length(max = 255)
  private String authority;

  public Principal getPrincipal() {
    return principal;
  }

  public void setPrincipal(Principal principal) {
    this.principal = principal;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

}
