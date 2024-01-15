package ch.ess.addressbook.resource;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.JobExecutionException;

import ch.ess.addressbook.web.contact.search.IndexAll;
import ch.ess.common.search.SearchEngine;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.3 $ $Date: 2003/11/11 18:59:57 $ 
  */
public class InitLucene implements PlugIn {

  private static final Log LOG = LogFactory.getLog(InitLucene.class);

  public void destroy() {
    SearchEngine.shutDown();
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

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
    } catch (JobExecutionException e) {
      LOG.error("init lucene", e);
    }

  }

}
