package ch.ess.base.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.base.model.UserConfiguration;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "userConfigurationDao", autowire = Autowire.BYTYPE)
public class UserConfigurationDao extends AbstractDao<UserConfiguration> {

  public UserConfigurationDao() {
    super(UserConfiguration.class);
  }
  
  @Transactional(readOnly = true)
  public List<UserConfiguration> findByUserId(final String userId) {
    return findByCriteria(Restrictions.eq("user.id", new Integer(userId)));
  }

  @Transactional(readOnly = true)
  public Config getUserConfig(final User user) {

    UserConfig userConfig = new UserConfig();

    List<UserConfiguration> result = findByUserId(user.getId().toString());

    for (UserConfiguration conf : result) {
      userConfig.setProperty(conf.getName(), conf.getPropValue());
    }

    if (userConfig.isEmpty()) {
      userConfig.setProperty(UserConfig.LOGIN_REMEMBER_NO, 1);
      userConfig.setProperty(UserConfig.LOGIN_REMEMBER_UNIT, TimeEnum.DAY.getValue());
      userConfig.setProperty(UserConfig.LOGIN_REMEMBER_SECONDS, 86400);
    }

    return userConfig;
  }

  @Transactional
  public void save(final User user, final Config userConfig) {
    Set<Map.Entry<String, String>> entries = userConfig.entries();
    for (Entry<String, String> entry : entries) {
      UserConfiguration userConfiguration = new UserConfiguration();
      userConfiguration.setUser(user);
      userConfiguration.setName(entry.getKey());
      userConfiguration.setPropValue(entry.getValue());
            
      save(userConfiguration);

    }
  }
}
