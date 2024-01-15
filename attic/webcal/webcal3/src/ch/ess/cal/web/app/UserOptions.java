package ch.ess.cal.web.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean name="userOptions" singleton="false" 
 */
public class UserOptions extends AbstractOptions {

  private UserDao userDao;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<User> userList = userDao.list();

    for (User user : userList) {
      add(user.getName() + " " + user.getFirstName(), user.getId().toString());
    }

    sort();
  }

}
