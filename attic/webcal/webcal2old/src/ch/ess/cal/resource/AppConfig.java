package ch.ess.cal.resource;

import java.util.*;

import ch.ess.cal.db.*;

import net.sf.hibernate.*;


public final class AppConfig extends Properties {

  public static final String PASSWORD_MINLENGTH  = "password.minlength";
  public static final String MAIL_SMTPHOST = "mail.smtphost";
  public static final String MAIL_SENDER = "mail.sender";
  public static final String ERROR_MAIL_RECEIVER = "error.mail.receiver";
  public static final String START_OF_WEEK  = "start.of.week";
  public static final String TIMEZONE  = "timezone";
  public static final String MIN_DAYS_IN_FIRST_WEEK = "minimal.days";
  
  //public static final String UPLOAD_PATH = "upload.path";
  
  
  
  private static AppConfig instance = new AppConfig();

  private AppConfig() {    
  }
   
  public static Boolean getBooleanProperty(String key) {

    String value = instance.getProperty(key);

    if (value != null) {    
      return Boolean.valueOf(value.trim());
    } else {
      return null;
    }
  }

  public static boolean getBooleanProperty(String key, boolean defaultValue) {

    String value = instance.getProperty(key);

    if (value != null) {
      
      return (Boolean.valueOf(value.trim()).booleanValue());
    } else {
      return defaultValue;
    }
  }

  public static Double getDoubleProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Double.valueOf(value);
    } else {
      return null;
    }
  }

  public static double getDoubleProperty(String key, double defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Double.parseDouble(value);
    } else {
      return defaultValue;
    }
  }

  public static Integer getIntegerProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Integer.valueOf(value);
    } else {
      return null;
    }
  }


  public static Long getLongProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Long.valueOf(value);
    } else {
      return null;
    }
  }

  public static int getIntegerProperty(String key, int defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Integer.parseInt(value);
    } else {
      return defaultValue;
    }
  }

  public static String getStringProperty(String key) {

    String value = instance.getProperty(key);

    if (value != null) {
      return (value.trim());
    } else {
      return null;
    }
  }

  public static String getStringProperty(String key, String defaultValue) {

    String value = instance.getProperty(key);

    if (value != null) {
      return (value.trim());
    } else {
      return defaultValue;
    }
  }

  public static long getLongProperty(String key, long defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Long.parseLong(value);
    } else {
      return defaultValue;
    }
  }

  
  public static void storeProperty(String key, int value) {
    storeProperty(key, String.valueOf(value));
  }
  
  public static void storeProperty(String key, long value) {
    storeProperty(key, String.valueOf(value));
  }

  public static void storeProperty(String key, double value) {
    storeProperty(key, String.valueOf(value));
  }
  
  public static void storeProperty(String key, boolean value) {
    storeProperty(key, String.valueOf(value));
  }
  
  public static void storeProperty(String key, Object value) {
    storeProperty(key, value.toString());
  }
  
  public static void removeProperty(String key) {
    
    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      session.delete("from Configuration as conf where conf.name = ?", key, Hibernate.STRING);            
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
    
    
    instance.remove(key);
  }
  
  public static void storeProperty(String key, String value) {
    

    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      
      List result = session.find("from Configuration as conf where conf.name = ?", key, Hibernate.STRING);
      
      if (!result.isEmpty()) {
        Configuration oldConf = (Configuration)result.get(0);
        oldConf.setPropValue(value);  
      } else {
        Configuration newConf = new Configuration();
        newConf.setName(key);
        newConf.setPropValue(value);
        session.save(newConf);
      }
                  
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
        
    instance.setProperty(key, value);   
  }
  

  public static void init() {
    instance.initialize();
  }


  private void initialize() {
    clear();
    load();
  }
  
  private void load() {
    
            
    Session session = null;
    Transaction tx = null;
    
    try {
      session = HibernateManager.open();
      tx = session.beginTransaction();        
      
      List result = session.find("from Configuration as conf");
      Iterator it = result.iterator();
      while (it.hasNext()) {
        Configuration conf = (Configuration) it.next();        
        setProperty(conf.getName(), conf.getPropValue());            
      }            
                  
      tx.commit();  
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
    } finally {
      HibernateManager.finallyHandling(session);    
    }
    
              
    if (getProperty(MAIL_SMTPHOST) == null) {
      storeProperty(MAIL_SMTPHOST, "mail.ess.ch");
      storeProperty(MAIL_SENDER, "sr@ess.ch");
      storeProperty(ERROR_MAIL_RECEIVER, "sr@ess.ch");      

      storeProperty(TIMEZONE, TimeZone.getDefault().getID());
      storeProperty(START_OF_WEEK, Calendar.MONDAY);
      storeProperty(MIN_DAYS_IN_FIRST_WEEK, 4);
      
      storeProperty(PASSWORD_MINLENGTH, 7);
      
      //storeProperty(UPLOAD_PATH, "c:/temp");
    }
      
  }  
}
