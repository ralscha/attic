package ch.ess.pbroker.security;

import java.util.*;

public abstract class PermissionCollection {


  public abstract void add(Permission permission);
  public abstract boolean implies(Permission permission);

  public abstract Enumeration elements();

  public String toString() {
    Enumeration enum = elements();
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString() + " (\n");
    while (enum.hasMoreElements()) {
      try {
        sb.append(" ");
        sb.append(enum.nextElement().toString());
        sb.append("\n");
      } catch (NoSuchElementException e) {
        // ignore
      }
    }
    sb.append(")\n");
    return sb.toString();
  }
}
