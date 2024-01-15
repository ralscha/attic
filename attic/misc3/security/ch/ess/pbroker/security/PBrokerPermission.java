package ch.ess.pbroker.security;

import java.io.*;
import java.util.*;
import java.security.*;

public final class PBrokerPermission extends BasicPermission {

  public final static int READ = 0x1;
  public final static int WRITE = 0x2;
  public final static int ALL = READ | WRITE;
  public final static int NONE = 0x0;

  private int mask;

  private String actions;

  private void init(int mask) {

    if ((mask & ALL) != mask)
      throw new IllegalArgumentException("invalid actions mask");

    if (mask == NONE)
      throw new IllegalArgumentException("invalid actions mask");

    if (getName() == null)
      throw new NullPointerException("name can't be null");

    this.mask = mask;
  }


  public PBrokerPermission(String name, int action) {
    super(name, null);
    init(action);
  }

  public boolean implies(Permission p) {
    if (!(p instanceof PBrokerPermission))
      return false;

    PBrokerPermission that = (PBrokerPermission) p;

    return ((this.mask & that.mask) == that.mask) && super.implies(that);
  }


  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (! (obj instanceof PBrokerPermission))
      return false;

    PBrokerPermission that = (PBrokerPermission) obj;

    return (this.mask == that.mask) && (this.getName().equals(that.getName()));
  }

  public int hashCode() {
    return this.getName().hashCode();
  }


  public int getAction() {
    return mask;
  }

  public PermissionCollection newPermissionCollection() {
    return new PBrokerPermissionCollection();
  }

}

final class PBrokerPermissionCollection extends PermissionCollection {

  private Hashtable permissions;

  private boolean all_allowed;

  public PBrokerPermissionCollection() {
    permissions = new Hashtable();
    all_allowed = false;
  }

  public void add(Permission permission) {
    if (! (permission instanceof PBrokerPermission))
      throw new IllegalArgumentException("invalid permission: "+ permission);

    PBrokerPermission pp = (PBrokerPermission) permission;

    PBrokerPermission existing = (PBrokerPermission) permissions.get(pp.getName());

    if (existing != null) {
      int oldMask = existing.getAction();
      int newMask = pp.getAction();
      if (oldMask != newMask) {
        int effective = oldMask | newMask;
        permissions.put(pp.getName(), new PBrokerPermission(pp.getName(), effective));

      }
    } else {
      permissions.put(pp.getName(), permission);
    }

    if (!all_allowed) {
      if (pp.getName().equals("*"))
        all_allowed = true;
    }
  }

  public boolean implies(Permission permission) {
    if (! (permission instanceof PBrokerPermission))
      return false;

    PBrokerPermission pp = (PBrokerPermission) permission;
    PBrokerPermission x;

    int desired = pp.getAction();
    int effective = 0;

    // short circuit if the "*" Permission was added
    if (all_allowed) {
      x = (PBrokerPermission) permissions.get("*");
      if (x != null) {
        effective |= x.getAction();
        if ((effective & desired) == desired)
          return true;
      }
    }

    String name = pp.getName();

    x = (PBrokerPermission) permissions.get(name);

    if (x != null) {
      // we have a direct hit!
      effective |= x.getAction();
      if ((effective & desired) == desired)
        return true;
      else
        return false;
    }

    // work our way up the tree...
    int last, offset;

    offset = name.length() - 1;
    while ((last = name.lastIndexOf(".", offset)) != -1) {
      name = name.substring(0, last + 1) + "*";
      x = (PBrokerPermission) permissions.get(name);

      if (x != null) {
        effective |= x.getAction();
        if ((effective & desired) == desired)
          return true;
      }
      offset = last - 1;
    }

    return false;
  }

  public Enumeration elements() {
    return permissions.elements();
  }
}


