package ch.ess.base.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractConfig extends HashMap<String, String> implements Config {

  public Boolean getBooleanProperty(final String key) {

    String value = get(key);

    if (value != null) {
      return Boolean.valueOf(value.trim());
    }
    return null;

  }

  public boolean getBooleanProperty(final String key, final boolean defaultValue) {

    String value = get(key);

    if (value != null) {

      return (Boolean.valueOf(value.trim()));
    }
    return defaultValue;

  }

  public Double getDoubleProperty(final String key) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Double.valueOf(value);
    }
    return null;

  }

  public double getDoubleProperty(final String key, final double defaultValue) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Double.parseDouble(value);
    }
    return defaultValue;

  }

  public Integer getIntegerProperty(final String key) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Integer.valueOf(value);
    }
    return null;

  }

  public Integer getIntegerProperty(final String key, final Integer defaultValue) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Integer.valueOf(value);
    }
    return defaultValue;

  }

  public Long getLongProperty(final String key) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Long.valueOf(value);
    }
    return null;

  }

  public int getIntegerProperty(final String key, final int defaultValue) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Integer.parseInt(value);
    }
    return defaultValue;

  }

  public String getStringProperty(final String key) {

    String value = get(key);

    if (value != null) {
      return (value.trim());
    }
    return null;

  }

  public String getStringProperty(final String key, final String defaultValue) {

    String value = get(key);

    if (value != null) {
      return (value.trim());
    }
    return defaultValue;

  }

  public long getLongProperty(final String key, final long defaultValue) throws NumberFormatException {

    String value = get(key);

    if (value != null) {
      return Long.parseLong(value);
    }
    return defaultValue;

  }

  public void setProperty(final String key, final Integer value) {
    setProperty(key, value.toString());
  }

  public void setProperty(final String key, final int value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(final String key, final long value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(final String key, final double value) {
    setProperty(key, String.valueOf(value));
  }

  public void setProperty(final String key, final boolean value) {
    setProperty(key, String.valueOf(value));
  }

  public Object setProperty(final String key, final String value) {
    return super.put(key, value);
  }

  public void removeProperty(final String key) {
    remove(key);
  }

  public void saveAll() {
    throw new UnsupportedOperationException();
  }

  public Set<Map.Entry<String,String>> entries() {
    return entrySet();
  }
  
}
