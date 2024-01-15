package ch.ess.startstop.service;

import java.util.ArrayList;
import java.util.List;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Synchronized;

@Name("buildLogger")
@Scope(ScopeType.SESSION)
@TideEnabled
@AutoCreate
@Synchronized
public class BuildLogger {

  private List<String> log = new ArrayList<String>();

  public void addLog(String line) {
    log.add(line);
  }

  public void clearLog() {
    log.clear();
  }

  public List<String> getLogs() {
    List<String> copyList = new ArrayList<String>();
    for (String line : log) {
      copyList.add(line);
    }
    log.clear();
    return copyList;
  }

}
