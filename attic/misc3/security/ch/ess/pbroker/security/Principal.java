package ch.ess.pbroker.security;

public class Principal {

  protected String name;

  public Principal(String s) {
    name = s;
  }

  public boolean equals(Object obj) {
    if (obj instanceof Principal) {
      Principal principalimpl = (Principal) obj;
      return name.equals(principalimpl.toString());
    } else {
      return false;
    }
  }

  public String toString() {
    return name;
  }

  public int hashCode() {
    return name.hashCode();
  }

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

}
