package ch.ess.cal.service.impl;

import java.util.List;
import java.util.Map;

import ch.ess.cal.Constants;
import ch.ess.cal.model.Configuration;
import ch.ess.cal.persistence.ConfigurationDao;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/05/17 06:04:25 $ 
 * 
 * @spring.bean id="appConfig" init-method="initialize"
 */
public final class AppConfig extends AbstractConfig {

  public static final String MAIL_SMTPHOST = "mail.smtphost";
  public static final String MAIL_SMTPPORT = "mail.smtpport";
  public static final String MAIL_SMTPUSER = "mail.smtpuser";
  public static final String MAIL_SMTPPASSWORD = "mail.smtppassword";

  public static final String MAIL_SENDER = "mail.sender";
  public static final String PASSWORD_MINLENGTH = "password.minlength";
  public static final String SESSION_TIMEOUT = "session.timeout";

  public static final String DATE_FORMAT = "date.format";
  public static final String PARSE_DATE_FORMAT = "date.parse.format";
  public static final String DATE_TIME_FORMAT = "date.time.format";
  public static final String TIME_FORMAT = "time.format";

  private ConfigurationDao configurationDao;

  /**
   * @spring.property reflocal="configurationDao"   
   */
  public void setConfigurationDao(ConfigurationDao configurationDao) {
    this.configurationDao = configurationDao;
  }

  public void initialize() {

    List<Configuration> result = configurationDao.list();

    if (result.isEmpty()) {
      //einf�gen
      setProperty(PASSWORD_MINLENGTH, 5);
      setProperty(MAIL_SMTPHOST, "mail.ess.ch");
      setProperty(MAIL_SMTPPORT, 25);
      setProperty(MAIL_SENDER, "sr@ess.ch");
      setProperty(SESSION_TIMEOUT, 30);
      setProperty(DATE_FORMAT, "dd.MM.yyyy");
      setProperty(PARSE_DATE_FORMAT, "dd.MM.yy");
      setProperty(DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm");
      setProperty(TIME_FORMAT, "HH:mm");
      saveAll();

    } else {

      for (Configuration conf : result) {
        setProperty(conf.getName(), conf.getPropValue());
      }

    }

    //Init Constants
    Constants.setDefaultDateFormat(getStringProperty(DATE_FORMAT, "dd.MM.yyyy"), getStringProperty(PARSE_DATE_FORMAT,
        "dd.MM.yy"), getStringProperty(DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm"), getStringProperty(TIME_FORMAT, "HH:mm"));
  }

  @Override
  public void saveAll() {
    //delete all
    configurationDao.deleteAll();

    //insert all
    for (Map.Entry<String, String> entry : entries()) {
      saveConfigurationProperty(entry.getKey(), entry.getValue());
    }
  }

  private void saveConfigurationProperty(final String key, final String value) {
    Configuration configuration = new Configuration();
    configuration.setName(key);
    configuration.setPropValue(value);
    configurationDao.saveOrUpdate(configuration);
  }

}
