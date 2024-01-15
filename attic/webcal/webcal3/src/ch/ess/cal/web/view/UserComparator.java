package ch.ess.cal.web.view;

import java.util.Comparator;

import ch.ess.cal.model.User;

public class UserComparator implements Comparator<User> {

  public int compare(User u1, User u2) {

    String n1 = (u1.getShortName() != null) ? u1.getShortName() : u1.getName();
    String n2 = (u2.getShortName() != null) ? u2.getShortName() : u2.getName();

    return n1.compareTo(n2);
  }

}
