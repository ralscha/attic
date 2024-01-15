package ch.ess.blankrc.service.impl;

import java.util.Properties;

import ch.ess.blankrc.service.Config;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:06 $
 */
public abstract class BaseConfig extends Properties implements Config {

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

  public void setProperty(String key, Integer value) {
    setProperty(key, value.toString());
  }

  public void setProperty(String key, int value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(String key, long value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(String key, double value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(String key, boolean value) {
    setProperty(key, String.valueOf(value));
  }

  public Object setProperty(String key, String value) {
    return super.setProperty(key, value);
  }

  public void removeProperty(String key) {
    remove(key);
  }

  public void saveAll() {
    throw new UnsupportedOperationException();
  }
}