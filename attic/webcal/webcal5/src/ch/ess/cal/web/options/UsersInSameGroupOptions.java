package ch.ess.cal.web.options;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.model.Group;


@Option(id = "userInSameGroupOptions")
public class UsersInSameGroupOptions extends AbstractOptions {

  private UserDao userDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    String eventGroup = (String)getTagAttributes().get("eventGroup");
    String taskGroup = (String)getTagAttributes().get("taskGroup");
    String timeGroup = (String)getTagAttributes().get("timeGroup");
    
    User myUser = Util.getUser(request.getSession(), userDao);

    Set<Group> userGroups = myUser.getGroups();
    Set<Integer> userIdSet = new HashSet<Integer>();

    userIdSet.add(myUser.getId());
    add(myUser.getName() + " " + myUser.getFirstName(), myUser.getId().toString());

    for (Group group : userGroups) {
      Set<User> users = group.getUsers();
      for (User user : users) {
        if (user.isEnabled()) {
          if (!userIdSet.contains(user.getId())) {
            if (("true".equals(eventGroup) && group.getEventGroup()) || 
                ("true".equals(taskGroup) && group.getTaskGroup()) ||
                ("true".equals(timeGroup) && group.getTimeGroup()) ||
                (eventGroup == null && taskGroup == null) && timeGroup == null) {
              add(user.getName() + " " + user.getFirstName(), user.getId().toString());
              userIdSet.add(user.getId());
            }
          }
        }
      }

    }

    sort();
  }

}
