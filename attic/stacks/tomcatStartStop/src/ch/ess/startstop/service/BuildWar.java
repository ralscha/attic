package ch.ess.startstop.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Log;

@Name("buildWar")
@TideEnabled
@AutoCreate
public class BuildWar {

  @Logger
  private Log log;

  @In
  private OnOff onoff;

  @In
  private BuildLogger buildLogger;

  private String antPath;
  private String buildFile;
  private String target;
  
  public void build() {

    onoff.off();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    try {
      //ProcessBuilder pb = new ProcessBuilder("C:/tomcat_casetracker/ant/bin/ant.bat", "-f", "C:/tomcat_casetracker/build.xml", "checkout");

      ProcessBuilder pb = new ProcessBuilder(antPath, "-f", buildFile, target);

      Process process = pb.start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      boolean found = false;
      while ((line = br.readLine()) != null && !found) {
        buildLogger.addLog(line);
      }

    } catch (IOException e) {
      log.error("getState", e);
    }

    Events.instance().raiseEvent("buildComplete");
  }
}
