package ch.ess.base.persistence.hibernate;

import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:22 $
 */
public class AppNamingStrategy implements NamingStrategy {

  private String prefix;

  public void setPrefix(final String prefix) {
    this.prefix = prefix;
  }

  /**
   * Return the unqualified class name, mixed case converted to underscores
   */
  public String classToTableName(final String className) {
    return prefix + addUnderscores(StringHelper.unqualify(className));
  }

  /**
   * Return the full property path with underscore seperators, mixed case
   * converted to underscores
   */
  public String propertyToColumnName(final String propertyName) {
    return addUnderscores(propertyName);
  }

  /**
   * Convert mixed case to underscores
   */
  public String tableName(final String tableName) {
    return prefix + addUnderscores(tableName);
  }

  /**
   * Convert mixed case to underscores
   */
  public String columnName(final String columnName) {
    return addUnderscores(columnName);
  }

  /**
   * Return the full property path prefixed by the unqualified class name, with
   * underscore seperators, mixed case converted to underscores
   */
  public String propertyToTableName(final String className, final String propertyName) {
    return prefix + classToTableName(className) + '_' + propertyToColumnName(propertyName);
  }

  private String addUnderscores(final String name) {
    StringBuilder buf = new StringBuilder(name.replace('.', '_'));
    for (int i = 1; i < buf.length() - 1; i++) {
      if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
        buf.insert(i++, '_');
      }
    }
    return buf.toString().toLowerCase();
  }

}