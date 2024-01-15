package ch.ess.base.persistence;

import java.util.List;
import java.util.Set;

import ch.ess.base.model.User;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */
public interface UserDao extends Dao<User> {

  User findWithLoginToken(String token);

  User find(String userName, String token);

  User find(String userName);

  User findExcludeId(String userName, String id);

  List<User> findWithSearchText(String searchText, String userGroupId);

  List<User> listOrderByCantonAndName();
  
  Set<String> getPermission(String id);
  
  

}