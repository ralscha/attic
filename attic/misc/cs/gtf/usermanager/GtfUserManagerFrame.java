package gtf.usermanager;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.sql.*;
import java.text.*;
import java.lang.reflect.InvocationTargetException;
import EDU.oswego.cs.dl.util.concurrent.TimeoutException;

import com.klg.jclass.util.swing.*;

import gtf.db.*;
import gtf.common.*;

import common.swing.*;

public class GtfUserManagerFrame extends JExitFrame {
	
	private final static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
	
	private JPanel jPanel;
	private JLabel jlblStatus;
	private Blip blip;
	private transient SwingWorker worker;	
	
	private UserTableModel userModel;
	private JCSortableTable userTable;
	private ListSelectionModel userSelectionModel;
	
	private JTextField userIDTF;
	private JTextField nameTF;
	private JTextField phoneTF;
	private JTextField orgTF;

	private JTextField expiryTF;
	private JTextField passwordTF;
	private PriviledgesTableModel priviledgesModel;
	private ServiceCenters serviceCenters = null;
	private USER selectedUser = null;
	private USER newUser = null;
	private JButton resetPWButton;
	private JButton commitButton;
	String selectedPriviledges = null;

	private JMenuItem addCopyMenuItem;
	private BusyCursor busyCursor;
	
