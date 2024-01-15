package ch.ess.startstop.service;

import java.lang.management.ManagementFactory;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import ch.ess.startstop.entity.Info;

@Name("memInfo")
@TideEnabled
@AutoCreate
public class MemInfo {

  public Info getInfo() {
    com.sun.management.OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    long total = bean.getTotalPhysicalMemorySize();
    long free = bean.getFreePhysicalMemorySize();
    
    Info info = new Info();
    info.setTotal(total);
    info.setFree(free);
    info.setUsed(total-free);
    return info;
    
  }
}
