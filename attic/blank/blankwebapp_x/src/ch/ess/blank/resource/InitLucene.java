package ch.ess.blank.resource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexWriter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import ch.ess.blank.web.user.search.IndexAll;
import ch.ess.common.search.SearchEngine;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class InitLucene implements PlugIn {

  private static final Log LOG = LogFactory.getLog(InitLucene.class);

  public void destroy() {
    SearchEngine.shutDown();
  }

  public void init(ActionServlet servlet, ModuleConfig config) {

    File servletTmpDir = (File) servlet.getServletContext().getAttribute("javax.servlet.context.tempdir");
    String luceneDir = servletTmpDir.getAbsolutePath();

    File ixDir = new File(luceneDir, "index");

    System.setProperty("org.apache.lucene.lockdir", luceneDir);

    SearchEngine.init(ixDir.getAbsolutePath());
    LOG.debug("SearchEngine initalized");

    try {
      if (!ixDir.exists()) {
        IndexWriter writer = SearchEngine.getIndexWriter(true);
        writer.close();
        new IndexAll().execute(null);
        LOG.debug("SearchEngine index all done");
      }
    } catch (IOException e) {
      LOG.error("init lucene", e);
    }

  }

}