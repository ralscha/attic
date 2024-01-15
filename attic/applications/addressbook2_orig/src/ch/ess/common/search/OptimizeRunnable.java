package ch.ess.common.search;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class OptimizeRunnable implements Runnable {

  private static final Log LOG = LogFactory.getLog(OptimizeRunnable.class);

  public void run() {

    IndexWriter writer = null;

    try {

      writer = SearchEngine.getIndexWriter(false);
      writer.optimize();
    } catch (IOException ioe) {
      LOG.error("optimizeRunnable", ioe);
    } finally {
      if (writer != null) {
        try {
          writer.close();
        } catch (IOException e) {
          LOG.error("optimizeRunnable", e);
        }
      }
    }
  }

}
