package ch.ess.blankrc.service.impl;

import ch.ess.blankrc.persistence.ConfigurationDao;
import ch.ess.blankrc.service.ConfigurationService;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 * 
 * @spring.bean id="configurationService"
 * @spring.property name="dao" reflocal="configurationDao"
 */
public class ConfigurationServiceImpl extends BaseServiceImpl implements ConfigurationService {

  public void deleteAll() {
    ((ConfigurationDao)getDao()).deleteAll();
  }

}