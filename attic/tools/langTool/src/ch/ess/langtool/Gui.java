package ch.ess.langtool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.bushe.swing.event.EventBus;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


public class Gui extends JFrame {

  boolean exportMode = true;
  
  public Gui() {

    try {
      UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
    } catch (Exception e) {
      // Likely Plastic is not in the classpath; ignore it.
    }


    //Menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu();
    fileMenu.setText("File");

    JMenuItem exitItem = new JMenuItem();
    exitItem.setText("Exit");
    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent evt) {
        shutdown();
      }
    });

    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    FormLayout layout = new FormLayout("left:max(30dlu;p), 3dlu, p:grow, 3dlu, p",
        "p, 3dlu, p, 3dlu, p, 3dlu, p, 1dlu, p, 3dlu, fill:p:grow, 3dlu, p");

    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();

    final JFileChooser resourceFileChooser = new JFileChooser();
    resourceFileChooser.setFileFilter(new PropertiesFileFilter());
    resourceFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    final JFileChooser excelFileChooser = new JFileChooser();
    excelFileChooser.setFileFilter(new XlsFileFilter());
    excelFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    final JTextField resourceFileField = new JTextField();
    final JTextField excelFileField = new JTextField();
    
    final JButton goButton = new JButton("Export");
    goButton.setEnabled(false);

    
    resourceFileField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(@SuppressWarnings("unused") FocusEvent e) {
        if (resourceFileField.getText() == null || resourceFileField.getText().trim().equals("")) {
          goButton.setEnabled(false);
        } else {
          if (excelFileField.getText() != null && !excelFileField.getText().trim().equals("")) {
            goButton.setEnabled(true);
          }
        }
      }
    });

    excelFileField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(@SuppressWarnings("unused") FocusEvent e) {
        if (excelFileField.getText() == null || excelFileField.getText().trim().equals("")) {
          goButton.setEnabled(false);
        } else {
          if (resourceFileField.getText() != null && !resourceFileField.getText().trim().equals("")) {
            goButton.setEnabled(true);
          }
        }    
      }
    });
    
    final JCheckBox sortCheckBox = new JCheckBox();
    final JCheckBox onlyEmptyCheckBox = new JCheckBox();
    
    goButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")ActionEvent e) {
        
        if (exportMode) {
          EventBus.publish("clear", null);
          ExportTool.exportExcel(resourceFileField.getText() ,excelFileField.getText(), sortCheckBox.isSelected(),
              onlyEmptyCheckBox.isSelected());        
        } else {
          EventBus.publish("clear", null);
          ImportTool.importResourceFile(resourceFileField.getText(), excelFileField.getText());        
        }
        
      }
    });

    JPanel modePanel = new JPanel();
    modePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    ButtonGroup group = new ButtonGroup();
    JRadioButton importRadioButton = new JRadioButton("Import", false);
    JRadioButton exportRadioButton = new JRadioButton("Export", true);
    group.add(importRadioButton);
    group.add(exportRadioButton);

    modePanel.add(exportRadioButton);
    modePanel.add(importRadioButton);

    importRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent e) {
        goButton.setText("Import");
        exportMode = false;
      }
    });

    exportRadioButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent e) {
        goButton.setText("Export");
        exportMode = true;
      }
    });

    int row = 1;

    builder.addLabel("Modus: ", cc.xy(1, row));
    builder.add(modePanel, cc.xy(3, row));

    JButton showResourceFileDialogButton = new JButton("...");
    showResourceFileDialogButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent e) {
        resourceFileChooser.setCurrentDirectory(new File(resourceFileField.getText()));

        if (resourceFileChooser.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
          return;
        }
        File file = resourceFileChooser.getSelectedFile();
        resourceFileField.setText(file.getPath());
        
        if (excelFileField.getText() != null) {
          goButton.setEnabled(true);
        } else {
          goButton.setEnabled(false);
        }
      }
    });

    JButton showExcelFileDialogButton = new JButton("...");
    showExcelFileDialogButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused")
      ActionEvent e) {
        excelFileChooser.setCurrentDirectory(new File(excelFileField.getText()));
        if (excelFileChooser.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) {
          return;
        }
        File file = excelFileChooser.getSelectedFile();
        excelFileField.setText(file.getPath());
        
        if (resourceFileField.getText() != null) {
          goButton.setEnabled(true);
        } else {
          goButton.setEnabled(false);
        }
      }
    });

    row += 2;
    builder.addLabel("Resourcefile: ", cc.xy(1, row));
    builder.add(resourceFileField, cc.xy(3, row));
    builder.add(showResourceFileDialogButton, cc.xy(5, row));

    row += 2;
    builder.addLabel("Excelfile: ", cc.xy(1, row));
    builder.add(excelFileField, cc.xy(3, row));
    builder.add(showExcelFileDialogButton, cc.xy(5, row));

    
    row += 2;        
    JLabel sortLabel = new JLabel("Keys beim Export sortieren");
    JPanel sortPanel = new JPanel();
    sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    sortPanel.add(sortCheckBox);
    sortPanel.add(sortLabel);
    builder.add(sortPanel, cc.xy(3, row));
    
    
    row += 2;        
    JLabel onlyEmptyLabel = new JLabel("Nur noch nicht übersetzte Keys exportieren");
    JPanel onlyEmptyPanel = new JPanel();
    onlyEmptyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    onlyEmptyPanel.add(onlyEmptyCheckBox);
    onlyEmptyPanel.add(onlyEmptyLabel);
    builder.add(onlyEmptyPanel, cc.xy(3, row));
    
    row += 2;
    StatusTextArea infoArea = new StatusTextArea();
    JScrollPane scrollPane = new JScrollPane(infoArea);
    builder.add(scrollPane, cc.xywh(1, row, 5, 1));

    row += 2;
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.add(goButton);

    builder.add(buttonPanel, cc.xywh(1, row, 5, 1));

    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    locateOnScreenCenter(this);
    setTitle("Resource-Tool");
    //pack();

  }

  public void shutdown() {
    System.exit(0);
  }

  private void locateOnScreenCenter(Component component) {
    Dimension paneSize = component.getSize();
    Dimension screenSize = component.getToolkit().getScreenSize();
    component.setLocation((screenSize.width - paneSize.width) / 2, (screenSize.height - paneSize.height) / 2);
  }

  public static void main(String[] args) {
    
      new Gui().setVisible(true);
//      EventBus.publish("status", "All data loaded.");
//      EventBus.publish("log", "All data loaded1.");
//      Thread.sleep(1000);
//      EventBus.publish("log", "All data loaded2.");
//      Thread.sleep(1000);
//      EventBus.publish("log", "All data loaded3.");
//      Thread.sleep(2000);
//      EventBus.publish("clear", null);
//      EventBus.publish("status", "All data loaded.");
    
  }
}