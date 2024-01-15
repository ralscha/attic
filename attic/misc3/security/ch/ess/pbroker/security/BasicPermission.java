package ch.ess.pbroker.security;

import java.util.*;

public abstract class BasicPermission extends Permission {

  private boolean wildcard;
  private String path;


  private void init(String name) {
    if (name == null)
      throw new NullPointerException("name can't be null");

    if (name.equals("")) {
      throw new IllegalArgumentException("name can't be empty");
    }

    if (name.endsWith(".*") || name.equals("*")) {
      wildcard = true;
      if (name.length() == 1) {
        path = "";
      } else {
        path = name.substring(0, name.length() - 1);
      }
    } else {
      path = name;
    }
  }

  public BasicPermission(String name) {
    super(name);
    init(name);
  }

  public BasicPermission(String name, String actions) {
    super(name);
    init(name);
  }

  public boolean implies(Permission p) {
    if ((p == null) || (p.getClass() != getClass()))
      return false;

    BasicPermission that = (BasicPermission) p;

    if (this.wildcard) {
      if (that.wildcard)// one wildcard can imply another

        return that.path.startsWith(path);
      else // make sure ap.path is longer so a.b.* doesn't imply a.b

        return (that.path.length() > this.path.length()) &&
               that.path.startsWith(this.path);
    } else {
      if (that.wildcard) {
        // a non-wildcard can't imply a wildcard
        return false;
      } else {
        return this.path.equals(that.path);
      }
    }
  }


  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    
    if (obj == null)
      return false;

    if (! (obj instanceof BasicPermission))
      return false;

    BasicPermission bp = (BasicPermission) obj;

    return getName().equals(bp.getName());
  }

  public int hashCode() {
    return this.getName().hashCode();
  }

  public String getActions() {
    return "";
  }


  public PermissionCollection newPermissionCollection() {
    return new BasicPermissionCollection();
  }

}


final class BasicPermissionCollection extends PermissionCollection {

  private Hashtable permissions;
  private boolean all_allowed; // true if "*" is in the collection

  public BasicPermissionCollection() {
    permissions = new Hashtable(11);
    all_allowed = false;
  }

  public void add(Permission permission) {
    if (! (permission instanceof BasicPermission))
      throw new IllegalArgumentException("invalid permission: "+ permission);

    BasicPermission bp = (BasicPermission) permission;

    permissions.put(bp.getName(), permission);
    if (!all_allowed) {
      if (bp.getName().equals("*"))
        all_allowed = true;
    }
  }

  public boolean implies(Permission permission) {
    if (! (permission instanceof BasicPermission))
      return false;

    BasicPermission bp = (BasicPermission) permission;

    // short circuit if the "*" Permission was added
    if (all_allowed)
      return true;

    // strategy:
    // Check for full match first. Then work our way up the
    // path looking for matches on a.b..*

    String path = bp.getName();

    Permission x = (Permission) permissions.get(path);

    if (x != null) {
      // we have a direct hit!
      return x.implies(permission);
    }

    // work our way up the tree...
    int last, offset;

    offset = path.length() - 1;

    while ((last = path.lastIndexOf(".", offset)) != -1) {

      path = path.substring(0, last + 1) + "*";
      x = (Permission) permissions.get(path);

      if (x != null) {
        return x.implies(permission);
      }
      offset = last - 1;
    }

    // we don't have to check for "*" as it was already checked
    // at the top (all_allowed), so we just return false
    return false;
  }

  public Enumeration elements() {
    return permissions.elements();
  }
}
