package ch.ess.blankrc.persistence;

import org.springframework.dao.DataAccessException;

import ch.ess.blankrc.model.User;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:07 $
 */
public interface UserDao extends Dao {


  public User find(String userName, String token) throws DataAccessException;

  public User find(String userName) throws DataAccessException;

  public User findExcludeId(String userName, Integer id) throws DataAccessException;
}