package ch.ess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.log.Log;

@Name("poller")
@Restrict("#{identity.loggedIn}")
public class Poller {

  @SuppressWarnings("unused")
  @Out
  private String statusMessage;

  @SuppressWarnings("unused")
  @Out
  private String buildStatusMessage;

  @Logger
  private Log log;

  private static String STOPPED = "STATE              : 1  STOPPED";
  private static String STARTED = "STATE              : 4  RUNNING";

  public void poll() {
    statusMessage = "";
    try {
      ProcessBuilder pb = new ProcessBuilder("c:/windows/system32/sc.exe", "query", "casetracker");
      Process process = pb.start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      boolean found = false;

      while ((line = br.readLine()) != null && !found) {
        System.out.println(line);
        if (line.contains(STOPPED)) {
          found = true;
          statusMessage = DateFormat.getTimeInstance(DateFormat.LONG).format(new Date()) + ": Tomcat Service is stopped";
        } else if (line.contains(STARTED)) {
          found = true;
          statusMessage = DateFormat.getTimeInstance(DateFormat.LONG).format(new Date()) + ": Tomcat Service is running";
        }
      }

    } catch (IOException e) {
      log.error("getState", e);
    }

    if (!new File("C:\\tomcat_casetracker\\casetracker\\build\\dist\\casetracker.war").exists()) {
      buildStatusMessage = "WAR building in progress...";
    } else {
      buildStatusMessage = "WAR is ready";
    }

  }

}
