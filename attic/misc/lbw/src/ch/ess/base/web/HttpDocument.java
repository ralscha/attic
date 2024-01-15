/*
 * Created on Dec 7, 2004
 */
package ch.ess.base.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.model.Document;

public class HttpDocument {

  private Document document = null;

  public HttpDocument(Document document) {
    this.document = document;
  }

  public void write(HttpServletResponse response) throws IOException, SQLException, ServletException {
    write(response, null);
  }

  public void write(HttpServletResponse response, String cacheControl) throws IOException, SQLException,
      ServletException {
    OutputStream out = response.getOutputStream();
    Util.setExportHeader(response, document.getContentType(), document.getFileName());
    if (StringUtils.isNotBlank(cacheControl)) {
      response.setHeader("Cache-control", cacheControl);
    }
    String extension = document.getFileExtension();
    if (extension != null) {
      response.setHeader("extension", extension);
    }
    response.setContentLength(document.getContentSize());
    document.writeContent(out);
    out.close();
  }

}
