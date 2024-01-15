package ch.ess.cal.web;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;

public class DynaBeanComparator<T extends Comparable<T>> implements Comparator<DynaBean>, Serializable {

  private String property;
  private String property2;
  private Comparator<T> comparator;

  public DynaBeanComparator(final String property) {
    this(property, new ComparableComparator<T>());
  }

  public DynaBeanComparator(final String property, final String property2) {
    this(property, property2, new ComparableComparator<T>());
  }

  public DynaBeanComparator(final String property, final Comparator<T> comparator) {
    this(property, null, comparator);
  }

  public DynaBeanComparator(final String property, final String property2, final Comparator<T> comparator) {
    setProperty(property);
    setProperty2(property2);
    this.comparator = comparator;
  }

  public String getProperty2() {
    return property2;
  }

  public void setProperty2(final String property2) {
    this.property2 = property2;
  }

  public void setProperty(final String property) {
    this.property = property;
  }

  public String getProperty() {
    return property;
  }

  public Comparator getComparator() {
    return comparator;
  }

  @SuppressWarnings("unchecked")
  public int compare(final DynaBean o1, final DynaBean o2) {

    try {
      T value1 = (T)PropertyUtils.getProperty(o1, property);
      T value2 = (T)PropertyUtils.getProperty(o2, property);
      int comp = comparator.compare(value1, value2);
      if (comp == 0) {

        if (property2 != null) {
          value1 = (T)PropertyUtils.getProperty(o1, property2);
          value2 = (T)PropertyUtils.getProperty(o2, property2);
          comp = comparator.compare(value1, value2);
        }

        return comp;
      }
      return comp;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
