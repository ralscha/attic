import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hier werden die Eingaben �berpr�ft
 * 
 * @author Sebastian Brand
 * @version 1.0
 *
 */
public class Validator {

  String xmlMatch;
  String srcMatch;
  String webMatch;

  /**
   * Pr�ft ob die Eingabe k�rzer oder gleich 0 ist
   * 
   * @param Input
   * @return
   */
  public boolean textValidator(String Input) {
    if (Input.length() <= 0) {
      return false;
    }
    return true;
  }

  /**
   * Pr�ft ob das File struts-config.xml ist
   * 
   * @param Input
   * @return
   */
  public String ifXml(String Input) {
    Pattern p = Pattern.compile("struts-config\\.xml");
    Matcher m = p.matcher(Input);

    while (m.find()) {
      xmlMatch = Input.substring(m.start(), m.end());
    }

    return xmlMatch;
  }

  /**
   * Pr�ft ob der Ordner nicht src ist
   * 
   * @param Input
   * @return
   */
  public String ifNotFolderSrc(String Input) {
    Pattern p = Pattern.compile("\\\\src");
    Matcher m = p.matcher(Input);

    while (m.find()) {
      srcMatch = Input.substring(m.start(), m.end());
    }
    return srcMatch;
  }

  /**
   * Pr�ft ob der Ordner nich web ist
   * 
   * @param Input
   * @return
   */
  public String ifNotFolderWeb(String Input) {
    Pattern p = Pattern.compile("\\\\web");
    Matcher m = p.matcher(Input);

    while (m.find()) {
      webMatch = Input.substring(m.start(), m.end());
    }

    return webMatch;
  }
}
