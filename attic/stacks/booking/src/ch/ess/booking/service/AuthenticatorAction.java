package ch.ess.booking.service;

import static org.jboss.seam.ScopeType.SESSION;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import ch.ess.booking.entity.User;

@SuppressWarnings("all")
@Name("authenticator")
public class AuthenticatorAction {

  @In
  private Session hibernateSession;

  @Out(required = false, scope = SESSION)
  private User user;

  public boolean authenticate() {

    List results = hibernateSession.createQuery("select u from User u where u.username=#{identity.username} and u.password=#{identity.password}")
        .list();

    if (results.size() == 0) {
      return false;
    } else {
      user = (User)results.get(0);
      return true;
    }
  }

}
