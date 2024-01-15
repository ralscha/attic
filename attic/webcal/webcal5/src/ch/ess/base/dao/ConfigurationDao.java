package ch.ess.base.dao;

import ch.ess.base.model.Configuration;


public class ConfigurationDao extends AbstractDao<Configuration> {

  public ConfigurationDao() {
    super(Configuration.class);
  }
  

  @Override
  public boolean canDelete(final Configuration configuration) {
    return false;
  }

}
