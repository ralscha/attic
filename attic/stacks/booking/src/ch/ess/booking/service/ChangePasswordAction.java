//$Id: ChangePasswordAction.java,v 1.22 2007/06/27 00:06:49 gavin Exp $
package ch.ess.booking.service;

import static org.jboss.seam.ScopeType.EVENT;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.faces.FacesMessages;
import ch.ess.booking.entity.User;

@Scope(EVENT)
@Name("changePassword")
@Restrict("#{identity.loggedIn}")
public class ChangePasswordAction {

  @In
  @Out
  private User user;

  @In
  private Session hibernateSession;

  private String verify;

  private boolean changed;

  @In
  private FacesMessages facesMessages;

  public void changePassword() {
    if (user.getPassword().equals(verify)) {
      user = (User)hibernateSession.merge(user);
      facesMessages.add("Password updated");
      changed = true;
    } else {
      facesMessages.add("passwords do not match");
      revertUser();
      verify = null;
    }
  }

  public boolean isChanged() {
    return changed;
  }

  private void revertUser() {
    user = (User)hibernateSession.get(User.class, user.getUsername());
  }

  public String getVerify() {
    return verify;
  }

  public void setVerify(String verify) {
    this.verify = verify;
  }

}
