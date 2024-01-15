package ch.ess.lbw.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.lbw.model.UserWerk;

@Option(id = "userWerkOptions")
public class UserWerkOptions extends AbstractOptions {

  private UserDao userDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }


  @Override
  public void init(final HttpServletRequest request) {
    User user = Util.getUser(request.getSession(), userDao);
    
    for (UserWerk werk : user.getUserWerke()) {
      add(werk.getWerk().getName(), werk.getWerk().getId().toString());
    }

    sort();
  }

}