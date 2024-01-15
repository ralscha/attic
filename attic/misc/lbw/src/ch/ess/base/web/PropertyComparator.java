package ch.ess.base.web;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyComparator implements Comparator, Serializable {

  private String property;
  private String property2;
  private Comparator comparator;

  public PropertyComparator(final String property) {
    this(property, ComparableComparator.getInstance());
  }

  public PropertyComparator(final String property, final String property2) {
    this(property, property2, ComparableComparator.getInstance());
  }

  public PropertyComparator(final String property, final Comparator comparator) {
    this(property, null, comparator);
  }

  public PropertyComparator(final String property, final String property2, final Comparator comparator) {
    setProperty(property);
    setProperty2(property2);
    this.comparator = comparator;
  }

  public String getProperty2() {
    return property2;
  }

  public final void setProperty2(final String property2) {
    this.property2 = property2;
  }

  public final void setProperty(final String property) {
    this.property = property;
  }

  public String getProperty() {
    return property;
  }

  public Comparator getComparator() {
    return comparator;
  }

  @SuppressWarnings("unchecked")
  public int compare(final Object o1, final Object o2) {

    try {
      Object value1 = PropertyUtils.getProperty(o1, property);
      Object value2 = PropertyUtils.getProperty(o2, property);
      int comp;
      
      if (value1 instanceof String) {        
        comp = comparator.compare(StringUtils.lowerCase((String)value1), StringUtils.lowerCase((String)value2));
      } else {
        comp = comparator.compare(value1, value2);
      }
      
      
      if (comp == 0) {

        if (property2 != null) {
          value1 = PropertyUtils.getProperty(o1, property2);
          value2 = PropertyUtils.getProperty(o2, property2);
          
          
          if (value1 instanceof String) {        
            comp = comparator.compare(StringUtils.lowerCase((String)value1), StringUtils.lowerCase((String)value2));
          } else {
            comp = comparator.compare(value1, value2);
          }
        }

        return comp;
      }
      return comp;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
