package ch.ess.base.web;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.cc.framework.security.Principal;

public class UserPrincipal implements Principal, Serializable {

  private Set<String> rightSet;
  private boolean logonCookie;
  private String userId;

  public UserPrincipal(final Set<String> rightSet, final boolean logonCookie, final String userId) {
    this.rightSet = new HashSet<String>();
    this.rightSet.addAll(rightSet);
    this.logonCookie = logonCookie;
    this.userId = userId;
  }

  public boolean isInRole(final String role) {
    return true;
  }

  public boolean hasRight(final String right) {
    return rightSet.contains(right);
  }

  public boolean isLogonCookie() {
    return logonCookie;
  }

  public String getUserId() {
    return userId;
  }

}
