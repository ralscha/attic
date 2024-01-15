import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.beans.*; //Property change stuff
import java.awt.*;
import java.awt.event.*;
import common.swing.*;

public class DialogDemo extends JPanel {

    public DialogDemo(JFrame frame) {
        final JFrame thisframe = frame;
        final LoginDialog customDialog = new LoginDialog(frame, "Login to Test", new LoginValidator() {
        					public boolean validate(String name, String password) {
        						if (name.equals(password))
        							return true;
        						else
        							return false;
        					}
        	
							public void validationCancelled() {
								System.exit(1);
							}
        				
        				});
        customDialog.pack();

        JButton showItButton = new JButton("Show it!");
        showItButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	             customDialog.setLocationRelativeTo(thisframe);
                 customDialog.setVisible(true);

            		/*
                 String s = customDialog.getUserID();
                 if (s != null) {
                 		System.out.println(s);
                 }
                 */

            }
        });	
    	
    	add(showItButton);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DialogDemo");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(1,1));
        contentPane.add(new DialogDemo(frame));

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}