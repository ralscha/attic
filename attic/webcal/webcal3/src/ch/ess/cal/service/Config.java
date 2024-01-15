package ch.ess.cal.service;

import java.util.Map;
import java.util.Set;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $
 */
public interface Config {
  Boolean getBooleanProperty(String key);

  boolean getBooleanProperty(String key, boolean defaultValue);

  Double getDoubleProperty(String key) throws NumberFormatException;

  double getDoubleProperty(String key, double defaultValue) throws NumberFormatException;

  Integer getIntegerProperty(String key) throws NumberFormatException;

  Integer getIntegerProperty(String key, Integer defaultValue) throws NumberFormatException;

  Long getLongProperty(String key) throws NumberFormatException;

  int getIntegerProperty(String key, int defaultValue) throws NumberFormatException;

  String getStringProperty(String key);

  String getStringProperty(String key, String defaultValue);

  long getLongProperty(String key, long defaultValue) throws NumberFormatException;

  void setProperty(String key, Integer value);

  void setProperty(String key, int value);

  void setProperty(String key, long value);

  void setProperty(String key, double value);

  void setProperty(String key, boolean value);

  Object setProperty(String key, String value);

  void removeProperty(String key);

  void saveAll();

  Set<Map.Entry<String,String>> entries();
}
