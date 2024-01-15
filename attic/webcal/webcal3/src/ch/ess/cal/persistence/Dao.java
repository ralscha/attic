package ch.ess.cal.persistence;

import java.util.List;

import ch.ess.cal.model.BaseObject;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface Dao<T extends BaseObject> {

  T get(String id);

  void saveOrUpdate(T baseObject);

  void delete(String id);

  void delete(T baseObject);

  boolean deleteCond(String id);

  boolean deleteCond(T baseObject);

  boolean canDelete(String id);

  List<T> list();

  void deleteAll();

}
