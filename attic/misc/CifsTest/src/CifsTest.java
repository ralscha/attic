import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import org.apache.commons.io.IOUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CifsTest extends JFrame {

  public CifsTest() {
    
    setTitle("Cifs Test");

    getContentPane().setLayout(new BorderLayout());
    
    FormLayout layout = new FormLayout("left:max(30dlu;p), 4dlu, p:grow",
    "p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p");

    
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();

    builder.addLabel("Domain", cc.xy(1, 1));
    builder.addLabel("User", cc.xy(1, 3));
    builder.addLabel("Password", cc.xy(1, 5));
    builder.addLabel("Path", cc.xy(1, 7));
    builder.addLabel("LMv2", cc.xy(1, 9));
    
    final JTextField domain = new JTextField(30);
    final JTextField user = new JTextField("SA-FX-ContrackerFX", 30);
    final JTextField password  = new JTextField("Swisscom55", 30);
    final JTextField path = new JTextField("\\\\Direxexport\\DirexMedium01\\DirexMedium01.csv", 30);
    final JCheckBox lm3 = new JCheckBox();
    
    builder.add(domain, cc.xy(3, 1)); 
    builder.add(user, cc.xy(3, 3));
    builder.add(password, cc.xy(3, 5));
    builder.add(path, cc.xy(3, 7));
    
    builder.add(lm3, cc.xy(3, 9));
    
    JButton testButton = new JButton("Test");
    builder.add(testButton, cc.xy(3, 11));
    
    
    testButton.addActionListener(new ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        
        if (lm3.isSelected()) {
          System.out.println("SET LM Compatibility");
          jcifs.Config.setProperty( "jcifs.smb.lmCompatibility", "3");
        }
        
        String userString = "";
        String urlString = "smb://";
        if (domain.getText() != null && domain.getText().trim().length() > 0) {
          userString = domain.getText() + ";";
        }
        if (user.getText() != null && user.getText().trim().length() > 0) {
          userString += user.getText();
          if (password.getText() != null && password.getText().trim().length() > 0) {
            userString += ":" + password.getText();
          }
          userString += "@";
        }
        urlString += userString;
        
        String pathText = path.getText();
        pathText = pathText.replace('\\', '/');
        
        
        if (pathText.startsWith("//")) {
          urlString += pathText.substring(2);
        } else { 
          urlString += pathText;
        }
        
        
        System.out.println(urlString);
        
        String lmc = jcifs.Config.getProperty( "jcifs.smb.lmCompatibility");
        
        try {
          SmbFile smbFile = new SmbFile(urlString);
          
          InputStream is = smbFile.getInputStream();
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          
          try {
            int b = IOUtils.copy(is, bos);
            
            
            JOptionPane.showMessageDialog(null, "reading " + b + " bytes from " + urlString, "OK", JOptionPane.INFORMATION_MESSAGE);
          } finally {
            is.close();
            bos.close();
          }
        } catch (MalformedURLException e1) {
          JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE); 
        } catch (SmbException se) {
          StringBuffer sb = new StringBuffer();
          sb.append("Exception\n");
          sb.append(se.toString());
          sb.append("\n");
          sb.append("NT Status: " + Integer.toHexString(se.getNtStatus()));
          sb.append("\n");
          sb.append(lmc);
          JOptionPane.showMessageDialog(null, sb.toString(), "SMB Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e1) {
          JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        
      }
    }
      
    );
    
    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);
    pack();
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public static void main(String[] args) {
    new CifsTest().setVisible(true);
  }

}
