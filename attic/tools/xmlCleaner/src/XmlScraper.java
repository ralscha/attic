import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import org.jdom.Element;

/**
 * Hier sind alle XML Pr�fer enthalten
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */

public class XmlScraper {

  CreateLog log = new CreateLog();
  static private final String newline = "\n";
  boolean exist = false;

  /**
   * Pr�ft ob im Element form-bean das Attribut name auf ein File verlinkt ist.
   * Dieses File findet man im Ordner src unter dem Pfad des type Attributes.
   * Wenn das File nicht exitiert wird das form-bean und die dazugeh�rige action gel�scht
   * 
   * @param folder
   * @param subchild
   * @param actionSubchild
   * @param logArea
   */
  public void formBeenScraper(String folder, List<Element> subchild, List<Element> actionSubchild, JTextArea logArea) {
    int size = subchild.size() - 1;
    for (int i = 0; i <= size; i++) {
      Element elMain = subchild.get(i);
      //Holt das Attribut
      String s = elMain.getAttributeValue("type");

      String path = folder + "\\src\\" + s;
      String patternStr = "\\.";
      String replacementStr = "\\\\";

      Pattern pattern = Pattern.compile(patternStr);
      Matcher matcher = pattern.matcher(path);
      String output = matcher.replaceAll(replacementStr);
      //Schaut ob das File besteht
      File fileExist = new File(output + ".java");

      if (!fileExist.exists()) {
        log.writeLog(logArea, "Die Klasse " + elMain.getAttributeValue("name") + " existiert nicht! Action und Klasse im XML gel�scht."
            + newline);
        //child.removeContent();
        //child.removeChild("form-bean");

        int actionSize = actionSubchild.size() - 1;
        //Wenn es nicht existiert wird das Elemnt form-been und die Action gel�scht
        for (int y = 0; y <= actionSize; y++) {
          Element actionMain = actionSubchild.get(y);
          if (actionMain.getAttributeValue("name") == null) {
            continue;
          }

          if (actionMain.getAttributeValue("name").equals(elMain.getAttributeValue("name"))) {

            //actionMain.removeContent(y);
            actionMain.removeChild("forward");
            actionSubchild.remove(y);
            actionSize--;
          }
        }

        subchild.remove(i);
        i--;
        size--;
      }
    }
  }

  /**
   * Pr�ft ob die Klasse des XML physikalisch existiert, wenn nein wird es aus dem XML entfernt.
   * Pr�ft ob das form-bean existiert, wenn nein wird es aus dem XML entfernt (ausnahme sind die Actions wo keinen Namen haben).
   * 
   * @param folder
   * @param actionSubchild
   * @param subchild
   * @param logArea
   * @throws IOException
   */
  public void actionScraper(String folder, List<Element> actionSubchild, List<Element> subchild, JTextArea logArea) throws IOException {
    int size = subchild.size() - 1;
    int actionSize = actionSubchild.size() - 1;
    for (int i = 0; i <= actionSize; i++) {
      Element actionMain = actionSubchild.get(i);
      //Holt das Attribut
      String s = actionMain.getAttributeValue("type");

      ScanTree tree = new ScanTree();
      File dir = new File(folder + "\\web");
      ScanTree.scan(dir, actionMain, logArea);
      if (!tree.getExist()) {
        log.writeLog(logArea, "Die Action " + actionMain.getAttributeValue("name") + " mit dem Pfad: "
            + actionMain.getAttributeValue("path") + " wird in keinem JSP-File gebraucht." + newline);
      }
      //setzt Exist zur�ck
      tree.resetExist();

      String path = folder + "\\src\\" + s;
      String patternStr = "\\.";
      String replacementStr = "\\\\";

      Pattern pattern = Pattern.compile(patternStr);
      Matcher matcher = pattern.matcher(path);
      String output = matcher.replaceAll(replacementStr);
      //Pr�ft ob das File existiert
      File fileExist = new File(output + ".java");

      if (actionMain.getAttributeValue("type") == null) {
        continue;
      }
      //Wenn nicht, entfernt es die Action
      if (!fileExist.exists()) {
        log.writeLog(logArea, "Die Klasse " + actionMain.getAttributeValue("name") + " am Ort " + fileExist
            + " existiert nicht! Action im XML gel�scht." + newline);
        actionSubchild.remove(i);
        i--;
        actionSize--;
      }

      for (int y = 0; y <= size; y++) {
        Element elMain = subchild.get(y);
        if (actionMain.getAttributeValue("name") == null) {
          continue;
        }

        if (actionMain.getAttributeValue("name").equals(elMain.getAttributeValue("name"))) {
          break;
        }
        //Wenn das form-been nicht existiert, wird die dazugeh�rige ACtion gel�scht
        if (y == size) {
          actionMain.removeChild("forward");
          actionSubchild.remove(y);
          log.writeLog(logArea, "Das form-been " + actionMain.getAttributeValue("name") + " existiert nicht! Action im XML gel�scht."
              + newline);
          actionSize--;
        }
      }
    }
  }

  /**
   * Durchsucht das File, ob ein a href oder html:form tag mit dem entsprechenden Actionpath vorhanden ist.
   * 
   * @param file
   * @param actionMain
   * @throws IOException
   */
  public void fileScraper(File file, Element actionMain) throws IOException {
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line = null;
    String jspMatch = "";
    String jspMatch2 = "";
    //Prüft ob im File eins von den Beiden Strings vorkommt
    while ((line = br.readLine()) != null) {
      Pattern p = Pattern.compile("href=\"" + actionMain.getAttributeValue("path") + ".*\"");
      Pattern p2 = Pattern.compile("<html:form.*action=\"" + actionMain.getAttributeValue("path") + ".*\"");
      Matcher m = p.matcher(line);
      Matcher m2 = p2.matcher(line);

      while (m.find()) {
        jspMatch = line.substring(m.start(), m.end());
      }
      while (m2.find()) {
        jspMatch2 = line.substring(m2.start(), m2.end());
      }
      //Wenn ja, setzt es die Variable
      if (jspMatch.length() > 0 || jspMatch2.length() > 0) {
        exist = true;
        break;
      }
    }
    fr.close();
  }
}
