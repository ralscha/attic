package ch.ess.tools.schemacompare;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import ch.ess.tools.schemacompare.compare.TableComparer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Gui extends JFrame {


  /**
   * Comment for <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = -5547377448489169728L;
  
  
  JTextField oldDbDriver;
  JTextField oldDbUser;
  JTextField oldDbPassword;

  JTextField newDbDriver;
  JTextField newDbUser;
  JTextField newDbPassword;


  JTextField dbOldDatabaseUrl;
  JTextField dbNewDatabaseUrl;
  JTextArea outputArea;
  JButton compareButton;

  JTextField status;

  public Gui() {

    try {
      initComponents();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void initComponents() {

    try {
      UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
    } catch (Exception e) {
      // no action
    }

    setTitle("ESS DB Schema Comparer");

    getContentPane().setLayout(new BorderLayout());

    
    oldDbDriver = new JTextField("net.sourceforge.jtds.jdbc.Driver");
    oldDbUser = new JTextField("sa");
    oldDbPassword = new JTextField();
    
    newDbDriver = new JTextField("net.sourceforge.jtds.jdbc.Driver");
    newDbUser = new JTextField("sa");
    newDbPassword = new JTextField();
    
    dbOldDatabaseUrl = new JTextField("jdbc:jtds:sqlserver://localhost:1433/ct");
    dbNewDatabaseUrl = new JTextField("jdbc:jtds:sqlserver://localhost:1433/ct");
    outputArea = new JTextArea(15, 60);
    outputArea.setEditable(false);
    outputArea.setFont(new Font("Courier", Font.PLAIN, 11));
    
    status = new JTextField("Ready");
    status.setEditable(false);

    compareButton = new JButton("Compare Schemas");
    


    FormLayout layout = new FormLayout("left:max(30dlu;p), 4dlu, p:grow, 4dlu, p",
        "p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p,, 2dlu, p, 20dlu, p, 2dlu, fill:d:grow");

    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();

    builder.addSeparator("Config", cc.xywh(1, 1, 5, 1));

    builder.addLabel("Old Driver", cc.xy(1, 3));
    builder.add(oldDbDriver, cc.xy(3, 3));

    builder.addLabel("Old User", cc.xy(1, 5));
    builder.add(oldDbUser, cc.xy(3, 5));

    builder.addLabel("Old Password", cc.xy(1, 7));
    builder.add(oldDbPassword, cc.xy(3, 7));

    builder.addLabel("Old URL", cc.xy(1, 9));
    builder.add(dbOldDatabaseUrl, cc.xy(3, 9));
    
    
    
    builder.addLabel("New Driver", cc.xy(1, 11));
    builder.add(newDbDriver, cc.xy(3, 11));

    builder.addLabel("New User", cc.xy(1, 13));
    builder.add(newDbUser, cc.xy(3, 13));

    builder.addLabel("New Password", cc.xy(1, 15));
    builder.add(newDbPassword, cc.xy(3, 15));    

    builder.addLabel("New URL", cc.xy(1, 17));
    builder.add(dbNewDatabaseUrl, cc.xy(3, 17));

    
    compareButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        status.setText("Comparing....");
        compareSchema();
        status.setText("Ready");    
      }
    });

    builder.add(compareButton, cc.xy(3, 19));

    
    builder.addSeparator("Output", cc.xywh(1, 21, 5, 1));


    builder.add(new JScrollPane(outputArea), cc.xywh(1, 23, 5, 1));
   

    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

    status = new JTextField("Ready");
    status.setEditable(false);
    getContentPane().add(status, BorderLayout.SOUTH);

    //Menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu();
    fileMenu.setText("File");

    JMenuItem exitItem = new JMenuItem();
    exitItem.setText("Exit");
    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        System.exit(0);
      }
    });

    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    pack();
  }

  public static void main(String[] args) {
    new Gui().setVisible(true);
  }


  void compareSchema() {
    try {
      Class.forName(oldDbDriver.getText());
      Class.forName(newDbDriver.getText());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

    Connection conn = null;

    try {

      conn = DriverManager.getConnection(dbNewDatabaseUrl.getText(), newDbUser.getText(), newDbPassword.getText());
      Map newTable = DBSchemaCompare.readTables(conn);
      conn.close();
      
      conn = DriverManager.getConnection(dbOldDatabaseUrl.getText(), oldDbUser.getText(), oldDbPassword.getText());
      Map oldTable = DBSchemaCompare.readTables(conn);
      conn.close();
      conn = null;      
      
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      TableComparer comparer = new TableComparer(oldTable, newTable);
      comparer.compare(writer);

      outputArea.setText(sw.toString());
      writer.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
