

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.util.Hashtable;

/**
 Sample application of JTextFieldHistory
 */
 public class JTextFieldHistorySample extends JFrame {

  private JTextFieldHistory jTextField1 = new JTextFieldHistory();
  private HistoryStringReference historyStringA = new HistoryStringReference();

  public JTextFieldHistorySample() {

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);

    try {
      Hashtable table = HistoryUtils.loadFromLocalDisk("history.tmp");
		  History.setHistoryTable(table);	// IMPORTANT!!! Set table prior to create historyObject.

      jbInit();
      this.setSize(200,100);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    JTextFieldHistorySample frame = new JTextFieldHistorySample();
    frame.setVisible(true);
  }

  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      this.dispose();
      System.exit(0);
    }
  }
  
  private void jbInit() throws Exception {

    // set propeties of history reference
    historyStringA.setId("testA");
    historyStringA.setSizeMin(2);
    historyStringA.setSizeMax(4);

    jTextField1.setHistoryString(historyStringA);
    jTextField1.setText("");
    jTextField1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextField1_actionPerformed(e);
      }
    });
    this.getContentPane().add(jTextField1, BorderLayout.NORTH);
  }

  void jTextField1_actionPerformed(ActionEvent e) {

    String str = (String)e.getActionCommand();

    jTextField1.addHistory(str);

    System.out.println("str:"+str);

    jTextField1.setText("");
  }
}