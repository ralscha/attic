package ch.ess.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.dao.ConfigurationDao;
import ch.ess.base.model.Configuration;

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
  
  public static final String LICENSE = "license";
  public static final String GOOGLE_API_KEY = "google.api.key";
  
  public static final String DEFAULT_APPLICATION = "attribute.defaultApp";
  public static final String TIME_SETTING = "attribute.timeSetting";
  public static final String DEFAULT_HOUR_RATE = "attribute.defaultHourRate";
  public static final String NEW_CUSTOMER = "attribute.newCustomer";
  public static final String NEW_ORDER = "attribute.newOrder";
  public static final String ALLOW_ACTIVITY = "attribute.allowActivity";
  public static final String OPEN_CUSTOMER_TAG = "attribute.openCustomerTag";
  public static final String CLOSE_CUSTOMER_TAG = "attribute.closeCustomerTag";
  
  public static final String CUSTOMER_DE = "attribute.labelCustomerDe";
  public static final String CUSTOMER_EN = "attribute.labelCustomerEn";
  public static final String PROJECT_DE = "attribute.labelProjectDe";
  public static final String PROJECT_EN = "attribute.labelProjectEn";
  public static final String TASK_DE = "attribute.labelTaskDe";
  public static final String TASK_EN = "attribute.labelTaskEn";
  
  private ConfigurationDao configurationDao;

  public void setConfigurationDao(ConfigurationDao configurationDao) {
    this.configurationDao = configurationDao;
  }

  public void initialize() {
	  
    List<Configuration> result = configurationDao.findAll();
    
    if (result.isEmpty()) {
      //einfügen
      setProperty(PASSWORD_MINLENGTH, 5);
      setProperty(MAIL_SMTPHOST, "mail.ess.ch");
      setProperty(MAIL_SMTPPORT, 25);
      setProperty(MAIL_SENDER, "sr@ess.ch");
      setProperty(SESSION_TIMEOUT, 30);
      setProperty(DATE_FORMAT, "dd.MM.yyyy");
      setProperty(PARSE_DATE_FORMAT, "dd.MM.yy");
      setProperty(DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm");
      setProperty(TIME_FORMAT, "HH:mm");
      setProperty(DEFAULT_APPLICATION, "Zeiterfassung");
      setProperty(TIME_SETTING, "Aktueller Tag");
      setProperty(NEW_ORDER, false);
      setProperty(DEFAULT_HOUR_RATE, "150");
      setProperty(ALLOW_ACTIVITY, false);
      setProperty(OPEN_CUSTOMER_TAG, "<");
      setProperty(CLOSE_CUSTOMER_TAG, "> ");
      setProperty(CUSTOMER_DE, "Kunde");
      setProperty(CUSTOMER_EN, "Customer");
      setProperty(PROJECT_DE, "Projekt");
      setProperty(PROJECT_EN, "Project");
      setProperty(TASK_DE, "Tätigkeit");
      setProperty(TASK_EN, "Task");
      
      saveAll();

    } 
    else {
    	ArrayList<String> updateList = new ArrayList<String>();
    	
      for (Configuration conf : result) {
        setProperty(conf.getName(), conf.getPropValue());
        updateList.add(conf.getName());
      }

      // wenn Appliktion bereits ohne diese Erweiterungen betrieben wurde...
	  if(!updateList.contains(DEFAULT_APPLICATION)){
          setProperty(DEFAULT_APPLICATION, "Zeiterfassung");
    	  saveConfigurationProperty(DEFAULT_APPLICATION, "Zeiterfassung");
	  }
	  if(!updateList.contains(TIME_SETTING)){
          setProperty(TIME_SETTING, "1"); //1 für aktuellen Wochentag
    	  saveConfigurationProperty(TIME_SETTING, "1");
	  }
	  if(!updateList.contains(NEW_CUSTOMER)){
          setProperty(NEW_CUSTOMER, false);
    	  saveConfigurationProperty(NEW_CUSTOMER, String.valueOf(false));
	  }
	  if(!updateList.contains(DEFAULT_HOUR_RATE)){
          setProperty(DEFAULT_HOUR_RATE, "150");
    	  saveConfigurationProperty(DEFAULT_HOUR_RATE, "150");
	  }
	  if(!updateList.contains(NEW_ORDER)){
          setProperty(NEW_ORDER, false);
    	  saveConfigurationProperty(NEW_ORDER, String.valueOf(false));
	  }
	  if(!updateList.contains(ALLOW_ACTIVITY)){
          setProperty(ALLOW_ACTIVITY, false);
    	  saveConfigurationProperty(ALLOW_ACTIVITY, String.valueOf(false));
	  }
	  if(!updateList.contains(OPEN_CUSTOMER_TAG)){
          setProperty(OPEN_CUSTOMER_TAG, "<");
    	  saveConfigurationProperty(OPEN_CUSTOMER_TAG, "<");
	  }
	  if(!updateList.contains(CLOSE_CUSTOMER_TAG)){
          setProperty(CLOSE_CUSTOMER_TAG, "> ");
    	  saveConfigurationProperty(CLOSE_CUSTOMER_TAG, "> ");
	  }
	  if(!updateList.contains(CUSTOMER_DE)){
          setProperty(CUSTOMER_DE, "Kunde");
    	  saveConfigurationProperty(CUSTOMER_DE, "Kunde");
	  }
	  if(!updateList.contains(PROJECT_DE)){
          setProperty(PROJECT_DE, "Projekt");
    	  saveConfigurationProperty(PROJECT_DE, "Projekt");
	  }
	  if(!updateList.contains(TASK_DE)){
          setProperty(TASK_DE, "Tätigkeit");
    	  saveConfigurationProperty(TASK_DE, "Tätigkeit");
	  }
	  if(!updateList.contains(CUSTOMER_EN)){
          setProperty(CUSTOMER_EN, "Customer");
    	  saveConfigurationProperty(CUSTOMER_EN, "Customer");
	  }
	  if(!updateList.contains(PROJECT_EN)){
          setProperty(PROJECT_EN, "Project");
    	  saveConfigurationProperty(PROJECT_EN, "Project");
	  }
	  if(!updateList.contains(TASK_EN)){
          setProperty(TASK_EN, "Task");
    	  saveConfigurationProperty(TASK_EN, "Task");
	  }     
    }

    //Init Constants
    Constants.setDefaultDateFormat(getStringProperty(DATE_FORMAT, "dd.MM.yyyy"), getStringProperty(PARSE_DATE_FORMAT,
        "dd.MM.yy"), getStringProperty(DATE_TIME_FORMAT, "dd.MM.yyyy HH:mm"), getStringProperty(TIME_FORMAT, "HH:mm"));

    String license = getStringProperty(LICENSE);
    if (StringUtils.isNotBlank(license)) {
      Constants.setFeatures(license);    
    }
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
    configurationDao.save(configuration);
  }
}
