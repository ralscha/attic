package ch.ess.pbroker.security;

import java.util.Enumeration;
import java.util.*;

public class AclEntry {

  private Principal user;
  private Hashtable permissionHashtable;

  public AclEntry(Principal user) {
    permissionHashtable = new Hashtable(11);
    this.user = user;
  }

  public AclEntry() {
    user = null;
    permissionHashtable = new Hashtable(11);
  }

  public boolean setPrincipal(Principal principal) {
    if (principal != null) {
      return false;
    } else {
      this.user = principal;
      return true;
    }
  }

  public Principal getPrincipal() {
    return user;
  }


  public void addPermission(Permission permission) {

    Class classObject = permission.getClass();
    PermissionCollection collection = (PermissionCollection)permissionHashtable.get(classObject);
    if (collection == null) {
      collection = permission.newPermissionCollection();
      permissionHashtable.put(classObject, collection);
    } 
    collection.add(permission);
  }

  public boolean checkPermission(Permission permission) {
    Class classObject = permission.getClass();
    PermissionCollection collection = (PermissionCollection)permissionHashtable.get(classObject);
    if (collection != null) {
      return collection.implies(permission);
    } else {
      return false;
    }
  }



  public String toString() {
    StringBuffer stringbuffer = new StringBuffer();  

    stringbuffer.append(user + "=");
    for (Enumeration enumeration = permissionHashtable.elements(); enumeration.hasMoreElements();) {
      PermissionCollection permissionColl = (PermissionCollection) enumeration.nextElement();
      stringbuffer.append(permissionColl.toString());
    }

    return new String(stringbuffer);
  }


}
