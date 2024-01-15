import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Dier ist die main-Funktion enthalten und erstellt das Frame
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */

public class XmlCleaner {

  /**
   * Die main-Funktion vom xmlCleaner
   * 
   * @param args
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI("xmlCleaner");
      }
    });
  }

  /**
   * Diese Funktion erstellt das Frame und f�llt die Daten ab, die es aus der Klasse gui holt
   * 
   * @param titel
   */
  static void createAndShowGUI(String titel) {
    JFrame frame = new JFrame(titel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(new GUI(frame.getContentPane()));

    frame.pack();
    frame.setVisible(true);
  }
}
