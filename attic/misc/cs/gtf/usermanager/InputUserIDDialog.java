package gtf.usermanager;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; 
import java.awt.*;
import java.awt.event.*;

class InputUserIDDialog extends JDialog {
	private String userID = null;

	private JOptionPane optionPane;

	public String getUserID() {
		return userID;
	}

	public InputUserIDDialog(Frame aFrame) {
		super(aFrame, true);

		setTitle("New User");

		final String msgString1 = "Enter new user id";
		final JTextField textField = new JTextField(8);
		Object[] array = {msgString1, textField};

		final String btnString1 = "Enter";
		final String btnString2 = "Cancel";
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.YES_NO_OPTION, null, options, options[0]);
		setContentPane(optionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent we) {
                  				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                  			}
                  		});

		textField.addActionListener(new ActionListener() {
                            			public void actionPerformed(ActionEvent e) {
                            				optionPane.setValue(btnString1);
                            			}
                            		});

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
                			public void propertyChange(PropertyChangeEvent e) {
                				String prop = e.getPropertyName();

	            				if (isVisible() && (e.getSource() == optionPane) &&
										(prop.equals(JOptionPane.VALUE_PROPERTY) ||
										prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
	                				Object value = optionPane.getValue();
	
	            					if (value == JOptionPane.UNINITIALIZED_VALUE) {
		          						//ignore reset
		          						return;
	                				}
	
	                				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
	
	            					if (value.equals(btnString1)) {
	                					userID = textField.getText().toUpperCase().trim();
	
	            						if (!userID.equals(""))
	                						setVisible(false);
	            					} else { // user closed dialog or clicked cancel
	                					userID = null;
	                					setVisible(false);
	                				}
	                			}
	                		}});
	}
}