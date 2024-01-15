package ch.ess.tools;

import java.util.Comparator;

public class PropertyItemListSequenzComparator implements Comparator<PropertyItem> {

  public int compare(PropertyItem o1, PropertyItem o2) {
    return o1.getListsequenz() - o2.getListsequenz();
  }



}
