
import java.security.Principal;
import java.security.acl.*;
import java.util.Enumeration;
import java.util.Vector;

public class MyAclEntry implements AclEntry {

  private Principal user;
  private Vector permissionSet;
  private boolean negative;

  public MyAclEntry(Principal principal) {
    user = null;
    permissionSet = new Vector(10, 10);
    negative = false;
    user = principal;
  }

  public MyAclEntry() {
    user = null;
    permissionSet = new Vector(10, 10);
    negative = false;
  }

  public boolean setPrincipal(Principal principal) {
    if (user != null) {
      return false;
    } else {
      user = principal;
      return true;
    }
  }

  public void setNegativePermissions() {
    negative = true;
  }

  public boolean isNegative() {
    return negative;
  }

  public boolean addPermission(Permission permission) {
    if (permissionSet.contains(permission)) {
      return false;
    } else {
      permissionSet.addElement(permission);
      return true;
    }
  }

  public boolean removePermission(Permission permission) {
    return permissionSet.removeElement(permission);
  }

  public boolean checkPermission(Permission permission) {
    return permissionSet.contains(permission);
  }

  public Enumeration permissions() {
    return permissionSet.elements();
  }

  public String toString() {
    StringBuffer stringbuffer = new StringBuffer();
    if (negative)
      stringbuffer.append("-");
    else
      stringbuffer.append("+");
    if (user instanceof Group)
      stringbuffer.append("Group.");
    else
      stringbuffer.append("User.");
    stringbuffer.append(user + "=");
    for (Enumeration enumeration = permissions(); enumeration.hasMoreElements();) {
      Permission permission = (Permission) enumeration.nextElement();
      stringbuffer.append(permission);
      if (enumeration.hasMoreElements())
        stringbuffer.append(",");
    }

    return new String(stringbuffer);
  }


  public synchronized Object clone() {
    MyAclEntry aclentryimpl = new MyAclEntry(user);
    aclentryimpl.permissionSet = (Vector)permissionSet.clone();
    aclentryimpl.negative = negative;
    return aclentryimpl;
  }


  public Principal getPrincipal() {
    return user;
  }

}
