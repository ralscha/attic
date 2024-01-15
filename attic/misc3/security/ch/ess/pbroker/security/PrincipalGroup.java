package ch.ess.pbroker.security;

import java.util.Enumeration;
import java.util.Vector;

public class PrincipalGroup extends Principal {
  private Vector groupMembers;

  public PrincipalGroup(String name) {
    super(name);
    groupMembers = new Vector(50, 100);
  }

  public boolean addMember(Principal principal) {
    if (groupMembers.contains(principal))
      return false;

    if (getName().equals(principal.toString())) {
      throw new IllegalArgumentException();
    } else {
      groupMembers.addElement(principal);
      return true;
    }
  }

  public boolean removeMember(Principal principal) {
    return groupMembers.removeElement(principal);
  }

  public Enumeration members() {
    return groupMembers.elements();
  }

  public boolean equals(PrincipalGroup group1) {
    return getName().equals(group1.getName());
  }

  public boolean isMember(Principal principal) {
    if (groupMembers.contains(principal)) {
      return true;
    } else {
      return false;
    }
  }


}
