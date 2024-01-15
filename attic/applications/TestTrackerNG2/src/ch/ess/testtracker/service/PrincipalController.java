package ch.ess.testtracker.service;

import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import ch.ess.testtracker.entity.Principal;

@Name("principalController")
@Scope(ScopeType.CONVERSATION)
public class PrincipalController {

  @In
  private Session hibernateSession;

  @Out(required=false)
  @In(required=false)
  private Principal principal;
  
  @SuppressWarnings("unchecked")
  public List<Principal> list() {
    return hibernateSession.createCriteria(Principal.class).list();
  }
  
  @Begin
  public void getPrincipal(Long id) {
    principal = (Principal)hibernateSession.load(Principal.class, id);
  }

  @End
  public void cancel() {
    //no action
  }
  
  @End
  @Transactional
  public void save(Principal pr) {
    System.out.println(pr.getEmail());
    System.out.println(principal.getEmail());
    System.out.println("HI");
    hibernateSession.flush();
  }
  
  
}
