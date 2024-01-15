package ch.ess.test;

import org.apache.log4j.Logger;

public class PojoService {

  private static final Logger log = Logger.getLogger(PojoService.class);

  private int counter = 0;

  public int getCounter() {
    log.info("Counter: " + counter + " -> " + (counter + 1));
    return ++counter;
  }
  
  public void save(String name, String vorname) {
    System.out.println("Name: " + name);
    System.out.println("Vorname: " + vorname);
  }
}
