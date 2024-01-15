package ch.ess.startstop.service;

import java.lang.management.ManagementFactory;

public class Test {

  public static void main(String[] args) {
    com.sun.management.OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    System.out.println(bean.getTotalPhysicalMemorySize());
    System.out.println(bean.getFreePhysicalMemorySize());    
  }
}
