package ch.ess.task.resource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import ch.ess.common.search.SearchEngine;
import ch.ess.task.web.task.search.IndexAll;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/05 06:28:58 $ 
  */
public class InitLucene implements PlugIn {

  private static final Log LOG = LogFactory.getLog(InitLucene.class);

  public void destroy() {
    SearchEngine.shutDown();
  }

  public void init(ActionServlet servlet, ModuleConfig config) {

    File f = (File)servlet.getServletContext().getAttribute("javax.servlet.context.tempdir");
    File ixDir = new File(f, "index");

    SearchEngine.init(ixDir.getPath());

    try {
      if (!ixDir.exists()) {
        IndexWriter writer = SearchEngine.getIndexWriter(true);
        writer.close();
        new IndexAll().execute(null);
      }
    } catch (IOException e) {
      LOG.error("init lucene", e);
    } 

  }

}
