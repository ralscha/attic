package ch.ess.cal.web.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.UserGroup;
import ch.ess.cal.persistence.UserGroupDao;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @spring.bean id="userGroupOptions" singleton="false"
 */
public class UserGroupOptions extends AbstractOptions {

  private UserGroupDao userGroupDao;

  /**
   * @spring.property reflocal="userGroupDao"
   */
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
