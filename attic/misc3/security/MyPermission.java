
import java.util.*;
import java.security.Principal;
import java.security.acl.*;

public class MyPermission implements Permission {
  private String permission;

  public MyPermission(String permission) {
    this.permission = permission;
  }

  /* from Permission */
  public boolean equals(Object obj) {
    if (obj instanceof Permission) {
        Permission per = (Permission)obj;
        return permission.equals(per.toString());
    } else {
        return false;
    }
  }

  public String toString() {
    return permission;
  }

  public int hashCode() {
    return toString().hashCode();
  }
}
