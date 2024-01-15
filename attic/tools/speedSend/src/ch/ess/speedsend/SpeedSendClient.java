package ch.ess.speedsend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SpeedSendClient extends JFrame {

  JTextField statusLabel;
  JTextArea runningStatusTextArea;
  JProgressBar progressBar;
  ProfileManager profileManager = new ProfileManager();

  final JComboBox profileComboBox;
  JButton saveProfileButton;
  JButton deleteProfileButton;
  JButton showFileDialogJava;
  JButton startButton;
  JButton cancelButton;
  JTextField remoteCompareFileTextField;
  JTextField remoteFileTextField;
  JTextField server;
  JTextField localFileTextField;
  TransferWorker transferWorker = null;
  long startTime;
  JLabel dauerLabel;
  JLabel dauerLabelLabel;
  NumberFormat nf = new DecimalFormat("00");
  
  SpeedSendClient() {

    try {
      profileManager.load("profile");
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    }

    try {
      UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
    } catch (Exception e) {
      // Likely Plastic is not in the classpath; ignore it.
    }

    setTitle("Speed Send");
    getContentPane().setLayout(new BorderLayout());

    FormLayout layout = new FormLayout("left:max(30dlu;p), 3dlu, p:grow, 3dlu, p",
        "p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 10dlu, p, 3dlu, p, 3dlu, p, 3dlu, fill:p:grow");

    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();

    CellConstraints cc = new CellConstraints();

    int row = 1;

    builder.addLabel("Profil", cc.xy(1, row));
    profileComboBox = new JComboBox();

    for (String name : profileManager.getProfiles().keySet()) {
      profileComboBox.addItem(name);
    }
    profileComboBox.setEditable(true);
    
    
    JPanel profilPanel = new JPanel();
    profilPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    profilPanel.add(profileComboBox);

    saveProfileButton = new JButton("Speichern");
    deleteProfileButton = new JButton("Löschen");


    
    profilPanel.add(saveProfileButton);
    profilPanel.add(deleteProfileButton);

    builder.add(profilPanel, cc.xy(3, row));

    row += 2;
    builder.addSeparator("Transfer", cc.xywh(1, row, 5, 1));

    row += 2;
    builder.addLabel("Server", cc.xy(1, row));
    server = new JTextField();
    builder.add(server, cc.xy(3, row));

    row += 2;

    builder.addLabel("Lokales File", cc.xy(1, row));
    localFileTextField = new JTextField();
    builder.add(localFileTextField, cc.xy(3, row));

    final JFileChooser chooser = new JFileChooser();

    showFileDialogJava = new JButton("...");
    showFileDialogJava.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        chooser.setCurrentDirectory(new File(localFileTextField.getText()));
        if (chooser.showOpenDialog(SpeedSendClient.this) != JFileChooser.APPROVE_OPTION)
          return;
        File file = chooser.getSelectedFile();
        localFileTextField.setText(file.getPath());

      }
    });
    builder.add(showFileDialogJava, cc.xy(5, row));

    remoteCompareFileTextField = new JTextField();
    remoteFileTextField = new JTextField();

    row += 2;
    builder.addLabel("Remote File (Vergleich)", cc.xy(1, row));
    builder.add(remoteCompareFileTextField, cc.xy(3, row));

    row += 2;
    builder.addLabel("Remote File", cc.xy(1, row));
    builder.add(remoteFileTextField, cc.xy(3, row));


    profileComboBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
                
        ProfileItem item = profileManager.getProfile((String)e.getItem());
        if (item != null) {
          server.setText(item.getHost());
          localFileTextField.setText(item.getLocalFile());
          remoteCompareFileTextField.setText(item.getRemoteFileCompare());
          remoteFileTextField.setText(item.getRemoteFile());
        } else {
          server.setText(null);
          localFileTextField.setText(null);
          remoteCompareFileTextField.setText(null);
          remoteFileTextField.setText(null);
        }
      }
    });
    
    deleteProfileButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        profileManager.remove((String)profileComboBox.getSelectedItem());
        profileComboBox.removeItem(profileComboBox.getSelectedItem());
        try {
          profileManager.save("profile");
        } catch (IOException e1) {          
          e1.printStackTrace();
        }
        
        if (profileComboBox.getItemCount() > 0) {
          profileComboBox.setSelectedIndex(0);
          ProfileItem item = profileManager.getProfile((String)profileComboBox.getSelectedItem());
          server.setText(item.getHost());
          localFileTextField.setText(item.getLocalFile());
          remoteCompareFileTextField.setText(item.getRemoteFileCompare());
          remoteFileTextField.setText(item.getRemoteFile());
        } else {
          server.setText(null);
          localFileTextField.setText(null);
          remoteCompareFileTextField.setText(null);
          remoteFileTextField.setText(null);
        }
      }
    });
    
    saveProfileButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        
        String profileName = (String)profileComboBox.getSelectedItem();
        if ((profileName == null) || profileName.trim().equals("")) {
          return;
        }
        
        String host = server.getText();
        String localFileName = localFileTextField.getText();
        String remoteCompareFileName = remoteCompareFileTextField.getText();
        String remoteFileName = remoteFileTextField.getText();
        if ((host != null) && (host.trim().equals(""))) {
          host = null;
        }
        if ((localFileName != null) && (localFileName.trim().equals(""))) {
          localFileName = null;
        }
        if ((remoteCompareFileName != null) && (remoteCompareFileName.trim().equals(""))) {
          remoteCompareFileName = null;
        }
        if ((remoteFileName != null) && (remoteFileName.trim().equals(""))) {
          remoteFileName = null;
        }
 
        if ((host != null) && (localFileName != null) && (remoteCompareFileName != null)) {
        
          ProfileItem item = new ProfileItem();
          item.setHost(host);
          item.setLocalFile(localFileName);
          item.setRemoteFileCompare(remoteCompareFileName);
          item.setRemoteFile(remoteFileName);
          
          profileManager.add(profileName, item);
          
          profileComboBox.removeAllItems();
          
          for (String name : profileManager.getProfiles().keySet()) {
            profileComboBox.addItem(name);
          }
          
          try {
            profileManager.save("profile");
          } catch (IOException e1) {          
            e1.printStackTrace();
          }
        }        
      }
    });
    
    startButton = new JButton("Starten");


    cancelButton = new JButton("Abbrechen");
    cancelButton.setEnabled(false);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(startButton);
    buttonPanel.add(cancelButton);
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    row += 2;
    builder.addLabel("Transfer", cc.xy(1, row));
    builder.add(buttonPanel, cc.xy(3, row));

    row += 2;
    builder.addSeparator("Progress", cc.xywh(1, row, 5, 1));
    progressBar = new JProgressBar(0, 100);
    row += 2;
    builder.add(progressBar, cc.xywh(1, row, 5, 1));

    row += 2;
    dauerLabel = new JLabel("");
    dauerLabelLabel = new JLabel(" ");   
    builder.add(dauerLabelLabel, cc.xy(1, row));
    builder.add(dauerLabel, cc.xy(3, row));
    
    runningStatusTextArea = new JTextArea(8, 50);
    runningStatusTextArea.setEditable(false);
    JScrollPane pane = new JScrollPane(runningStatusTextArea);
    
    row += 2;
    System.out.println(row);
    builder.add(pane, cc.xywh(1, row, 5, 1));
    
    
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent event) {
    
        String host = server.getText();
        String localFileName = localFileTextField.getText();
        String remoteCompareFileName = remoteCompareFileTextField.getText();
        String remoteFileName = remoteFileTextField.getText();
        
        if ((host != null) && (host.trim().equals(""))) {
          host = null;
        }
        if ((localFileName != null) && (localFileName.trim().equals(""))) {
          localFileName = null;
        }
        if ((remoteCompareFileName != null) && (remoteCompareFileName.trim().equals(""))) {
          remoteCompareFileName = null;
        }
        if ((remoteFileName != null) && (remoteFileName.trim().equals(""))) {
          remoteFileName = null;
        }
        if ((host != null) && (localFileName != null) && (remoteCompareFileName != null)) {
          disableAll();
          
          transferWorker = new TransferWorker();
          
          transferWorker.addPropertyChangeListener(
              new PropertyChangeListener() {
                  public  void propertyChange(PropertyChangeEvent evt) {
                      if ("progress".equals(evt.getPropertyName())) {
                        Integer value = (Integer)evt.getNewValue();
                        if (value > 100) {
                          value = 100;
                        }
                        
                        if ((value < 100) && (value > 0)) {
                          long ms = (System.currentTimeMillis()-startTime) / value * (100-value);
                          int s = (int)(ms / 1000);
                          int m = s / 60;
                          int h = m / 60;
                          
                          s = s - (m*60);
                          m = m - (h*60);                          
                          dauerLabelLabel.setText("Geschätzte Dauer");
                          dauerLabel.setText(nf.format(h)+":"+nf.format(m)+":"+nf.format(s) + " hh:mm:ss");
                        } else {
                          dauerLabelLabel.setText(" ");
                          dauerLabel.setText("");
                        }
                        progressBar.setValue(value);
                      }
                  }
              });          
          
          progressBar.setValue(0);
          transferWorker.setClient(SpeedSendClient.this);
          transferWorker.setHost(host);
          transferWorker.setLocalFileName(localFileName);
          transferWorker.setRemoteCompareFileName(remoteCompareFileName);
          transferWorker.setRemoteFileName(remoteFileName);
          
          runningStatusTextArea.setText(null);
          statusLabel.setText("Working...");
          transferWorker.execute();
          startTime = System.currentTimeMillis();
                    
        }

      }
    });
    
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
        if (transferWorker != null) {
          statusLabel.setText("Cancelling...");
          transferWorker.cancel(false);    
          dauerLabelLabel.setText(" ");
          dauerLabel.setText("");
        }
        
      }
    });
    
    if (profileComboBox.getItemCount() > 0) {
      profileComboBox.setSelectedIndex(0);
      ProfileItem item = profileManager.getProfile((String)profileComboBox.getSelectedItem());
      server.setText(item.getHost());
      localFileTextField.setText(item.getLocalFile());
      remoteCompareFileTextField.setText(item.getRemoteFileCompare());
      remoteFileTextField.setText(item.getRemoteFile());
    }
    
    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

    statusLabel = new JTextField("Ready");
    statusLabel.setEditable(false);
    getContentPane().add(statusLabel, BorderLayout.SOUTH);

    //Menu
