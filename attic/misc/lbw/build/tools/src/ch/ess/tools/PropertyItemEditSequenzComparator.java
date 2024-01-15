package ch.ess.tools;

import java.util.Comparator;

public class PropertyItemEditSequenzComparator implements Comparator<PropertyItem> {

  public int compare(PropertyItem o1, PropertyItem o2) {
    return o1.getEditsequenz() - o2.getEditsequenz();
  }



}
