package ch.ess;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator {

  @Logger
  private Log log;

  @In
  private Identity identity;

  public boolean authenticate() {
    log.info("authenticating #0", identity.getUsername());
    return "admin".equals(identity.getUsername()) && "ignazius".equals(identity.getPassword());
    
  }
}
