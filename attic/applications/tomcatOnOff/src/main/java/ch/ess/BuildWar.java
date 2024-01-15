package ch.ess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.log.Log;

@Name("buildWar")
@Restrict("#{identity.loggedIn}")
public class BuildWar {

  @Logger
  private Log log;

  @In
  private OnOff onoff;

  public void build() {

    onoff.off();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }

    try {
      ProcessBuilder pb = new ProcessBuilder("C:/tomcat_casetracker/ant/bin/ant.bat", "-f", "C:/tomcat_casetracker/build.xml", "checkout");
      Process process = pb.start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      @SuppressWarnings("unused")
      String line;

      boolean found = false;
      while ((line = br.readLine()) != null && !found) {
        //System.out.println(line);
      }

    } catch (IOException e) {
      log.error("getState", e);
    }

  }
}
