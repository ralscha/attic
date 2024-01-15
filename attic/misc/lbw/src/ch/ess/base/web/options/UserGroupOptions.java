package ch.ess.base.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.UserGroup;

@Option(id = "userGroupOptions")
public class UserGroupOptions extends AbstractOptions {

  private UserGroupDao userGroupDao;

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<UserGroup> userGroupList = userGroupDao.findAll();

    for (UserGroup userGroup : userGroupList) {
      add(userGroup.getGroupName(), userGroup.getId().toString());
    }

    sort();
  }

}