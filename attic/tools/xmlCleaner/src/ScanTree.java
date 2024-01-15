import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import org.jdom.Element;

/**
 * Hier wird das Directory ausgelesen und rekursiv aufgelistet.
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */

public class ScanTree {

  static String jspMatch = null;
  static XmlScraper xml = new XmlScraper();

  /**
   * Dateien mit der Endung .jsp werden geprüft und an die Funktion fileScraper
   * weitergegeben.
   * 
   * @param dir
   * @param actionMain
   * @param logArea
   * @throws IOException
   */
  public static void scan(File dir, Element actionMain, JTextArea logArea) throws IOException {
    String[] entries = dir.list();

    if (entries == null || entries.length < 1) {
      return;
    }
    for (int i = 0; i < entries.length; i++) {
      File entry = new File(dir, entries[i]);
      if (entry.isDirectory()) {
        scan(entry, actionMain, logArea); // rekursiv ins Unterverzeichnis verzweigen
      } else {
        //Pr�ft ob das File .jsp ist
        Pattern p = Pattern.compile("\\.jsp$");
        Matcher m = p.matcher(entry.getName());

        while (m.find()) {
          jspMatch = entry.getName().substring(m.start(), m.end());
        }
        if (jspMatch != null) {
          //Führt die Funktion zum überprüfen aus
          xml.fileScraper(entry, actionMain);
          jspMatch = null;
        }
      }
    }
  }

  /**
   * Gibt den Wert Exist zurück
   * @return
   */
  public boolean getExist() {
    return xml.exist;

  }

  /**
   * Setzt den Wert Exist auf false zur�ck
   * @return
   */
  public void resetExist() {
    xml.exist = false;

  }

}
