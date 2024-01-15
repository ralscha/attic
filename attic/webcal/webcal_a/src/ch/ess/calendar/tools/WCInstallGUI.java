package ch.ess.calendar.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WCInstallGUI extends JFrame {
  private static final long serialVersionUID = 1L;
  
  JPanel contentPane;
   BorderLayout borderLayout1 = new BorderLayout();
   JPanel jPanel1 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JButton installButton = new JButton();
   JButton exitButton = new JButton();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JLabel jLabel1 = new JLabel();
   JTextField smtpServerTf = new JTextField();
   JLabel jLabel2 = new JLabel();
   JTextField senderTf = new JTextField();
   JLabel jLabel3 = new JLabel();
   JTextField portTf = new JTextField();

   public static void main(String[] args) {
      new WCInstallGUI().setVisible(true);
   }

   //Construct the frame
   public WCInstallGUI() {
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   //Component initialization
   private void jbInit() throws Exception  {
      contentPane = (JPanel) this.getContentPane();
      contentPane.setLayout(borderLayout1);
      this.setSize(new Dimension(251, 155));
      this.setTitle("ESS Web Calendar Install");
      jPanel1.setLayout(gridBagLayout1);
      installButton.setText("Install");

      installButton.addActionListener(new java.awt.event.ActionListener() {

         public void actionPerformed(ActionEvent e) {
            installButton_actionPerformed(e);
         }
      });

      exitButton.setText("Exit");
      exitButton.addActionListener(new java.awt.event.ActionListener() {

         public void actionPerformed(ActionEvent e) {
            exitButton_actionPerformed(e);
         }
      });
      jLabel1.setText("SMTP Server");
      jLabel2.setText("Sender EMail");
      jLabel3.setText("HTTP Port");
      smtpServerTf.setColumns(20);
      senderTf.setColumns(20);
      portTf.setText("8080");
      portTf.setColumns(10);

      contentPane.add(jPanel1, BorderLayout.CENTER);
      jPanel1.add(smtpServerTf, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 113, 0));
      jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      jPanel1.add(senderTf, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 113, 0));
      jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
      jPanel1.add(portTf, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 37, 0));
      jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 33), 0, 0));
      contentPane.add(jPanel2, BorderLayout.SOUTH);
      jPanel2.add(installButton, null);
      jPanel2.add(exitButton, null);
   }

   //Overridden so we can exit when window is closed
   protected void processWindowEvent(WindowEvent e) {
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         System.exit(0);
      }
   }

  void installButton_actionPerformed(ActionEvent e) {
    String portStr = portTf.getText();

    int port;
    String smtp;
    String sender;

    if ((portStr != null) && (portStr.length() > 0)) {
      try {
        port = Integer.parseInt(portStr);
      } catch (NumberFormatException nfe) {      
        JOptionPane.showMessageDialog(this,
          "Wrong http server port number", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      JOptionPane.showMessageDialog(this,
        "Please specifie a http server port number", "Input Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    smtp = smtpServerTf.getText();
    sender = senderTf.getText();
    
    WCInstall.install(smtp, sender, port);

    JOptionPane.showMessageDialog(this,
      "Installation successful");
    
  }


   void exitButton_actionPerformed(ActionEvent e) {
      System.exit(0);
   }





}

