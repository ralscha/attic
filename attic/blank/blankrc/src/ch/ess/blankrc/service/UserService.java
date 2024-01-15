package ch.ess.blankrc.service;

import java.util.Set;

import ch.ess.blankrc.model.User;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public interface UserService extends BaseService {

  public Set getPermission(Integer id);

  public User find(String userName, String token);

  public User find(String userName);

  public User findExcludeId(String userName, Integer id);

}