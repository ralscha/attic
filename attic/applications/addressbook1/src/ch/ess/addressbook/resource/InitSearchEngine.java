package ch.ess.addressbook.resource;

import java.io.*;

import javax.servlet.*;

import org.apache.lucene.index.*;
import org.quartz.*;

import ch.ess.addressbook.search.*;

public class InitSearchEngine {

  public static void init(ServletContext context) throws IOException, JobExecutionException {

    File f = (File)context.getAttribute("javax.servlet.context.tempdir");
    File ixDir = new File(f, "ix");

    SearchEngine.init(ixDir.getPath());
    
    /*
    if (!ixDir.exists()) {
      IndexWriter writer = SearchEngine.getIndexWriter(true);
      writer.close();
      new IndexAll().handleAlarm(null);
    }
    */
    
    IndexWriter writer = SearchEngine.getIndexWriter(true);
    writer.close();
    new IndexAll().execute(null);    
    

  }

}

