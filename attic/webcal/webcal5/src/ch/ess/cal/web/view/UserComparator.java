package ch.ess.cal.web.view;

import java.io.Serializable;
import java.util.Comparator;

import ch.ess.base.model.User;

public class UserComparator implements Comparator<User>, Serializable {

  public int compare(User u1, User u2) {

    String n1 = (u1.getShortName() != null) ? u1.getShortName() : u1.getFirstName() + u1.getName();
    String n2 = (u2.getShortName() != null) ? u2.getShortName() : u2.getFirstName() + u2.getName();

    return n1.compareTo(n2);
  }

}
