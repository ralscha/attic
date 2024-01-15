package ch.ess.blankrc.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.ess.blankrc.model.Permission;
import ch.ess.blankrc.model.User;
import ch.ess.blankrc.model.UserGroup;
import ch.ess.blankrc.persistence.UserDao;
import ch.ess.blankrc.service.UserService;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="userService"
 * @spring.property name="dao" reflocal="userDao"
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  public User find(String userName, String token) {
    return ((UserDao)getDao()).find(userName, token);
  }

  public User find(String userName) {
    return ((UserDao)getDao()).find(userName);
  }

  public User findExcludeId(String userName, Integer id) {
    return ((UserDao)getDao()).findExcludeId(userName, id);
  }

  public Set getPermission(Integer id) {
    User user = (User)get(id);

    Set permissionsSet = new HashSet();

    Set userGroups = user.getUserGroups();
    for (Iterator it = userGroups.iterator(); it.hasNext();) {
      UserGroup ug = (UserGroup)it.next();
      Set permissions = ug.getPermissions();
      for (Iterator it2 = permissions.iterator(); it2.hasNext();) {
        Permission permission = (Permission)it2.next();
        permissionsSet.add(permission.getPermission());
      }
    }

    return permissionsSet;

  }

}