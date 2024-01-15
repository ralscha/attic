

package ch.ess.pbroker.security;

public abstract class Permission {

  private String name;

  public Permission(String name) {
    this.name = name;
  }

  public abstract boolean implies(Permission permission);
  public abstract boolean equals(Object obj);
  public abstract int hashCode();

  public final String getName() {
    return name;
  }

  public abstract String getActions();

  public PermissionCollection newPermissionCollection() {
    return null;
  }

  public String toString() {
    String actions = getActions();
    if ((actions == null) || (actions.length() == 0)) { // OPTIONAL
      return "(" + getClass().getName() + " " + name + ")";
    } else {
      return "(" + getClass().getName() + " " + name + " " + actions + ")";
    }
  }
}

