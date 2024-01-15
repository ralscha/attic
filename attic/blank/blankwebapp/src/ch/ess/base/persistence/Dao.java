package ch.ess.base.persistence;

import java.util.List;

import ch.ess.base.model.BaseObject;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
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