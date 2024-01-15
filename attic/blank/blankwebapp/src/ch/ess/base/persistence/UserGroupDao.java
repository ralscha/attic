package ch.ess.base.persistence;

import java.util.List;

import ch.ess.base.model.UserGroup;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */
public interface UserGroupDao extends Dao<UserGroup> {

  List<UserGroup> find(String name);

}