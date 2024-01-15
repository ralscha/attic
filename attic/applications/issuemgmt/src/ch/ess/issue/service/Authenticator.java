package ch.ess.issue.service;

import static org.jboss.seam.ScopeType.SESSION;
import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import ch.ess.issue.entity.User;

@SuppressWarnings({"unused", "unchecked"})
@Name("authenticator")
public class Authenticator {

  @In
  private Session hibernateSession;

  @Out(required = false, scope = SESSION)
  private User user;

  public boolean authenticate() {

    List<User> results = hibernateSession.createQuery(
        "select u from User u where u.username = #{identity.username} and u.password = #{identity.password}").list();

    if (results.size() == 0) {
      return false;
    }

    user = results.get(0);

    return true;

  }

}
