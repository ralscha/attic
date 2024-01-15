package ch.ess.common.db;

import net.sf.hibernate.cfg.NamingStrategy;
import net.sf.hibernate.util.StringHelper;

/**
 * @author sr
 */
public class AppNamingStrategy implements NamingStrategy {

  private final static String APP_NAME = "blank_";

  /**
   * Return the unqualified class name, mixed case converted to underscores
   */
  public String classToTableName(String className) {
    return APP_NAME + addUnderscores(StringHelper.unqualify(className));
  }

  /**
   * Return the full property path with underscore seperators, mixed case
   * converted to underscores
   */
  public String propertyToColumnName(String propertyName) {
    return addUnderscores(propertyName);
  }

  /**
   * Convert mixed case to underscores
   */
  public String tableName(String tableName) {
    return APP_NAME + addUnderscores(tableName);
  }

  /**
   * Convert mixed case to underscores
   */
  public String columnName(String columnName) {
    return addUnderscores(columnName);
  }

  /**
   * Return the full property path prefixed by the unqualified class name, with
   * underscore seperators, mixed case converted to underscores
   */
  public String propertyToTableName(String className, String propertyName) {
    return APP_NAME + classToTableName(className) + '_' + propertyToColumnName(propertyName);
  }

  private String addUnderscores(String name) {
    StringBuffer buf = new StringBuffer(name.replace('.', '_'));
    for (int i = 1; i < buf.length() - 1; i++) {
      if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
        buf.insert(i++, '_');
      }
    }
    return buf.toString().toLowerCase();
  }

}