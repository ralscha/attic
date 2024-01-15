package ch.ess.cal.persistence;

import java.util.List;

import ch.ess.cal.model.UserGroup;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface UserGroupDao extends Dao<UserGroup> {

  List<UserGroup> find(String name);

}
