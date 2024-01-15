import javax.swing.*;
import java.beans.*; 
import java.awt.*;
import java.awt.event.*;

class CustomDialog extends JDialog {
	private String typedText = null;

	private JOptionPane optionPane;
 	private JPasswordField t_passwd;
	private JTextField t_user;
	
	public String getValidatedText() {
		return typedText;
	}

	public CustomDialog(Frame aFrame) {
		super(aFrame, true);


		final String btnString1 = "Logon";
		final String btnString2 = "Cancel";
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(buildDialogUI(), JOptionPane.QUESTION_MESSAGE,
                             		JOptionPane.YES_NO_OPTION, new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("keylock.gif"))), options, options[0]);
		setContentPane(optionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent we) {
                  				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                  			}
                  		}
                 		);

		/*
		textField.addActionListener(new ActionListener() {
                            			public void actionPerformed(ActionEvent e) {
                            				optionPane.setValue(btnString1);
                            			}
                            		}
                           		);
		*/
		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
                                     			public void propertyChange(PropertyChangeEvent e) {
                                     				String prop = e.getPropertyName();

                                 				if (isVisible() && (e.getSource() == optionPane) &&
                                         					(prop.equals(JOptionPane.VALUE_PROPERTY) ||
                                          					prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
                                     					Object value = optionPane.getValue();

                                     					System.out.println(value);
                                     					System.out.println(JOptionPane.UNINITIALIZED_VALUE);
                                 					if (value == JOptionPane.UNINITIALIZED_VALUE) {
                                     						//ignore reset
                                     						return;
                                     					}

                                     					optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

                                 					if (value.equals(btnString1)) {
                                     					//	typedText = textField.getText().toUpperCase().trim();

                                 						if (!typedText.equals(""))
                                     							setVisible(false);
                                 					} else { // user closed dialog or clicked cancel
                                     						typedText = null;
                                     						setVisible(false);
                                     					}
                                     				}
                                     			}
                                     		}
                                    		);
	}
	
	
	  protected Component buildDialogUI()
    {

    String loginFailedMessage = "wrong userid/password";
    
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
    //label.setOpaque(false);
    //label.setForeground(Color.black);
    gbc.gridwidth = gbc.RELATIVE;
    gbc.insets = insets0;
    main.add(label, gbc);

    gbc.gridwidth = gbc.REMAINDER;
    gbc.weightx = 1;
    t_user = new JTextField(10);
    t_user.requestFocus();
    	
    t_user.addActionListener(new ActionListener()
      {
      public void actionPerformed(ActionEvent evt)
        {
        t_passwd.requestFocus();
        }
      });
    gbc.insets = insets1;
    main.add(t_user, gbc);

    gbc.gridwidth = gbc.RELATIVE;
    gbc.weightx = 0;
    gbc.insets = insets0;    
    label = new JLabel("Password");
    //label.setOpaque(false);
    //label.setForeground(Color.black);
    main.add(label, gbc);

    gbc.gridwidth = gbc.REMAINDER;
    gbc.weightx = 1;
    gbc.insets = insets1;    
    t_passwd = new JPasswordField(10);
    //registerTextInputComponent(t_passwd);
    main.add(t_passwd, gbc);

    if ((getTitle() == null) || (getTitle().length() == 0))
      setTitle("Login");
    
    return(main);
    }
	
	

}