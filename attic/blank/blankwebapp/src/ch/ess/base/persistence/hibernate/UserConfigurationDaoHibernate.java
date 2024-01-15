package ch.ess.base.persistence.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.enums.TimeEnum;
import ch.ess.base.model.User;
import ch.ess.base.model.UserConfiguration;
import ch.ess.base.persistence.UserConfigurationDao;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $
 * 
 * @spring.bean id="userConfigurationDao" autowire="byType" 
 */
public class UserConfigurationDaoHibernate extends AbstractDaoHibernate<UserConfiguration> implements UserConfigurationDao {
    
  @Override
  public Class<UserConfiguration> getClazz() {
    return UserConfiguration.class;
  }

  @Transactional(readOnly=true)
  @SuppressWarnings("unchecked") 
  public List<UserConfiguration> find(final String userId) {
    HibernateCallback callback = new HibernateCallback() {

      public Object doInHibernate(Session session) throws HibernateException {

        Query queryObject = session.createQuery("from UserConfiguration as conf where conf.user = :user");
        queryObject.setInteger("user", new Integer(userId));
        return queryObject.list();

      }
    };

    return getHibernateTemplate().executeFind(callback);
  }

  @Transactional(readOnly=true)
  public Config getUserConfig(final User user) {

    UserConfig userConfig = new UserConfig();

    List<UserConfiguration> result = find(user.getId().toString());

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
    deleteAll();

    Set<Map.Entry<String,String>> entries = userConfig.entries();
    for (Iterator<Map.Entry<String,String>> it = entries.iterator(); it.hasNext();) {
      Map.Entry<String,String> entry = it.next();

      UserConfiguration userConfiguration = new UserConfiguration();
      userConfiguration.setUser(user);
      userConfiguration.setName(entry.getKey());
      userConfiguration.setPropValue(entry.getValue());

      saveOrUpdate(userConfiguration);

    }

  }

}