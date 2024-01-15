package ch.ess.tools;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Gui extends JFrame {

  private static String[] javaTemplateFiles = {"editaction.vm", "listaction.vm", "form.vm"};

  private static String[] jspTemplateFiles = {"list.jsp.vm", "edit.jsp.vm"};

  private static String[] javaOutputFiles = {"Edit${objectName}Action.java",
      "List${objectName}Action.java", "${objectName}Form.java"};

  private static String[] jspOutputFiles = {"${objectLowercaseNames}.jsp", "edit${objectLowercaseName}.jsp"};

  JTextField packageText;
  JTextField objectNameText;
  JTextField objectLowercaseNameText;
  JTextField objectNamesText;
  JTextField objectLowercaseNamesText;
  JTextField rechtText;
  JTextArea strutsText;
  JTextArea keysText;

  JTextField javaPathText;
  JTextField jspPathText;

  private JTextField status;

  public Gui(String path) {

    try {
      initComponents(path);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void initComponents(String path) {

    try {
      UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
    } catch (Exception e) {
      // no action
    }

    setTitle("ConTracker SourceGen");

    getContentPane().setLayout(new BorderLayout());

    packageText = new JTextField("ch.ess.ct.web.dienste");
    objectNameText = new JTextField("Projekt");
    objectNamesText = new JTextField("Projekte");
    
    objectLowercaseNameText = new JTextField("projekt");
    objectLowercaseNamesText = new JTextField("projekte");
    
    rechtText = new JTextField("dienste.projekte");
    strutsText = new JTextArea(15, 123);
    strutsText.setEditable(false);    
    strutsText.setFont(new Font("Courier", Font.PLAIN, 11));
    
    keysText = new JTextArea(5, 123);
    keysText.setEditable(false);    
    keysText.setFont(new Font("Courier", Font.PLAIN, 11));
    
    
    javaPathText = new JTextField(path, 40);
    jspPathText = new JTextField(path, 40);

    packageText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        packageText.select(0, packageText.getText().length());
      }
    });
    objectNameText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        objectNameText.select(0, objectNameText.getText().length());
      }
    });
    objectNamesText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        objectNamesText.select(0, objectNamesText.getText().length());
      }
    });
    objectLowercaseNameText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        objectLowercaseNameText.select(0, objectLowercaseNameText.getText().length());
      }
    });
    objectLowercaseNamesText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        objectLowercaseNamesText.select(0, objectLowercaseNamesText.getText().length());
      }
    });  
    rechtText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        rechtText.select(0, rechtText.getText().length());
      }
    });    
    javaPathText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        javaPathText.select(0, javaPathText.getText().length());
      }
    });
    jspPathText.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jspPathText.select(0, jspPathText.getText().length());
      }
    });

    FormLayout layout = new FormLayout("left:max(30dlu;p), 4dlu, p:grow, 4dlu, p",
        "p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 10dlu, p, 2dlu, p, 2dlu, p, 2dlu, p:grow, 2dlu, p, 20dlu, p");

    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();

    builder.addSeparator("Config", cc.xywh(1, 1, 5, 1));

    builder.addLabel("Package", cc.xy(1, 3));
    builder.add(packageText, cc.xy(3, 3));

    builder.addLabel("Objektname Einzahl", cc.xy(1, 5));
    builder.add(objectNameText, cc.xy(3, 5));

    builder.addLabel("Objektname Mehrzahl", cc.xy(1, 7));
    builder.add(objectNamesText, cc.xy(3, 7));    
    
    builder.addLabel("Objektname klein Einzahl", cc.xy(1, 9));
    builder.add(objectLowercaseNameText, cc.xy(3, 9));

    builder.addLabel("Objektname klein Mehrzahl", cc.xy(1, 11));
    builder.add(objectLowercaseNamesText, cc.xy(3, 11));
    
    builder.addLabel("Recht", cc.xy(1, 13));
    builder.add(rechtText, cc.xy(3, 13));
    
    
    builder.addSeparator("Output", cc.xywh(1, 15, 5, 1));

    builder.addLabel("Java", cc.xy(1, 17));
    builder.add(javaPathText, cc.xy(3, 17));

    final JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(path));
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    JButton showFileDialogJava = new JButton("...");
    showFileDialogJava.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        chooser.setCurrentDirectory(new File(javaPathText.getText()));
        if (chooser.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        javaPathText.setText(file.getPath());

      }
    });
    builder.add(showFileDialogJava, cc.xy(5, 17));

    builder.addLabel("JSP", cc.xy(1, 19));
    builder.add(jspPathText, cc.xy(3, 19));

    JButton showFileDialogJsp = new JButton("...");
    showFileDialogJsp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        chooser.setCurrentDirectory(new File(jspPathText.getText()));
        if (chooser.showOpenDialog(Gui.this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        jspPathText.setText(file.getPath());

      }
    });

    builder.add(showFileDialogJsp, cc.xy(5, 19));

    builder.add(new JScrollPane(strutsText), cc.xywh(1, 21, 5, 1));
    builder.add(new JScrollPane(keysText), cc.xywh(1, 23, 5, 1));
    
    JButton show = new JButton("Create Source");
    show.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        createSource();
      }
    });
        
    builder.add(show, cc.xywh(1, 25, 5, 1));

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

    try {
      InputStream is = Gui.class.getResourceAsStream("/velocity.properties");
      Properties props = new Properties();
      props.load(is);
      is.close();
      Velocity.init(props);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String path = null;
    if (args.length == 1) {
      path = args[0];
    } else {
      File currentDir = new File(".");
      try {
        path = currentDir.getCanonicalPath();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    new Gui(path).setVisible(true);
  }

  void createSource() {
    try {
      VelocityContext context = new VelocityContext();
      context.put("package", packageText.getText());
      context.put("objectName", objectNameText.getText());
      context.put("objectLowercaseName", objectLowercaseNameText.getText());

      context.put("objectNames", objectNamesText.getText());
      context.put("objectLowercaseNames", objectLowercaseNamesText.getText());
      context.put("recht", rechtText.getText() + ".bearbeiten");
      context.put("rechtparent", rechtText.getText());
      
      context.put("deletename", "${deletename}");
      context.put("deletable", "${"+objectLowercaseNameText.getText()+"Form.deletable}");
      context.put("geloescht", "${"+objectLowercaseNameText.getText()+"Form.geloescht}");
      context.put("notdeleted", "${not deleted}");
      context.put("formname", "${"+objectLowercaseNameText.getText()+"Form.name}");
      
      String javaOutputPath = javaPathText.getText();
      File f = new File(javaOutputPath);
      if (!f.exists()) {
        f.mkdirs();
      }
      createFile(context, javaOutputFiles, javaTemplateFiles, javaOutputPath);

      String jspOutputPath = jspPathText.getText();
      f = new File(jspOutputPath);
      if (!f.exists()) {
        f.mkdirs();
      }
      createFile(context, jspOutputFiles, jspTemplateFiles, jspOutputPath);

      
      //read strutsconfig file
      StringBuffer sb = new StringBuffer(1000);
      InputStream is = getClass().getResourceAsStream("/ch/ess/tools/template/strutsconfig.vm");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }      
      br.close();
      is.close();
      
      StringWriter w = new StringWriter();
      Velocity.evaluate(context, w, "output", sb.toString());
      String output = w.getBuffer().toString();
      strutsText.setText(output);
      
      
      //read key file
      sb = new StringBuffer(1000);
      is = getClass().getResourceAsStream("/ch/ess/tools/template/keys.vm");
      br = new BufferedReader(new InputStreamReader(is));
      
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }      
      br.close();
      is.close();
      
      w = new StringWriter();
      Velocity.evaluate(context, w, "output", sb.toString());
      output = w.getBuffer().toString();
      keysText.setText(output);      
      
      status.setText("Source created");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void createFile(VelocityContext context, String[] files, String[] templates, String outputPath)
      throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException, Exception {
    for (int i = 0; i < templates.length; i++) {

      StringWriter w = new StringWriter();
      Velocity.evaluate(context, w, "output", files[i]);
      String output = w.getBuffer().toString();

      PrintWriter pw = new PrintWriter(new FileWriter(outputPath + "/" + output));
      Velocity.mergeTemplate("ch/ess/tools/template/" + templates[i], "UTF-8", context, pw);
      pw.close();

    }
  }

}
