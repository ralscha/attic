package ch.ess.cal.resource;

import java.util.Properties;

public abstract class Config extends Properties {

  public Boolean getBooleanProperty(String key) {
  
    String value = getProperty(key);
  
    if (value != null) {    
      return Boolean.valueOf(value.trim());
    } else {
      return null;
    }
  }

  public boolean getBooleanProperty(String key, boolean defaultValue) {
  
    String value = getProperty(key);
  
    if (value != null) {
      
      return (Boolean.valueOf(value.trim()).booleanValue());
    } else {
      return defaultValue;
    }
  }

  public Double getDoubleProperty(String key) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Double.valueOf(value);
    } else {
      return null;
    }
  }

  public double getDoubleProperty(String key, double defaultValue) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Double.parseDouble(value);
    } else {
      return defaultValue;
    }
  }

  public Integer getIntegerProperty(String key) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Integer.valueOf(value);
    } else {
      return null;
    }
  }

  public Long getLongProperty(String key) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Long.valueOf(value);
    } else {
      return null;
    }
  }

  public int getIntegerProperty(String key, int defaultValue) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Integer.parseInt(value);
    } else {
      return defaultValue;
    }
  }

  public String getStringProperty(String key) {
  
    String value = getProperty(key);
  
    if (value != null) {
      return (value.trim());
    } else {
      return null;
    }
  }

  public String getStringProperty(String key, String defaultValue) {
  
    String value = getProperty(key);
  
    if (value != null) {
      return (value.trim());
    } else {
      return defaultValue;
    }
  }

  public long getLongProperty(String key, long defaultValue) throws NumberFormatException {
  
    String value = getProperty(key);
  
    if (value != null) {
      return Long.parseLong(value);
    } else {
      return defaultValue;
    }
  }

  public void storeProperty(String key, int value) {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, long value) {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, double value) {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, boolean value) {
    storeProperty(key, String.valueOf(value));
  }

  public void storeProperty(String key, Object value) {
    storeProperty(key, value.toString());
  }

  protected void initialize() {
    clear();
    load();
  }

  protected abstract void load();
  public abstract void storeProperty(String key, String value) ;
  public abstract void removeProperty(String key) ;
}
