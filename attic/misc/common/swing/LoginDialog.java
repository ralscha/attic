package common.swing;

import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {

	private final static String btnString1 = "OK";
	private final static String btnString2 = "Cancel";

	private final static String loginFailedMessage = "Login failed. Please try again.";	
	
	private JOptionPane optionPane;
	private JPasswordField passwdTF;
	private JTextField userTF;
	private LoginValidator validator;

	public LoginDialog(Frame parent, String title, LoginValidator validator) {
		super(parent, title, true);
		
		this.validator = validator;
		setResizable(false);

		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(buildDialogUI(), JOptionPane.QUESTION_MESSAGE,
                             		JOptionPane.YES_NO_OPTION,
                             		new ImageIcon( Toolkit.getDefaultToolkit().createImage(
                                  getClass().getResource("keylock.gif"))), options, options[0]);
		
		setContentPane(optionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent we) {
                  				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                  			}
                  		});	
		
		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
                			public void propertyChange(PropertyChangeEvent e) {
                				String prop = e.getPropertyName();

	            				if (isVisible() && (e.getSource() == optionPane) &&
	                    			(prop.equals(JOptionPane.VALUE_PROPERTY) ||
	                     		 prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
	                				
	                     		Object value = optionPane.getValue();
	                     						
	            					if (value == JOptionPane.UNINITIALIZED_VALUE) 
	                					return;

	                				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
	
	            					if (value.equals(btnString1)) {
		            					if (accept())
		                					setVisible(false);
		            				} else { // user closed dialog or clicked cancel
	            						cancel();
	                				}
	                			}
	                		}});
		pack();
	}



	protected boolean accept() {
		boolean ok = validator.validate(userTF.getText(), new String(passwdTF.getPassword()));
		if (ok)
			return(true);

		JOptionPane.showMessageDialog(null, loginFailedMessage);
		return(false);
	}

	protected void cancel() {
		validator.validationCancelled();
	}	

	public void setVisible(boolean flag) {
		
		if (flag) {			
			userTF.setText("");
			passwdTF.setText("");
			userTF.requestFocus();

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension size = getSize();
			screenSize.height = screenSize.height/2;
			screenSize.width = screenSize.width/2;
			size.height = size.height/2;
			size.width = size.width/2;
			setLocation(screenSize.width - size.width, screenSize.height - size.height);
		}
		super.setVisible(flag);

	}



	protected Component buildDialogUI() {

		JPanel main = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		main.setLayout(gb);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = gbc.NORTHWEST;
		gbc.fill = gbc.HORIZONTAL;
		gbc.gridwidth = 1;
		Insets insets0 = new Insets(0, 0, 5, 5);
		Insets insets1 = new Insets(0, 0, 5, 0);

		JLabel label = new JLabel("Username");
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets0;
		main.add(label, gbc);

		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		userTF = new JTextField(10);

		userTF.addActionListener(new ActionListener() {
                         			public void actionPerformed(ActionEvent evt) {
                         				passwdTF.requestFocus();
                         			}
                         		});
		gbc.insets = insets1;
		main.add(userTF, gbc);

		gbc.gridwidth = gbc.RELATIVE;
		gbc.weightx = 0;
		gbc.insets = insets0;
		label = new JLabel("Password");
		main.add(label, gbc);

		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		passwdTF = new JPasswordField(10);

		passwdTF.addActionListener(new ActionListener() {
	                          			public void actionPerformed(ActionEvent e) {
	                          				optionPane.setValue(btnString1);
	                          			}
	                          		});
		
		main.add(passwdTF, gbc);

		if ((getTitle() == null) || (getTitle().length() == 0))
			setTitle("Login");

		return(main);
	}


}