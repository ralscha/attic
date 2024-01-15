package ch.ess.startstop.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;

@Name("showLog")
@TideEnabled
@AutoCreate
public class ShowLog {

  private String logDir;

  public String showLogFiles() {
    StringBuilder sb = new StringBuilder();

    try {
      File logDirFile = new File(logDir);
      File[] logFiles = logDirFile.listFiles();
      if (logFiles != null) {
        for (File logFile : logFiles) {
          if (logFile.length() > 0) {
            sb.append("\n");
            sb.append("\n");
            sb.append("------------------------------------------------------------\n");
            sb.append(logFile.getName().toUpperCase() + "\n");
            sb.append("------------------------------------------------------------\n");

            String line;
            BufferedReader br = new BufferedReader(new FileReader(logFile));
            while ((line = br.readLine()) != null) {
              sb.append(line);
              sb.append("\n");
            }

          }
        }
      }
    } catch (IOException e) {
      sb.append("ERROR ERROR ERROR: " + e.toString());
    }

    return sb.toString();
  }

}
