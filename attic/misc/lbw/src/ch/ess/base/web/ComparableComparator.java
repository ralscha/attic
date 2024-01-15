package ch.ess.base.web;

import java.util.Comparator;


public class ComparableComparator<T extends Comparable<T>> implements Comparator<T> {

  private static final ComparableComparator instance = new ComparableComparator();

  public static ComparableComparator getInstance() {
    return instance;
  }
  
  private ComparableComparator() {
    //no external instance
  }
  
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