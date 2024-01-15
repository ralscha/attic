import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Hier wird das GUI erstellt und den Listener eingebunden
 * 
 * @author Sebastian Brand
 * @version 1.0
 * 
 */
public class GUI extends JPanel implements ActionListener {

  static private final String newline = "\n";
  JButton targetButton;
  JButton projectButton;
  JButton saveButton;
  JButton executeButton;
  JButton exitButton;
  JTextArea log;
  JTextField targetInput;
  JTextField projectInput;
  JTextField saveInput;
  JFileChooser fc;
  boolean err = false;

  /**
   * Erstellt die Felder, richtet Sie aus und fügt sie dem Frame hinzu
   * @param container
   */
  public GUI(Container container) {
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

    log = new JTextArea(40, 80);
    log.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(log);

    fc = new JFileChooser();

    targetInput = new JTextField(40);
    targetInput.setEditable(false);

    targetButton = new JButton("Ziel XML...");
    targetButton.addActionListener(this);
    targetButton.setPreferredSize(new Dimension(120, 25));

    projectInput = new JTextField(40);
    projectInput.setEditable(false);

    projectButton = new JButton("Projektordner...");
    projectButton.addActionListener(this);
    projectButton.setPreferredSize(new Dimension(120, 25));

    saveInput = new JTextField(40);
    saveInput.setEditable(false);

    saveButton = new JButton("Speicherort...");
    saveButton.addActionListener(this);
    saveButton.setPreferredSize(new Dimension(120, 25));

    executeButton = new JButton("Ausführen");
    executeButton.addActionListener(this);

    exitButton = new JButton("Beenden");
    exitButton.addActionListener(this);

    JPanel savePanel = new JPanel();
    savePanel.add(saveInput);
    savePanel.add(saveButton);

    JPanel targetPanel = new JPanel();
    targetPanel.add(targetInput);
    targetPanel.add(targetButton);

    JPanel projectPanel = new JPanel();
    projectPanel.add(projectInput);
    projectPanel.add(projectButton);

    JPanel actionPanel = new JPanel();
    actionPanel.add(executeButton);
    actionPanel.add(exitButton);

    container.add(targetPanel);
    container.add(projectPanel);
    container.add(savePanel);
    container.add(logScrollPane);
    container.add(actionPanel);
  }

  /**
   * Wenn ein Button geklickt wurde, wird hier herausgefunden, welcher Button geklickt wurde und was gemacht werden muss
   */
  public void actionPerformed(ActionEvent e) {
    /**
     * Einstellungen für die verschiedenen FileChooser
     */
    if (e.getSource() == targetButton) {
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

      int returnVal = fc.showOpenDialog(GUI.this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        targetInput.setText(file.getAbsolutePath());
      }
    } else if (e.getSource() == projectButton) {
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      int returnVal = fc.showSaveDialog(GUI.this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        projectInput.setText(file.getAbsolutePath());
      }
    } else if (e.getSource() == saveButton) {
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      int returnVal = fc.showSaveDialog(GUI.this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        saveInput.setText(file.getAbsolutePath());
      }
    } else if (e.getSource() == executeButton) {

      Validator val = new Validator();
      CreateLog logArea = new CreateLog();

      if (err == true) {
        log.setText("");
        err = false;
      }
      /**
       * Hier werden die ganzen Daten gepr�ft
       */
      if (!val.textValidator(targetInput.getText())) {
        logArea.writeLog(log, "XML reinigen fehlgeschlagen! Kein Ziel XML ausgew�hlt!" + newline);
        err = true;
      } else if (val.ifXml(targetInput.getText()) == null) {
        logArea.writeLog(log, "XML reinigen fehlgeschlagen! Das Ziel muss struts-config.xml sein!" + newline);
        err = true;
      }
      if (!val.textValidator(projectInput.getText())) {
        logArea.writeLog(log, "XML reinigen fehlgeschlagen! Kein Projektordner gew�hlt!" + newline);
        err = true;
      } else if (val.ifNotFolderSrc(projectInput.getText()) != null) {
        logArea.writeLog(log, "Sie d�rfen als Ordner nicht den src Ordner w�hlen!" + newline);
        err = true;
      } else if (val.ifNotFolderWeb(projectInput.getText()) != null) {
        logArea.writeLog(log, "Sie d�rfen als Ordner nicht den web Ordner w�hlen!" + newline);
        err = true;
      }
      if (!val.textValidator(saveInput.getText())) {
        logArea.writeLog(log, "XML reinigen fehlgeschlagen! Kein Speicherpunkt gew�hlt!" + newline);
        err = true;
      }
      /**
       * Wenn kein Fehler aufgetaucht ist, f�hrt er das untenstehende aus
       */
      if (err == false) {
        logArea.writeLog(log, "Startet mit dem XML einlesen und dem Aufräumen" + newline);
        logArea.writeLog(log, "*************************************************************************" + newline);

        WorkerStart work = new WorkerStart();
        work.setFields(targetInput.getText(), projectInput.getText(), saveInput.getText(), log);
        work.execute();
      }
    } else if (e.getSource() == exitButton) {
      System.exit(0);
    }
  }
}