//    JMenuBar menuBar = new JMenuBar();
//    JMenu fileMenu = new JMenu();
//    fileMenu.setText("File");
//
//    JMenuItem exitItem = new JMenuItem();
//    exitItem.setText("Exit");
//    exitItem.addActionListener(new ActionListener() {
//      public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
//        System.exit(0);
//      }
//    });
//
//    fileMenu.add(exitItem);
//    menuBar.add(fileMenu);
//    setJMenuBar(menuBar);

    //setSize(520, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    locateOnScreenCenter(this);
    pack();
  }
  
  public void disableAll() {
    profileComboBox.setEnabled(false);
    saveProfileButton.setEnabled(false);
    deleteProfileButton.setEnabled(false);
    showFileDialogJava.setEnabled(false);
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    remoteCompareFileTextField.setEnabled(false);
    remoteFileTextField.setEnabled(false);
    server.setEnabled(false);
    localFileTextField.setEnabled(false);
  }
  
  public void enableAll() {
    profileComboBox.setEnabled(true);
    saveProfileButton.setEnabled(true);
    deleteProfileButton.setEnabled(true);
    showFileDialogJava.setEnabled(true);
    startButton.setEnabled(true);
    cancelButton.setEnabled(false);
    remoteCompareFileTextField.setEnabled(true);
    remoteFileTextField.setEnabled(true);
    server.setEnabled(true);
    localFileTextField.setEnabled(true);
  }

  public JTextArea getRunningStatusTextArea() {
    return runningStatusTextArea;
  }

  public JTextField getStatusLabel() {
    return statusLabel;
  }


  public void locateOnScreenCenter(Component component) {
    Dimension paneSize = component.getSize();
    Dimension screenSize = component.getToolkit().getScreenSize();
    component.setLocation(
        (screenSize.width  - paneSize.width)  / 2,
        (screenSize.height - paneSize.height) / 2);
  }
  
  public static void main(String[] args) {
    new SpeedSendClient().setVisible(true);
  }
}
