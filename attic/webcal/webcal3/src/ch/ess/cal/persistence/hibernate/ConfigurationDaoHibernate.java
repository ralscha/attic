package ch.ess.cal.persistence.hibernate;

import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.model.Configuration;
import ch.ess.cal.persistence.ConfigurationDao;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/15 11:05:32 $
 * 
 * @spring.bean id="configurationDao"
 * @spring.property name="sessionFactory" ref="sessionFactory"
 * @spring.property name="clazz" value="ch.ess.cal.model.Configuration"
 */
public class ConfigurationDaoHibernate extends AbstractDaoHibernate<Configuration> implements ConfigurationDao {

  @Override
  @Transactional
  public boolean canDelete(final String id) {
    return false;
  }

}
