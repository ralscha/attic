package @packageProject@.service;

import java.util.List;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.Identity;
import @packageProject@.entity.Role;
import @packageProject@.entity.User;

@Name("userHome")
@Restrict("#{identity.loggedIn}")
@TideEnabled
public class UserHome extends EntityHome<User> {

  @In
  private Identity identity;

  @SuppressWarnings("unchecked")
  @Transactional
  public List<Role> findAllRoles() {
    return getEntityManager().createQuery("select r from Role r").getResultList();
  }

  private User getLoggedInUser() {
    User user = (User)getEntityManager().createQuery("select u from User u where u.userName = :userName").setParameter("userName",
        identity.getCredentials().getUsername()).getSingleResult();

    return user;
  }

  @Transactional
  public void updateLocale(String newLocale) {
    getLoggedInUser().setLocale(newLocale);
  }

  @Transactional
  public void updatePassword(String newPassword) {
    getLoggedInUser().setPasswordHash(newPassword);
  }

  @Transactional
  public String getLocale() {
    return getLoggedInUser().getLocale();
  }

}
