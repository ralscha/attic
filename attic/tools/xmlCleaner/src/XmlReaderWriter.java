import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JTextArea;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Liest das XML ein und schreibt das aufger�umte XML
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */

public class XmlReaderWriter {

  CreateLog log = new CreateLog();
  static private final String newline = "\n";

  /**
   * Liest das XML ein, ruft die Funktionen zum aufr�umen auf und erstellt ein neues XML
   * 
   * @param target
   * @param folder
   * @param save
   * @param logField
   */
  @SuppressWarnings("unchecked")
  public void xmlReadWrite(String target, String folder, String save, JTextArea logField) {
    try {

      // Liest das XML File
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(new File(target));
      // Liest die Elemente vom XML aus
      Element root = doc.getRootElement();
      Element child = root.getChild("form-beans");
      List<Element> subchild = child.getChildren("form-bean");

      Element actionChild = root.getChild("action-mappings");
      List<Element> actionSubchild = actionChild.getChildren("action");

      //Startet Prüfung
      XmlScraper scraper = new XmlScraper();

      log.writeLog(logField, "*************************************************************************" + newline);
      log.writeLog(logField, "Startet mit dem aufräumen vom Form-Been" + newline);
      log.writeLog(logField, "*************************************************************************" + newline);
      scraper.formBeenScraper(folder, subchild, actionSubchild, logField);

      log.writeLog(logField, "*************************************************************************" + newline);
      log.writeLog(logField, "Startet mit dem aufräumen der Action" + newline);
      log.writeLog(logField, "*************************************************************************" + newline);
      scraper.actionScraper(folder, actionSubchild, subchild, logField);

      // Schreibt Resultat
      XMLOutputter outp = new XMLOutputter();
      outp.setFormat(Format.getPrettyFormat());
      // Erstellt neues XML.File und schreibt die Daten rein
      outp.output(doc, new FileOutputStream(new File(save + "\\struts-config-new.xml"))); //New File
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}