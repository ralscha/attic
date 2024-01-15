package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.windward.xmlreport.ProcessPdf;
import net.windward.xmlreport.ProcessReportAPI;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class GetPDFAction extends Action {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    //System.setProperty( "WindwardReports.properties.filename", "c:/WindwardReports.properties");

    response.setContentType("application/pdf");
    response.setHeader("Cache-control", "must-revalidate");

    OutputStream out = response.getOutputStream();
    InputStream xml = null;
    InputStream rtf = null;

    try {

      Document document = DocumentHelper.createDocument();
      Element root = document.addElement("test");

      Element elem = root.addElement("no");
      elem.addText("100");

      StringWriter swriter = new StringWriter();
      XMLWriter writer = new XMLWriter(swriter, new OutputFormat());
      writer.write(document);
      writer.close();

      xml = new StringInputStream(swriter.toString());

      rtf = getClass().getResourceAsStream("/template.rtf");

      ProcessReportAPI report = new ProcessPdf(xml, rtf, out);
      report.process();

    } catch (Exception ioe) {
      ioe.printStackTrace();
      throw ioe;
    } finally {
      if (xml != null) {
        xml.close();
      }
      if (rtf != null) {
        rtf.close();
      }
      out.close();
    }

    return null;

  }

}
