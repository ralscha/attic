package ch.ess.blankrc.persistence;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:07 $
 */
public interface Dao {

  public Object get(Integer id) throws DataAccessException;

  public Object getAndInitCollections(Integer id, String[] collections) throws DataAccessException;

  public int getSize(Integer id, String collection) throws DataAccessException;

  public void save(Object baseObject) throws DataAccessException;

  public void delete(Integer id) throws DataAccessException;

  public void delete(Object baseObject) throws DataAccessException;

  public List list() throws DataAccessException;

  public List listAndInitCollections(String[] collections) throws DataAccessException;

  public void deleteAll() throws DataAccessException;
}