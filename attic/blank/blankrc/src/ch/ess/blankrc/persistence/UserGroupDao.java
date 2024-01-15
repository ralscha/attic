package ch.ess.blankrc.persistence;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:07 $
 */
public interface UserGroupDao extends Dao {

  public List find(String name) throws DataAccessException;
}