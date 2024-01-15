package ch.ess.startstop.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

@Name("onoff")
@TideEnabled
@AutoCreate
public class OnOff {

  @Logger
  private Log log;

  public void on() {
    doOnOff("start");
  }

  public void off() {
    doOnOff("stop");
  }

  private String scPath;
  private String serviceName;
  
  private void doOnOff(String action) {
    try {
      ProcessBuilder pb = new ProcessBuilder(scPath, action, serviceName);
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
