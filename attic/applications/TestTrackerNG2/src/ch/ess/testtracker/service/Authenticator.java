package ch.ess.testtracker.service;

import static org.jboss.seam.ScopeType.SESSION;
import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import ch.ess.testtracker.entity.Principal;

@SuppressWarnings({"unused", "unchecked"})
@Name("authenticator")
public class Authenticator {

  @In
  private Session hibernateSession;

  @Out(required = false, scope = SESSION)
  private Principal principal;

  public boolean authenticate() {

    List<Principal> results = hibernateSession.createQuery(
        "from Principal p where p.userName = #{identity.username} and p.passwordHash = #{identity.password}").list();

    if (results.size() == 0) {
      return false;
    }
        
    principal = results.get(0);
    
    return true;

  }

}
