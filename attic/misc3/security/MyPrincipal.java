
import java.security.Principal;

public class MyPrincipal implements Principal {

  private String user;

  public MyPrincipal(String s) {
    user = s;
  }

  public boolean equals(Object obj) {
    if (obj instanceof MyPrincipal) {
      MyPrincipal principalimpl = (MyPrincipal) obj;
      return user.equals(principalimpl.toString());
    } else {
      return false;
    }
  }

  public String toString() {
    return user;
  }

  public int hashCode() {
    return user.hashCode();
  }

  public String getName() {
    return user;
  }

}
