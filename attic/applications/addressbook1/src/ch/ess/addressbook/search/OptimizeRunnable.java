package ch.ess.addressbook.search;

import java.io.*;

import org.apache.commons.logging.*;
import org.apache.lucene.index.*;


public class OptimizeRunnable implements Runnable {

  private static final Log logger = LogFactory.getLog(OptimizeRunnable.class);  
  

  public void run() {
    
    IndexWriter writer = null;
    
    try {
      
      writer = SearchEngine.getIndexWriter(false);                 
      writer.optimize();      
    } catch (IOException ioe) {
      logger.error("optimizeCommand", ioe);
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
        }
      }
    }  
  }

}
