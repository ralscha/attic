package ch.ess.blank.resource;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.blank.db.Configuration;
import ch.ess.common.db.HibernateSession;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:51:17 $
 */
public final class AppConfig extends Properties {

  public static final String MAIL_SMTPHOST = "mail.smtphost";
  public static final String MAIL_SMTPPORT = "mail.smtpport";
  public static final String MAIL_SMTPUSER = "mail.smtpuser";
  public static final String MAIL_SMTPPASSWORD = "mail.smtppassword";

  public static final String MAIL_SENDER = "mail.sender";
  public static final String ERROR_MAIL_RECEIVER = "error.mail.receiver";
  public static final String PASSWORD_MINLENGTH = "password.minlength";
  public static final String SESSION_TIMEOUT = "session.timeout";

  private static AppConfig instance = new AppConfig();

  private AppConfig() {
    //no action
  }

  public static Boolean getBooleanProperty(String key) {

    String value = instance.getProperty(key);

    if (value != null) {
      return Boolean.valueOf(value.trim());
    }
    return null;
  }

  public static boolean getBooleanProperty(String key, boolean defaultValue) {

    String value = instance.getProperty(key);

    if (value != null) {

      return (Boolean.valueOf(value.trim()).booleanValue());
    }
    return defaultValue;

  }

  public static Double getDoubleProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Double.valueOf(value);
    }
    return null;

  }

  public static double getDoubleProperty(String key, double defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Double.parseDouble(value);
    }
    return defaultValue;

  }

  public static Integer getIntegerProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Integer.valueOf(value);
    }
    return null;

  }

  public static Long getLongProperty(String key) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Long.valueOf(value);
    }
    return null;

  }

  public static int getIntegerProperty(String key, int defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Integer.parseInt(value);
    }
    return defaultValue;

  }

  public static String getStringProperty(String key) {

    String value = instance.getProperty(key);

    if (value != null) {
      return (value.trim());
    }
    return null;

  }

  public static String getStringProperty(String key, String defaultValue) {

    String value = instance.getProperty(key);

    if (value != null) {
      return (value.trim());
    }
    return defaultValue;

  }

  public static long getLongProperty(String key, long defaultValue) throws NumberFormatException {

    String value = instance.getProperty(key);

    if (value != null) {
      return Long.parseLong(value);
    }
    return defaultValue;

  }

  public static void storeProperty(String key, int value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public static void storeProperty(String key, long value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public static void storeProperty(String key, double value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public static void storeProperty(String key, boolean value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public static void storeProperty(String key, Object value) throws HibernateException {
    storeProperty(key, value.toString());
  }

  public static void removeProperty(String key) throws HibernateException {
    HibernateSession.currentSession().delete("from Configuration as conf where conf.name = ?", key, Hibernate.STRING);
    instance.remove(key);
  }

  public static void storeProperty(String key, String value) throws HibernateException {

    List result = HibernateSession.currentSession().find("from Configuration as conf where conf.name = ?", key,
        Hibernate.STRING);

    if (!result.isEmpty()) {
      Configuration oldConf = (Configuration) result.get(0);
      oldConf.setPropValue(value);
    } else {
      Configuration newConf = new Configuration();
      newConf.setName(key);
      newConf.setPropValue(value);
      newConf.persist();
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

    Transaction tx = null;

    try {
      Session session = HibernateSession.currentSession();
      tx = session.beginTransaction();

      List result = session.find("from Configuration as conf");
      Iterator it = result.iterator();
      while (it.hasNext()) {
        Configuration conf = (Configuration) it.next();
        setProperty(conf.getName(), conf.getPropValue());
      }

      tx.commit();

      if (getProperty(MAIL_SMTPHOST) == null) {
        tx = session.beginTransaction();
        storeProperty(PASSWORD_MINLENGTH, 5);
        storeProperty(MAIL_SMTPHOST, "mail.ess.ch");
        storeProperty(MAIL_SMTPPORT, 25);
        storeProperty(MAIL_SENDER, "sr@ess.ch");
        storeProperty(ERROR_MAIL_RECEIVER, "sr@ess.ch");
        storeProperty(SESSION_TIMEOUT, 30);
        tx.commit();
      }

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
    } finally {
      HibernateSession.closeSession();
    }

  }
}