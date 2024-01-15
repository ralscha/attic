package ch.ess.cal.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.options.AbstractOptions;


@Option(id = "userOptions")
public class UserOptions extends AbstractOptions {

  private UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    String permission = (String)getTagAttributes().get("permission");
    
    List<User> userList;
    if (StringUtils.isBlank(permission)) {
      userList = userDao.findAll();
    } else {
      userList = userDao.findWithPermission(permission);
    }

    for (User user : userList) {
      if (user.isEnabled()) {
        add(user.getName() + " " + user.getFirstName(), user.getId().toString());
      }
    }

    sort();
  }

}
