package ch.ess.base.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:20 $
 */
public class UserPrincipal implements Serializable {

  private Set<String> rightSet;
  private boolean logonCookie;
  private String userId;

  public UserPrincipal(final Set<String> rightSet, final boolean logonCookie, final String userId) {
    this.rightSet = new HashSet<String>();
    this.rightSet.addAll(rightSet);
    this.logonCookie = logonCookie;
    this.userId = userId;
  }

  public boolean hasRole(final String right) {
    return rightSet.contains(right);
  }

  public boolean isLogonCookie() {
    return logonCookie;
  }

  public String getUserId() {
    return userId;
  }

}