package ch.ess.testtracker.service;

import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import ch.ess.testtracker.entity.Principal;

@SuppressWarnings({"unused", "unchecked"})
@Name("test")
@Scope(ScopeType.CONVERSATION)
public class Test {

  @In
  private Session hibernateSession;

  @In(required=false)
  @Out(required=false)
  private Principal principal;
  
  @Begin
  public void test() {
    List<Principal> results = hibernateSession.createQuery(
        "from Principal").list();

    principal = results.get(0);
    principal.setEmail("sr7@ess.ch");
    System.out.println("END STATUS");
  }
  
  @End
  @Transactional
  public void end() {
    System.out.println(principal.getEmail());
    hibernateSession.flush();
    System.out.println("END");
  }

}
