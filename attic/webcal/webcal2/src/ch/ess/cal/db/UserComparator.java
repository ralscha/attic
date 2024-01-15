package ch.ess.cal.db;

import java.util.Comparator;

public class UserComparator implements Comparator {

  public int compare(Object o1, Object o2) {
    if ((o1 instanceof User) && (o2 instanceof User)) {
      User u1 = (User)o1;
      User u2 = (User)o2;
      
      String n1 = (u1.getShortName() != null) ? u1.getShortName() : u1.getName();
      String n2 = (u2.getShortName() != null) ? u2.getShortName() : u2.getName();
      
      return n1.compareTo(n2);
    }
    return -1; 
  }

}
