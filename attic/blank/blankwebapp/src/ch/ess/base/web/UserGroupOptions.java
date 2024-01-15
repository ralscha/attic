package ch.ess.base.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.model.UserGroup;
import ch.ess.base.persistence.UserGroupDao;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean id="userGroupOptions" singleton="false" autowire="byType"
 */
public class UserGroupOptions extends AbstractOptions {

  private UserGroupDao userGroupDao;

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<UserGroup> userGroupList = userGroupDao.list();

    for (UserGroup userGroup : userGroupList) {
      add(userGroup.getGroupName(), userGroup.getId().toString());
    }

    sort();
  }

}