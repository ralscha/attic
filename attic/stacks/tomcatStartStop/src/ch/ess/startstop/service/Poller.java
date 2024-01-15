package ch.ess.startstop.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

@Name("poller")
@Scope(ScopeType.SESSION)
@AutoCreate
public class Poller {

  @Logger
  private Log log;

  private static String STOPPED = "STATE              : 1  STOPPED";
  private static String STARTED = "STATE              : 4  RUNNING";

  private String scPath;
  private String serviceName;

  private final static DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");

  public void poll() {
    String statusMessage = "Working...";
    try {
      ProcessBuilder pb = new ProcessBuilder(scPath, "query", serviceName);
      Process process = pb.start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;

      boolean found = false;

      while ((line = br.readLine()) != null && !found) {
        //System.out.println(line);

        if (line.contains(STOPPED)) {
          found = true;
          statusMessage = DATEFORMAT.format(new Date()) + " Tomcat Service is stopped";
        } else if (line.contains(STARTED)) {
          found = true;
          statusMessage = DATEFORMAT.format(new Date()) + " Tomcat Service is running";
        }
      }

    } catch (IOException e) {
      log.error("getState", e);
    }
//
//    String buildStatusMessage;
//    if (!new File("C:\\tomcat_casetracker\\casetracker\\build\\dist\\casetracker.war").exists()) {
//      buildStatusMessage = "WAR building in progress...";
//    } else {
//      buildStatusMessage = "WAR is ready";
//    }

    FacesMessages.instance().add(statusMessage);
//    FacesMessages.instance().add(buildStatusMessage);

  }

  @Asynchronous
  @SuppressWarnings("unused")
  public QuartzTriggerHandle sendAsync(@Expiration Date when, @IntervalDuration Long interval) {
    poll();
    Events.instance().raiseEvent("notification");
    return null;
  }

//  @Observer("notification")
//  public void timerRaised() {
//    DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
//    FacesMessages.instance().add(df.format(new Date()));
//  }

}
