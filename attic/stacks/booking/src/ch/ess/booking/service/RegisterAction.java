//$Id: RegisterAction.java 5579 2007-06-27 00:06:49Z gavin $
package ch.ess.booking.service;

import static org.jboss.seam.ScopeType.EVENT;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import ch.ess.booking.entity.User;

@SuppressWarnings("all")
@Scope(EVENT)
@Name("register")
public class RegisterAction {

  @In
  private User user;

  @In
  private Session hibernateSession;

  @In
  private FacesMessages facesMessages;

  private String verify;

  private boolean registered;

  public void register() {
    if (user.getPassword().equals(verify)) {
      List existing = hibernateSession.createQuery("select u.username from User u where u.username=#{user.username}").list();
      if (existing.size() == 0) {
        hibernateSession.persist(user);
        facesMessages.add("Successfully registered as #{user.username}");
        registered = true;
      } else {
        facesMessages.addToControl("username", "Username #{user.username} already exists");
      }
    } else {
      facesMessages.addToControl("verify", "Re-enter your password");
      verify = null;
    }
  }

  public void invalid() {
    facesMessages.add("Please try again");
  }

  public boolean isRegistered() {
    return registered;
  }

  public String getVerify() {
    return verify;
  }

  public void setVerify(String verify) {
    this.verify = verify;
  }

}
