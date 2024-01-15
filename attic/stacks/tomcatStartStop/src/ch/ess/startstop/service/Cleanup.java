package ch.ess.startstop.service;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

@Name("cleanup")
@TideEnabled
@AutoCreate
public class Cleanup {

  private String workDir;
  private String logDir;
  private String tempDir;
  
  public String cleanup() {
    
    File workDirFile = new File(workDir);
    File tempDirFile = new File(logDir);
    File logDirFile = new File(tempDir);
    
    try {
      FileUtils.deleteDirectory(workDirFile);
      workDirFile.mkdirs();
      
      FileUtils.deleteDirectory(tempDirFile);
      tempDirFile.mkdirs();
      
      FileUtils.deleteDirectory(logDirFile);
      logDirFile.mkdirs();
      
    } catch (IOException e) {
      return e.toString();
    }
    
    return null;
    
  }
  
}
