package ch.ess.base.persistence.hibernate;

import ch.ess.base.model.Configuration;
import ch.ess.base.persistence.ConfigurationDao;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 11:31:19 $
 * 
 * @spring.bean id="configurationDao" autowire="byType"
 */
public class ConfigurationDaoHibernate extends AbstractDaoHibernate<Configuration> implements ConfigurationDao {

  @Override
  public Class<Configuration> getClazz() {
    return Configuration.class;
  }

  @Override
  public boolean canDelete(final String id) {
    return false;
  }

}