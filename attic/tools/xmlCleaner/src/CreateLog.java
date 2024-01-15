import javax.swing.JTextArea;

/**
 * Diese Klasse ist f�r das Log zust�ndig
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */

public class CreateLog {

  /**
   * Diese Methode schreibt das Log in das Textarea
   * 
   * @param log
   * @param logText
   */
  public void writeLog(JTextArea log, String logText) {
    log.append(logText);
  }
}
