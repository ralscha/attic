package ch.ess.startstop.service;

import java.io.File;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

@Name("warFileExists")
@TideEnabled
public class WarFileExists {

  @Unwrap
  public boolean warFileExists() {
    return new File("C:\\TEST").exists();
  }
  
}