	public GtfUserManagerFrame() {
		super("Gtf User Manager V1.1");
		initComponents();
		
		ImageIcon ii = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("people01.gif")));
		this.setIconImage(ii.getImage());
		
		busyCursor = new BusyCursor(this);
	}

	protected void doExit() {
		serviceCenters.cleanUp();
		super.doExit();
	}	
	
	private void initComponents() {

		blip = new Blip ();
		jlblStatus = new JLabel();

		//Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenu addMenu = new JMenu("Add New User");
		JMenuItem addNewMenuItem = new JMenuItem("New...");
		addCopyMenuItem = new JMenuItem("Copy Selected User...");
		addCopyMenuItem.setEnabled(false);
		addMenu.add(addNewMenuItem);
		addMenu.add(addCopyMenuItem);
		
		JMenuItem lockItem = new JMenuItem("Lock Selected User");
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");

		fileMenu.setActionCommand("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		addMenu.setMnemonic('A');
		fileMenu.add(addMenu);

		fileMenu.add(new JSeparator());

		lockItem.setActionCommand("Lock");
		lockItem.setMnemonic('L');
		fileMenu.add(lockItem);
		
		fileMenu.add(new JSeparator());
				
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.setMnemonic('x');
		fileMenu.add(exitMenuItem);

		setJMenuBar(menuBar);

		fileMenu.add(new JSeparator());
		
		lockItem.addActionListener(new ActionListener() {
              			public void actionPerformed(ActionEvent ae) {
              				
             					if (selectedUser != null) {
	             					try {
	             						busyCursor.setBusy(true);
	             						
	             						USERTable ut = new USERTable(serviceCenters.getSelectedServiceCenter().getConnection());
	                 					int rc = ut.lockUser(selectedUser);
	             						
	             						if (rc == 1) {
	             							startWorker("User " + selectedUser.getUSERID().trim() + " locked");
	             						} else {
	                 						jlblStatus.setText("Lock " + selectedUser.getUSERID().trim() + " failed : "+rc);
	             							busyCursor.setBusy(false);	
	             						}

	             					} catch (SQLException sqle) {
	                 					jlblStatus.setText(sqle.toString());
	             						busyCursor.setBusy(false);
	                 				}
                 				}}});	

		
		addNewMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent event) {
									showNewUserDialog();									
								}
							});

		addCopyMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent event) {
									showNewCopyUserDialog();									
								}
							});		
		
		exitMenuItem.addActionListener(new ActionListener() {
                               			public void actionPerformed(ActionEvent event) {
                               				GtfUserManagerFrame.this.doExit();
                               			}
                               		});


		getContentPane().setLayout(new BorderLayout());

		userModel = new UserTableModel();
		userTable = new JCSortableTable(userModel) {
						private boolean first = true;
			
						public void sort(int column) {
							super.sort(column);	
							if (!first)
								updateUserDetails();
							else
								first = false;
						}
					};

		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		userSelectionModel = userTable.getSelectionModel();
		// ServiceCenter List
		JPanel serviceListPanel = new JPanel();

		serviceListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel scLabel = new JLabel("Service Center");

		serviceListPanel.add(scLabel);

		serviceCenters = new ServiceCenters();
		JComboBox jcb = serviceCenters.getComboBox();

		try {
			userModel.populateData(serviceCenters.getSelectedServiceCenter().getConnection());
			userModel.fireTableDataChanged();
			userTable.sort(0);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	
		serviceListPanel.add(jcb);
		serviceCenters.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent ae) {
            				busyCursor.setBusy(true);
            				userSelectionModel.clearSelection();
							startWorker("Ready");
       	  			}});
		
		getContentPane().add(serviceListPanel, BorderLayout.NORTH);

		for (int i = 0; i < userModel.getColumnCount(); i++) {
			TableColumn col = userTable.getColumn(userModel.getColumnName(i));
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(userModel.getAlignment(i));
			col.setCellRenderer(dtcr);
			col.setPreferredWidth(userModel.getWidth(i));
		}

		JScrollPane userTableScrollPane = new JScrollPane(userTable);

		getContentPane().add(userTableScrollPane, BorderLayout.WEST);
		
		userSelectionModel.addListSelectionListener(new ListSelectionListener() {
          			public void valueChanged(ListSelectionEvent e) {
            				if (e.getValueIsAdjusting()) return;
          					updateUserDetails();
          				}});


		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		//User Details
		JPanel jp = new JPanel();

		// set layout for container
		JCAlignLayout layout = new JCAlignLayout(2, 5, 5);
		jp.setLayout(layout);


		DocumentListener dl = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				checkCommitButton();
			}
			
			public void removeUpdate(DocumentEvent e) {
				checkCommitButton();
			}
			
			public void changedUpdate(DocumentEvent e) {}
		};
		
		/*
		FocusAdapter focusAdapter = new FocusAdapter() {
							public void focusLost(FocusEvent e) {
								checkCommitButton();
							}};
		*/
		jp.add(new JLabel("UserID"));
		userIDTF = new JTextField("", 8);
		userIDTF.setEditable(false);
		jp.add(userIDTF);

		jp.add(new JLabel("Name"));
		nameTF = new JTextField("", 20);
		//nameTF.addFocusListener(focusAdapter);		
		nameTF.getDocument().addDocumentListener(dl);
		jp.add(nameTF);

		jp.add(new JLabel("Phone"));
		phoneTF = new JTextField("", 15);
		//phoneTF.addFocusListener(focusAdapter);	
		phoneTF.getDocument().addDocumentListener(dl);
		jp.add(phoneTF);

		jp.add(new JLabel("OrgUnit"));
		orgTF = new JTextField("", 8);
		//orgTF.addFocusListener(focusAdapter);
		orgTF.getDocument().addDocumentListener(dl);
		jp.add(orgTF);

		jp.add(new JLabel("Expiry Date"));
		expiryTF = new JTextField("", 8);
		expiryTF.setEditable(false);
		
		jp.add(expiryTF);

		jp.add(new JLabel("Password"));
		passwordTF = new JTextField("", 25);
		passwordTF.setEditable(false);
		jp.add(passwordTF);

		rightPanel.add(jp, BorderLayout.NORTH);

		JPanel privPanel = new JPanel();
		privPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		priviledgesModel = new PriviledgesTableModel();
		priviledgesModel.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											checkCommitButton();
   										}
								});
		
		JTable priviledgesTable = new JTable(priviledgesModel);


		for (int i = 0; i < priviledgesModel.getColumnCount(); i++) {
			TableColumn col = priviledgesTable.getColumn(priviledgesModel.getColumnName(i));
			col.setPreferredWidth(priviledgesModel.getWidth(i));
		}

		priviledgesTable.setPreferredScrollableViewportSize(new Dimension(150, 220));
		JScrollPane scrollPane = new JScrollPane(priviledgesTable);
		privPanel.add(scrollPane, BorderLayout.WEST);


		rightPanel.add(privPanel, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		resetPWButton = new JButton("Reset Password");
		resetPWButton.setEnabled(false);
		
		commitButton = new JButton("Commit Changes");
		commitButton.setEnabled(false);
		
		
		commitButton.addActionListener(new ActionListener() {
              			public void actionPerformed(ActionEvent ae) {
              				
             					if (selectedUser != null) {
             						try {
             							busyCursor.setBusy(true);
										USERTable ut = new USERTable(serviceCenters.getSelectedServiceCenter().getConnection());             						
	             						updateSelectedUser();
										int rc = ut.update(selectedUser);
	             						if (rc == 1) {
	             							startWorker("User " + selectedUser.getUSERID().trim() + " successfully changed");
	             						} else {
	                 						jlblStatus.setText("Commit Change for User " + selectedUser.getUSERID().trim() + " failed : "+rc);
	             							busyCursor.setBusy(false);
	             						}
		
		              
	             					} catch (SQLException sqle) {
	                 					jlblStatus.setText(sqle.toString());
	             						busyCursor.setBusy(false);
	                 				}
        
             					}
              			}});
		
		resetPWButton.addActionListener(new ActionListener() {
              			public void actionPerformed(ActionEvent ae) {
              				
             					if (selectedUser != null) {
	             					try {
	             						busyCursor.setBusy(true);
	             						USERTable ut = new USERTable(serviceCenters.getSelectedServiceCenter().getConnection());
	                 					int rc = ut.resetPassword(selectedUser);
	             						
	             						if (rc == 1) {
	             							startWorker("Password for " + selectedUser.getUSERID().trim() + " reseted");
	             						} else {
	                 						jlblStatus.setText("Reset Password for " + selectedUser.getUSERID().trim() + " failed : "+rc);
	             							busyCursor.setBusy(false);
	             						}

	             					} catch (SQLException sqle) {
	                 					jlblStatus.setText(sqle.toString());
	             						busyCursor.setBusy(false);
	                 				}
                 				}}});
		
		
		controlPanel.add(resetPWButton);
		controlPanel.add(commitButton);
		
		rightPanel.add(controlPanel, BorderLayout.SOUTH);

		JSplitPane splitPane =
  		new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userTableScrollPane, rightPanel);

		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(250);

		getContentPane().add(splitPane, BorderLayout.CENTER);

		// STATUS
		jPanel = new JPanel();

		jPanel.setLayout(new BorderLayout());



		jlblStatus.setToolTipText("Status");
		jlblStatus.setBorder( new CompoundBorder(
                        new CompoundBorder(new EmptyBorder(new Insets(1, 1, 1, 1)),
                        new LineBorder(Color.gray)), new EmptyBorder(new Insets(0, 2, 0, 0))));
		jlblStatus.setText("Ready");
		jPanel.add(jlblStatus, "Center");

		
		blip.setToolTipText ("Activity Indicator");
		blip.setPreferredSize (new java.awt.Dimension(23, 23));
		blip.setOpaque (true);
		blip.setBorder (new javax.swing.border.CompoundBorder (
		    new javax.swing.border.CompoundBorder (
		    new javax.swing.border.EmptyBorder (new java.awt.Insets(1, 1, 1, 1)),
		    new javax.swing.border.LineBorder (java.awt.Color.gray)),
		    new javax.swing.border.EmptyBorder (new java.awt.Insets(1, 1, 1, 1))));
		blip.setForeground (new java.awt.Color (153, 153, 204));
		
		jPanel.add (blip, "East");
		
		getContentPane().add(jPanel, BorderLayout.SOUTH);
	}

	private void updateUserDetails() {
		if (userSelectionModel.isSelectionEmpty()) {
			clearUserDetails();
		} else {
			int selectedRow = userSelectionModel.getMinSelectionIndex();
			setUserDetails((USER)userTable.getModel().getValueAt(selectedRow, 2));
		}
	}
	
	private void updateSelectedUser() {
		if (selectedUser == null) return ;
		
		selectedUser.setNAME(nameTF.getText().trim());
		selectedUser.setPHONE_NUMBER(phoneTF.getText().trim());
		selectedUser.setORGANIZATION_UNIT(orgTF.getText().trim());
		selectedUser.setPRIVILEDGES(priviledgesModel.getSelected());
	}
	
	private void checkCommitButton() {		
		if (selectedUser == null) return;
		
		String name   = nameTF.getText().trim();
		String phone  = phoneTF.getText().trim();
		String org    = orgTF.getText().trim();
				
		if (!selectedUser.getPHONE_NUMBER().trim().equals(phone) ||
			 !selectedUser.getNAME().trim().equals(name) ||
			 !selectedUser.getORGANIZATION_UNIT().trim().equals(org) ||
			 !priviledgesModel.getSelected().equals(selectedPriviledges))
			commitButton.setEnabled(true);
		else
			commitButton.setEnabled(false);
		
	}
	
	private void clearUserDetails() {
		selectedUser = null;
		newUser = null;
		
		userIDTF.setText("");
		nameTF.setText("");
		phoneTF.setText("");
		orgTF.setText("");
		expiryTF.setText("");
		
		passwordTF.setText("");
		priviledgesModel.clearSelected();
		selectedPriviledges = null;
		resetPWButton.setEnabled(false);
		commitButton.setEnabled(false);
		addCopyMenuItem.setEnabled(false);
	}

	private void setUserDetails(USER user) {
		
		selectedUser = user;
		if (user != null) {
			
			userIDTF.setText(user.getUSERID().trim());
			nameTF.setText(user.getNAME().trim());
			phoneTF.setText(user.getPHONE_NUMBER().trim());
			orgTF.setText(user.getORGANIZATION_UNIT().trim());
			expiryTF.setText(format.format(user.getEXPIRY_DATE()));
			
			passwordTF.setText(user.getENCRYPTED_PASSWORD().trim());
			priviledgesModel.setSelected(user.getPRIVILEDGES().trim());
			selectedPriviledges = priviledgesModel.getSelected();
			
			commitButton.setEnabled(false);
			if (user.getENCRYPTED_PASSWORD().trim().equals(USER.DEFAULT_PASSWORD) ||
				user.getENCRYPTED_PASSWORD().trim().equals(USER.LOCK_PASSWORD))
					
				resetPWButton.setEnabled(false);
			else
				resetPWButton.setEnabled(true);			
			
			addCopyMenuItem.setEnabled(true);
		} else {
			clearUserDetails();
		}
	}

	private void showNewUserDialog() {

		InputUserIDDialog userIDDialog = new InputUserIDDialog(this);
		userIDDialog.setLocationRelativeTo(this);
		userIDDialog.pack(); 
		userIDDialog.setVisible(true);
		
		String s = userIDDialog.getUserID();
		if (s != null) {
			newUser = new USER(s);
			setUserDetails(newUser);
			nameTF.requestFocus();
		} 
	}

	private void showNewCopyUserDialog() {

		InputUserIDDialog userIDDialog = new InputUserIDDialog(this);
		userIDDialog.setLocationRelativeTo(this);
		userIDDialog.pack(); 
		userIDDialog.setVisible(true);
		
		String s = userIDDialog.getUserID();
		if (s != null) {
			newUser = new USER(s);
			if (selectedUser != null) {				
				newUser.setPHONE_NUMBER(selectedUser.getPHONE_NUMBER());
				newUser.setORGANIZATION_UNIT(selectedUser.getORGANIZATION_UNIT());
				newUser.setPRIVILEDGES(selectedUser.getPRIVILEDGES());
			}
			
			setUserDetails(newUser);
			nameTF.requestFocus();
		} 
	}
	
	protected void startWorker(final String message) {
		
        worker = new SwingWorker() {
			 public long getTimeout() { return 10000; }  
        	
			 public Object construct() throws Exception {			 	
				userModel.populateData(serviceCenters.getSelectedServiceCenter().getConnection());
				return null;
            }
        	
            public void finished() {
                   
                if (worker == this) {
                    worker = null;
                    blip.stop();
                }
            	
                try {
						Object o = get();

                		userModel.fireTableDataChanged();
                		userTable.sort(0);
						updateUserDetails();
						jlblStatus.setText(message);	                		
                } catch (InvocationTargetException ex) {
                    String msg = "Failed populate ";
                    Throwable err = ex.getTargetException();
                    if (err instanceof TimeoutException) {
                        msg += "timeout";
                    }
                    else if (err instanceof InterruptedException) {
                        msg += "interrupted";
                    }
                    else {
                        msg += err;
                    }
                    jlblStatus.setText(msg);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
            			busyCursor.setBusy(false);	                	
					}
            }
        };

        worker.start();
        jlblStatus.setText("Working...");
        blip.start();
    }
	
	
	protected void stopWorker() {
		if (worker != null) {
			worker.interrupt();
		}
	}

}