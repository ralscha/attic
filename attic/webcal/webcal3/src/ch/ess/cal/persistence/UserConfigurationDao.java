package ch.ess.cal.persistence;

import java.util.List;

import ch.ess.cal.model.User;
import ch.ess.cal.model.UserConfiguration;
import ch.ess.cal.service.Config;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public interface UserConfigurationDao extends Dao<UserConfiguration> {

  List<UserConfiguration> find(String userId);

  Config getUserConfig(User user);

  void save(User user, Config userConfig);

}
