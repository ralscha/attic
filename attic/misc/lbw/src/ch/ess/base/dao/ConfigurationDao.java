package ch.ess.base.dao;

import ch.ess.base.model.Configuration;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "configurationDao", autowire = Autowire.BYTYPE)
public class ConfigurationDao extends AbstractDao<Configuration> {

  public ConfigurationDao() {
    super(Configuration.class);
  }
  

  @Override
  public boolean canDelete(final Configuration configuration) {
    return false;
  }

}