import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Macht für das GUI einen neuen Thread , so dass das GUI nicht einfriert
 * 
 * @author Sebastian Brand
 * @version 1.0
 *
 */
class WorkerStart extends SwingWorker<Void, Object> {

  String setTarget;
  String setFolder;
  String setSave;
  JTextArea setLogField;
  CreateLog logArea = new CreateLog();
  static private final String newline = "\n";

  /**
   * Diese Funktion wird aufgerufen und mat das neue Thread und ruft die Funktion auf
   */
  @Override
  public Void doInBackground() {
    try {
      XmlReaderWriter xml = new XmlReaderWriter();
      xml.xmlReadWrite(setTarget, setFolder, setSave, setLogField);

    } catch (Exception e) {
      //no action
    }

    return null;
  }

  /**
   * Wenn die Funktion doInBackground durchgelaufen ist, wird diese Funktion ausgef�hrt
   */
  @Override
  protected void done() {
    try {
      logArea.writeLog(setLogField, "*************************************************************************" + newline);
      logArea.writeLog(setLogField, "XML ist aufgeräumt!" + newline);
      logArea.writeLog(setLogField, "ENDE!" + newline);
    } catch (Exception e) {
      //no action
    }
  }

  /**
   * Setzt die Variablen, das ich sie im WorkerStart habe
   * 
   * @param target
   * @param folder
   * @param save
   * @param logField
   */
  public void setFields(String target, String folder, String save, JTextArea logField) {
    setTarget = target;
    setFolder = folder;
    setSave = save;
    setLogField = logField;
  }
}