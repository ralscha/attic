package ch.ess.base.persistence;

import java.util.List;

import ch.ess.base.model.User;
import ch.ess.base.model.UserConfiguration;
import ch.ess.base.service.Config;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 */
public interface UserConfigurationDao extends Dao<UserConfiguration> {

  List<UserConfiguration> find(String userId);

  Config getUserConfig(User user);

  void save(User user, Config userConfig);

}