package ch.ess.timetracker.web.tasktime;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import net.windward.xmlreport.ProcessPdf;
import net.windward.xmlreport.ProcessPdfAPI;

import org.apache.struts.action.ActionForward;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import ch.ess.common.util.StringInputStream;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;


/** 
 * @struts.action path="/test"
 */
public class TestAction extends BaseAction {

  public ActionForward execute() throws Exception {
    
    WebContext ctx = WebContext.currentContext();
    
    ctx.getResponse().setContentType("application/pdf");
    ctx.getResponse().setHeader("Cache-control", "must-revalidate");
    
    OutputStream out = ctx.getResponse().getOutputStream();
    InputStream rtf = null;

    try {

      Document document = DocumentHelper.createDocument();
      Element root = document.addElement("vertrag");
      Element elem = root.addElement("bestellnr");
      elem.addText("testnr");


      
      StringWriter swriter = new StringWriter();
      XMLWriter writer = new XMLWriter( swriter, new OutputFormat() );
      writer.write(document);
      writer.close();      
      
      System.out.println(swriter.toString());
      
      
      InputStream xml = new StringInputStream(swriter.toString());
      
      
      File templateFile = new File("c://temp//template.rtf");
      rtf = new FileInputStream(templateFile);

      ProcessPdfAPI report = new ProcessPdf(xml, rtf, out);
      report.process();

    } catch (Exception ioe) {
      ioe.printStackTrace();
      throw ioe;
    } finally {
      if (rtf != null) {
        rtf.close();
      }
      out.close();
    }

    return null;

  }

}
