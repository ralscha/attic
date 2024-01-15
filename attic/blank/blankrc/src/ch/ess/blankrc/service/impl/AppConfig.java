package ch.ess.blankrc.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.ess.blankrc.model.Configuration;
import ch.ess.blankrc.service.ConfigurationService;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $ 
 * 
 * @spring.bean id="appConfig" init-method="init"
 * @spring.property name="configurationService" reflocal="configurationService"
 */
public final class AppConfig extends BaseConfig {

  public static final String MAIL_SMTPHOST = "mail.smtphost";
  public static final String MAIL_SMTPPORT = "mail.smtpport";
  public static final String MAIL_SMTPUSER = "mail.smtpuser";
  public static final String MAIL_SMTPPASSWORD = "mail.smtppassword";

  public static final String MAIL_SENDER = "mail.sender";

  private ConfigurationService configurationService;

  public void setConfigurationService(ConfigurationService configurationService) {
    this.configurationService = configurationService;
  }

  public void init() {

    List result = configurationService.list();

    if (result.isEmpty()) {
      //einfügen
      setProperty(MAIL_SMTPHOST, "mail.ess.ch");
      setProperty(MAIL_SMTPPORT, 25);
      setProperty(MAIL_SENDER, "sr@ess.ch");

      saveAll();

    } else {
      Iterator it = result.iterator();
      while (it.hasNext()) {
        Configuration conf = (Configuration)it.next();
        setProperty(conf.getName(), conf.getPropValue());
      }

    }
  }

  public void saveAll() {
    //delete all
    configurationService.deleteAll();

    //insert all
    Set entries = entrySet();
    for (Iterator it = entries.iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      saveConfigurationProperty((String)entry.getKey(), (String)entry.getValue());
    }
  }

  private void saveConfigurationProperty(String key, String value) {
    Configuration configuration = new Configuration();
    configuration.setName(key);
    configuration.setPropValue(value);
    configurationService.save(configuration);
  }

}