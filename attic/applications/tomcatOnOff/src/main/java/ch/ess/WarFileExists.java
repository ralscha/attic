package ch.ess;

import java.io.File;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;
import org.jboss.seam.annotations.security.Restrict;

@Name("warFileExists")
@Restrict("#{identity.loggedIn}")
public class WarFileExists {

  @Unwrap
  public boolean warFileExists() {
    return new File("C:\\TEST").exists();
  }
  
}
