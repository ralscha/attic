package ch.ess.base.web;

import java.util.Comparator;

public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

  public int compare(T obj1, T obj2) {
    if ((obj1 == null) && (obj2 == null)) {
      return 0;
    } else if ((obj1 == null) && (obj2 != null)) {
      return -1;
    } else if ((obj1 != null) && (obj2 == null)) {
      return 1;
    } else {
      return obj1.compareTo(obj2);
    }
  }

}