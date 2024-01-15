package ch.ess.cal.web;

import java.util.Comparator;

public class ReverseComparator<T extends Comparable<T>> implements Comparator<T> {

  /** The comparator being decorated. */
  private Comparator<T> comparator;

  public ReverseComparator() {
    this(null);
  }

  public ReverseComparator(Comparator<T> comparator) {
    if (comparator != null) {
      this.comparator = comparator;
    } else {
      this.comparator = new ComparableComparator<T>();
    }
  }

  public int compare(T obj1, T obj2) {
    return comparator.compare(obj2, obj1);
  }

}
