package ch.ess.cal.web.app;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.Util;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @spring.bean name="userInSameGroupOptions" singleton="false" 
 */
public class UsersInSameGroupOptions extends AbstractOptions {

  private UserDao userDao;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    User myUser = Util.getUser(request.getSession(), userDao);

    Set<Group> userGroups = myUser.getGroups();
    Set<Integer> userIdSet = new HashSet<Integer>();

    userIdSet.add(myUser.getId());
    add(myUser.getName() + " " + myUser.getFirstName(), myUser.getId().toString());

    for (Group group : userGroups) {
      Set<User> users = group.getUsers();
      for (User user : users) {
        if (!userIdSet.contains(user.getId())) {
          add(user.getName() + " " + user.getFirstName(), user.getId().toString());
          userIdSet.add(user.getId());
        }
      }

    }

    sort();
  }

}
