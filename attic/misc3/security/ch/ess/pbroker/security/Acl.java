
package ch.ess.pbroker.security;

import java.util.*;

public class Acl  {

  private Hashtable aclEntryTable;
  private String aclName;
  private boolean groupPermissions;

  // groupPermission: true  = nur Gruppen
  //                  false = nur Users

  public Acl(String s, boolean groupPermissions) {
    aclEntryTable = new Hashtable();
    aclName = s;
    this.groupPermissions = groupPermissions;
  }

  public void setName(String s) {
      aclName = s;
  }

  public String getName() {
    return aclName;
  }

  public synchronized boolean addEntry(AclEntry aclentry) {
    Principal principal1 = aclentry.getPrincipal(); 
    if (aclEntryTable.get(principal1) != null) {
      return false;
    } else {
      aclEntryTable.put(principal1, aclentry);
      return true;
    }
  }

  public synchronized boolean removeEntry(AclEntry aclentry)  {
      Principal principal1 = aclentry.getPrincipal(); 
      Object obj = aclEntryTable.remove(principal1);
      return obj != null;
  }

  public boolean checkPermission(Principal principal, Permission permission) {
    if (groupPermissions) {
      for (Enumeration enumeration = getGroupAclEntries(principal);
          enumeration.hasMoreElements();) {
        AclEntry entry = (AclEntry)enumeration.nextElement();
        if (entry.checkPermission(permission)) {
          return true;
        }
      }
      return false;    
    } else {
      AclEntry entry = (AclEntry)aclEntryTable.get(principal);    
      return entry.checkPermission(permission);
    }
  }

 
  private Enumeration getGroupAclEntries(Principal principal) {
    Vector vector = new Vector(20,20);
    for (Enumeration enumeration1 = aclEntryTable.keys();
        enumeration1.hasMoreElements();) {
      PrincipalGroup group = (PrincipalGroup) enumeration1.nextElement();
      if (group.isMember(principal)) {
        AclEntry aclentry = (AclEntry) aclEntryTable.get(group);
        vector.addElement(aclentry);
      }
    }

    return vector.elements();
  }


}
