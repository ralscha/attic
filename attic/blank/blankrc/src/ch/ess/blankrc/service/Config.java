package ch.ess.blankrc.service;

import java.util.Set;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2004/06/04 20:24:07 $
 */
public interface Config {
  public Boolean getBooleanProperty(String key);

  public boolean getBooleanProperty(String key, boolean defaultValue);

  public Double getDoubleProperty(String key) throws NumberFormatException;

  public double getDoubleProperty(String key, double defaultValue) throws NumberFormatException;

  public Integer getIntegerProperty(String key) throws NumberFormatException;

  public Integer getIntegerProperty(String key, Integer defaultValue) throws NumberFormatException;

  public Long getLongProperty(String key) throws NumberFormatException;

  public int getIntegerProperty(String key, int defaultValue) throws NumberFormatException;

  public String getStringProperty(String key);

  public String getStringProperty(String key, String defaultValue);

  public long getLongProperty(String key, long defaultValue) throws NumberFormatException;

  public void setProperty(String key, Integer value);

  public void setProperty(String key, int value);

  public void setProperty(String key, long value);

  public void setProperty(String key, double value);

  public void setProperty(String key, boolean value);

  public Object setProperty(String key, String value);

  public void removeProperty(String key);

  public void saveAll();

  public Set entrySet();
}