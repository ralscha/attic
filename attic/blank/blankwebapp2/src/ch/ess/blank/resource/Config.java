package ch.ess.blank.resource;

import java.util.Properties;

import net.sf.hibernate.HibernateException;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:51:17 $
 */
public abstract class Config extends Properties {

  public Boolean getBooleanProperty(String key) {

    String value = getProperty(key);

    if (value != null) {
      return Boolean.valueOf(value.trim());
    }
    return null;

  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {

    String value = getProperty(key);

    if (value != null) {

      return (Boolean.valueOf(value.trim()).booleanValue());
    }
    return defaultValue;

  }

  public Double getDoubleProperty(String key) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Double.valueOf(value);
    }
    return null;

  }

  public double getDoubleProperty(String key, double defaultValue) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Double.parseDouble(value);
    }
    return defaultValue;

  }

  public Integer getIntegerProperty(String key) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Integer.valueOf(value);
    }
    return null;

  }

  public Integer getIntegerProperty(String key, Integer defaultValue) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Integer.valueOf(value);
    }
    return defaultValue;

  }

  public Long getLongProperty(String key) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Long.valueOf(value);
    }
    return null;

  }

  public int getIntegerProperty(String key, int defaultValue) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Integer.parseInt(value);
    }
    return defaultValue;

  }

  public String getStringProperty(String key) {

    String value = getProperty(key);

    if (value != null) {
      return (value.trim());
    }
    return null;

  }

  public String getStringProperty(String key, String defaultValue) {

    String value = getProperty(key);

    if (value != null) {
      return (value.trim());
    }
    return defaultValue;

  }

  public long getLongProperty(String key, long defaultValue) throws NumberFormatException {

    String value = getProperty(key);

    if (value != null) {
      return Long.parseLong(value);
    }
    return defaultValue;

  }

  public void storeProperty(String key, int value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, long value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, double value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, boolean value) throws HibernateException {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, Object value) throws HibernateException {
    storeProperty(key, value.toString());
  }

  protected void initialize() throws HibernateException {
    clear();
    load();
  }

  protected abstract void load() throws HibernateException;

  public abstract void storeProperty(String key, String value) throws HibernateException;

  public abstract void removeProperty(String key) throws HibernateException;
}