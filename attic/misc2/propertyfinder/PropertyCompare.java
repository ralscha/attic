

import java.io.*;
import java.util.*;
import common.io.*;

public class PropertyCompare {


  private Properties propsDE;
  private Properties propsEN;

  private PropertyCompare() {
    
    try {
    FileInputStream fis = new FileInputStream("D:\\javaprojects\\ess\\pbroker\\web-inf\\classes\\pbroker.properties");
    propsDE = new Properties();
    propsDE.load(fis);
    fis.close();
    
    fis = new FileInputStream("D:\\javaprojects\\ess\\pbroker\\web-inf\\classes\\pbroker_en.properties");
    propsEN = new Properties();
    propsEN.load(fis);
    fis.close();
    
    } catch (IOException ioe) {
      System.err.println(ioe);
    }    
    
  }
  
  
  private void go() {
      
    Enumeration e = propsEN.propertyNames();
    while(e.hasMoreElements()) {
      String property = (String)e.nextElement();

      String prop = propsDE.getProperty(property);
      if (prop != null) {
        propsDE.remove(property);
      }
      
    }

    try {
    FileOutputStream fos = new FileOutputStream("fehlt.txt");
    propsDE.store(fos, "fehlt");
    fos.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }

  public static void main(String[] args) {
    new PropertyCompare().go();
  }


}