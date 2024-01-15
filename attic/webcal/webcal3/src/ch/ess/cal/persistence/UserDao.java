package ch.ess.cal.persistence;

import java.util.List;
import java.util.Set;

import ch.ess.cal.model.User;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface UserDao extends Dao<User> {

  User findWithLoginToken(String token);

  User find(String userName, String token);

  User find(String userName);

  User findExcludeId(String userName, String id);

  List<User> findWithSearchText(String searchText, String userGroupId);

  Set<String> getPermission(String id);

}
