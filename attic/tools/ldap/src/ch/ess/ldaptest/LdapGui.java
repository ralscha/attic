package ch.ess.ldaptest;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class LdapGui extends JFrame {
  
  private static final String defLdapUrl[] = {"ldap://193.73.4.134:389", "ldap://192.168.20.202:389"};
  private static final String defRootContext[] = {"DC=CH,DC=EURW,DC=EY,DC=NET", "DC=ANET,DC=ESS,DC=CH"};
  private static final String defLdapUserName[] = {"adminnaw", "mr"};
  private static final int defSelect = 0;

  private JTextField ldapUrlTextField;
  private JTextField rootContextTextField;
  private JTextField usernameTextField;
  private JTextField passwordTextField;
  private JTextField searchUserTextField;
  private JTextArea statusTextArea;
  
  LdapGui() {
    try {
      UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
    } catch (Exception e) {
      // Likely Plastic is not in the classpath; ignore it.
    }
    
    
    setTitle("LDAP Test");
    getContentPane().setLayout(new BorderLayout());

    FormLayout layout = new FormLayout("left:max(30dlu;p), 3dlu, p:grow",
      "p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 12dlu, p, 3dlu, p, 3dlu, p, 12dlu, p, 3dlu, fill:p:grow");
    
    
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    
    CellConstraints cc = new CellConstraints();

    int row = 1;    
    builder.addSeparator("Settings", cc.xywh(1, row, 3, 1));
    
    row += 2;
    builder.addLabel("LDAP Url", cc.xy(1, row));
    ldapUrlTextField = new JTextField(30);
    ldapUrlTextField.setText(defLdapUrl[defSelect]);
    builder.add(ldapUrlTextField, cc.xy(3, row));
    
    row += 2;
    builder.addLabel("Root Context (DC[389]: specify, GC[3268]: leave empty)", cc.xy(1, row));
    rootContextTextField = new JTextField(30);
    rootContextTextField.setText(defRootContext[defSelect]);
    builder.add(rootContextTextField, cc.xy(3, row));

    row += 2;
    builder.addLabel("Username", cc.xy(1, row));
    usernameTextField = new JTextField(30);
    usernameTextField.setText(defLdapUserName[defSelect]);
    builder.add(usernameTextField, cc.xy(3, row));

    row += 2;
    builder.addLabel("Passwort", cc.xy(1, row));
    passwordTextField = new JPasswordField(30);
    builder.add(passwordTextField, cc.xy(3, row));
    
    row += 2;
    builder.addSeparator("Input", cc.xywh(1, row, 3, 1));
    
    row += 2;
    builder.addLabel("Gesuchter User", cc.xy(1, row));
    searchUserTextField = new JTextField(30);
    builder.add(searchUserTextField, cc.xy(3, row));    
    
    row += 2;
    JButton startButton = new JButton("Suchen");
    builder.add(startButton, cc.xy(3, row));
    
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(@SuppressWarnings("unused") java.awt.event.ActionEvent e) {
        doSomething();        
      }}
    );
    
    row += 2;
    builder.addSeparator("Output", cc.xywh(1, row, 3, 1));
    
    row += 2;
    statusTextArea = new JTextArea(20, 100);
    statusTextArea.setEditable(false);
    JScrollPane pane = new JScrollPane(statusTextArea);
    builder.add(pane, cc.xywh(1, row, 3, 1));
        
    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    pack();
  }
  
  void doSomething() {
    statusTextArea.setText("");
    
    List<String> groups = getGroups(searchUserTextField.getText());
    for (String group : groups) {
      statusTextArea.append(group);
      statusTextArea.append("\n");
    }
  }
    
  private List<String> getGroups(String user)  {
    try {
    List<String> result = new ArrayList<String>();
    
    LdapSearch ldaps = new LdapSearch();
    ldaps.setLdapUrl(ldapUrlTextField.getText());
    ldaps.setRootContext(rootContextTextField.getText());
    ldaps.setLdapUserName(usernameTextField.getText());
    ldaps.setLdapPassword(passwordTextField.getText());
    
    Map groups = ldaps.getGroups(searchUserTextField.getText());
    Set groupNames = groups.entrySet();
    Iterator nai = groupNames.iterator();
    while (nai.hasNext()) {
      Map.Entry entry = (Map.Entry)nai.next();
      result.add("Group: " + entry.getKey() + ", DN: " + entry.getValue());
    }

    return result;
    } catch (Exception e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      pw.close();
      statusTextArea.setText(sw.toString()); 
      return Collections.EMPTY_LIST;
    }
  }
  
  public static void main(String[] args) {
    new LdapGui().setVisible(true);

  }

}
