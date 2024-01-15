
package gtf.tools;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import common.swing.*;
import gtf.common.*;
import gtf.db.*;
import java.util.*;
import java.sql.*;
import ViolinStrings.*;
import com.ibm.mask.imask.*;
import gtf.usermanager.*;

public class BUChanger extends JExitFrame {

	private ServiceCenters serviceCenters;
	private IMask dossierField;
	private JButton searchButton;
	private JLabel buLabel;
	private JLabel dossierLabel;
	private JButton changeButton;
	private JLabel approvalLabel;
	private JLabel itemLabel;
	private JLabel effBookLabel;
	private JLabel noteffBookLabel;
	private DOSSIER selectedLCDossier = null;
	private RC_DOSSIER selectedRCDossier = null;
	
	private JPanel jPanel;
	private JLabel jlblStatus;
	
	public BUChanger() {
		super("Gtf Dossier BU Changer");
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
       searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));

		
		JPanel leftPanel = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		leftPanel.setLayout(gb);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = gbc.WEST;
		gbc.fill = gbc.HORIZONTAL;
		Insets insets0 = new Insets(0, 5, 5, 5);
		Insets insets1 = new Insets(5, 0, 5, 5);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets0;
		
		JLabel scLabel = new JLabel("Service Center");
		scLabel.setDisplayedMnemonic('S');

		
		leftPanel.add(scLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		
		serviceCenters = new ServiceCenters();
		serviceCenters.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									clearLabels();
									selectedLCDossier = null;
									selectedRCDossier = null;
									jlblStatus.setText("Ready");	
								}
							});
		JComboBox jcb = serviceCenters.getComboBox();
		scLabel.setLabelFor(jcb);		

		jcb.requestFocus();
		leftPanel.add(jcb, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.weightx = 0;
		gbc.insets = insets0;
		JLabel dossierDescrLabel = new JLabel("Dossier No");

		dossierDescrLabel.setDisplayedMnemonic('D');
		dossierDescrLabel.setLabelFor(dossierField);
		
		leftPanel.add(dossierDescrLabel, gbc);

		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		
		DocumentListener dl = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if (dossierField.getData().trim().length() > 0)
					searchButton.setEnabled(true);
				else
					searchButton.setEnabled(false);
			}
			
			public void removeUpdate(DocumentEvent e) {
				if (dossierField.getData().trim().length() > 0)
					searchButton.setEnabled(true);
				else
					searchButton.setEnabled(false);
			}
			
			public void changedUpdate(DocumentEvent e) {}
		};

		
		dossierField = new IMask("#######", 5);
		
		dossierField.getDocument().addDocumentListener(dl);
				
		/*
		dossierField.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchButton.doClick();
                        }
                    }});
		*/
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		dossierField.getKeymap().removeKeyStrokeBinding(enter);
		
		
		leftPanel.add(dossierField, gbc);
		
		searchPanel.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		searchButton = new JButton("Search");
		searchButton.setEnabled(false);
		
		getRootPane().setDefaultButton(searchButton);
		
				
		rightPanel.add(searchButton);
		searchPanel.add(rightPanel);
		
		getContentPane().add(searchPanel, BorderLayout.NORTH);

		searchButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							try {
								
								try {
									int dno = Integer.parseInt(dossierField.getData().trim());
									if (dno >= 9500000) {
										selectedLCDossier = null;
										workRCDossier();
									} else {
										selectedRCDossier = null;
										workLCDossier();
									}
								} catch (NumberFormatException nfe) {
									
								}
								
								
								
							} catch (SQLException sqle) {
								System.out.println(sqle);
							}
							
						}
					});
		
		///////////////////////////////////
		
		JPanel dossierPanel = new JPanel();
		dossierPanel.setBorder(BorderFactory.createTitledBorder("Dossier"));
		
		dossierPanel.setLayout(gb);

		Insets insets2 = new Insets(5, 5, 5, 5);
		
		gbc.anchor = gbc.WEST;
		gbc.fill = gbc.HORIZONTAL;
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;
		JLabel dossierDescr2Label = new JLabel("Dossier No :");
		
		dossierPanel.add(dossierDescr2Label, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		dossierLabel = new JLabel();
		dossierLabel.setForeground(Color.black);
		dossierPanel.add(dossierLabel, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;		
		JLabel buDescrLabel = new JLabel("Business Unit : ");
		dossierPanel.add(buDescrLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		buLabel = new JLabel();
		buLabel.setForeground(Color.black);
		dossierPanel.add(buLabel, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;		
		JLabel itemDescrLabel = new JLabel("Item Type : ");
		dossierPanel.add(itemDescrLabel, gbc);

		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		itemLabel = new JLabel();
		itemLabel.setForeground(Color.black);
		dossierPanel.add(itemLabel, gbc);		
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;		
		JLabel appDescrLabel = new JLabel("Approval Status : ");
		dossierPanel.add(appDescrLabel, gbc);

		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		approvalLabel = new JLabel();
		approvalLabel.setForeground(Color.black);
		dossierPanel.add(approvalLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		changeButton = new JButton("Change BU to");
		changeButton.setEnabled(false);
		

		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;		
		JLabel nebDescrLabel = new JLabel("Not Effected : ");
		dossierPanel.add(nebDescrLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		noteffBookLabel = new JLabel();
		noteffBookLabel.setForeground(Color.black);
		dossierPanel.add(noteffBookLabel, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets2;		
		JLabel ebDescrLabel = new JLabel("Effected : ");
		dossierPanel.add(ebDescrLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets2;
		effBookLabel = new JLabel();
		effBookLabel.setForeground(Color.black);
		dossierPanel.add(effBookLabel, gbc);
		
		
		changeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						try {
							if (selectedLCDossier != null) {
								DOSSIERTable dt = new DOSSIERTable(
									serviceCenters.getSelectedServiceCenter().getConnection());
								selectedLCDossier.setBUSINESS_UNIT(ae.getActionCommand());
								
								if (dt.updateBU(selectedLCDossier) == 1) {
									
									buLabel.setText(ae.getActionCommand());
									if (ae.getActionCommand().equals("0011")) {
										changeButton.setText("Change BU to 0014");
										changeButton.setActionCommand("0014");
									} else {	
										changeButton.setText("Change BU to 0011");
										changeButton.setActionCommand("0011");
									}
									jlblStatus.setText("BU Code changed");
									
									//clearLabels();
									//selectedDossier = null;
									//changeButton.setEnabled(false);
								}
							} else if (selectedRCDossier != null) {
								RC_DOSSIERTable dt = new RC_DOSSIERTable(
									serviceCenters.getSelectedServiceCenter().getConnection());
								selectedRCDossier.setBUSINESS_UNIT(ae.getActionCommand());
								
								if (dt.updateBU(selectedRCDossier) == 1) {
									
									buLabel.setText(ae.getActionCommand());
									if (ae.getActionCommand().equals("0011")) {
										changeButton.setText("Change BU to 0014");
										changeButton.setActionCommand("0014");
									} else {	
										changeButton.setText("Change BU to 0011");
										changeButton.setActionCommand("0011");
									}
									jlblStatus.setText("BU Code changed");
								}
								
							}
						} catch (SQLException sqle) {
							System.err.println(sqle);
						}
					}
				});
		
		dossierPanel.add(changeButton, gbc);
		
		getContentPane().add(dossierPanel, BorderLayout.CENTER);

		// STATUS
		jlblStatus = new JLabel();

		jPanel = new JPanel();

		jPanel.setLayout(new BorderLayout());


		jlblStatus.setToolTipText("Status");
		jlblStatus.setBorder( new CompoundBorder(
                        new CompoundBorder(new EmptyBorder(new Insets(1, 1, 1, 1)),
                        new LineBorder(Color.gray)), new EmptyBorder(new Insets(0, 2, 0, 0))));
		jlblStatus.setText("Ready");
		jPanel.add(jlblStatus, "Center");

		
		getContentPane().add(jPanel, BorderLayout.SOUTH);
		
		
	}
	
	protected void doExit() {
		serviceCenters.cleanUp();
		super.doExit();
	}
	
	private void clearLabels() {
		changeButton.setEnabled(false);
		buLabel.setText("");
		dossierLabel.setText("");	
		itemLabel.setText("");
		approvalLabel.setText("");
		effBookLabel.setText("");
		noteffBookLabel.setText("");
		
	}
	
	
	private void workLCDossier() throws SQLException {
	
		DOSSIERTable dt = new DOSSIERTable(
			serviceCenters.getSelectedServiceCenter().getConnection());
	
		Iterator it = dt.select("DOSSIER_NUMBER = "+dossierField.getData().trim());
		if (it.hasNext()) {
			selectedLCDossier = (DOSSIER)it.next();
			dossierLabel.setText(selectedLCDossier.getBRANCH().trim()+" "+
									selectedLCDossier.getBRANCH_GROUP().trim()+"-"+
									Strings.rightJustify(String.valueOf(selectedLCDossier.getDOSSIER_NUMBER()), 7, '0'));
			String bu = selectedLCDossier.getBUSINESS_UNIT().trim();
			buLabel.setText(bu);
			
			LIABILITY_BOOKINGTable lbt = new LIABILITY_BOOKINGTable(serviceCenters.getSelectedServiceCenter().getConnection());
			Iterator bookIt = lbt.select("DOSSIER_OID = "+selectedLCDossier.getOID());
			int eff = 0;
			int noteff = 0;
			while(bookIt.hasNext()) {
				LIABILITY_BOOKING lb = (LIABILITY_BOOKING)bookIt.next();
				short is = lb.getIS_EFFECTED();
				if (is == 1)
					eff++;
				else 
					noteff++;										
			}
			
			if ((eff == 0) && (noteff == 0)) {
				effBookLabel.setText("no bookings");
				noteffBookLabel.setText("no bookings");
			} else {
				effBookLabel.setText(String.valueOf(eff));
				noteffBookLabel.setText(String.valueOf(noteff));										
			}
			
			DEF_DOSSIER_ITEMTable ddit = new DEF_DOSSIER_ITEMTable(serviceCenters.getSelectedServiceCenter().getConnection());
			String whereClause = "(ITEM_TYPE_CODE = 'II' OR ITEM_TYPE_CODE = 'EN') AND DOSSIER_OID = "+selectedLCDossier.getOID();
			Iterator dit = ddit.select(whereClause);
			
			if (dit.hasNext()) {
				DEF_DOSSIER_ITEM item = (DEF_DOSSIER_ITEM)dit.next();
				
				String itemCode = item.getITEM_TYPE_CODE();
				if (itemCode.equals("II"))											
					itemLabel.setText("import");
				else if (itemCode.equals("EN"))
					itemLabel.setText("export");
				else
					itemLabel.setText(itemCode);
				
				String aCode = item.getAPPROVAL_STATUS();
				if (aCode.equals("C"))
					approvalLabel.setText("control list printed");
				else if (aCode.equals("F"))
					approvalLabel.setText("fully approved");
				else if (aCode.equals("O"))
					approvalLabel.setText("open");
				else if (aCode.equals("P"))
					approvalLabel.setText("partially approved");
				else
					approvalLabel.setText(aCode);
														
			} else {
				itemLabel.setText("");
				approvalLabel.setText("");
			}
			
			
			if (bu.equals("0011")) {
				changeButton.setText("Change BU to 0014");
				changeButton.setActionCommand("0014");
			} else {	
				changeButton.setText("Change BU to 0011");
				changeButton.setActionCommand("0011");
			}
			
			changeButton.setEnabled(true);		
			jlblStatus.setText("Dossier found");
		} else {
			selectedLCDossier = null;
			jlblStatus.setText("Dossier not found");
			clearLabels();
		}
	}
	
	private void workRCDossier() throws SQLException {
	
		RC_DOSSIERTable dt = new RC_DOSSIERTable(
			serviceCenters.getSelectedServiceCenter().getConnection());
	
		Iterator it = dt.select("DOSSIER_NUMBER = "+dossierField.getData().trim());
		if (it.hasNext()) {
			selectedRCDossier = (RC_DOSSIER)it.next();
			dossierLabel.setText(selectedRCDossier.getBRANCH().trim()+" "+
									selectedRCDossier.getBRANCH_GROUP().trim()+"-"+
									Strings.rightJustify(String.valueOf(selectedRCDossier.getDOSSIER_NUMBER()), 7, '0'));
			String bu = selectedRCDossier.getBUSINESS_UNIT().trim();
			buLabel.setText(bu);
			
			RC_LIABILITY_BOOKTable lbt = new RC_LIABILITY_BOOKTable(serviceCenters.getSelectedServiceCenter().getConnection());
			Iterator bookIt = lbt.select("DOSSIER_OID = "+selectedRCDossier.getOID());
			int eff = 0;
			int noteff = 0;
			while(bookIt.hasNext()) {
				RC_LIABILITY_BOOK lb = (RC_LIABILITY_BOOK)bookIt.next();
				short is = lb.getIS_EFFECTED();
				if (is == 1)
					eff++;
				else 
					noteff++;										
			}
			
			if ((eff == 0) && (noteff == 0)) {
				effBookLabel.setText("no bookings");
				noteffBookLabel.setText("no bookings");
			} else {
				effBookLabel.setText(String.valueOf(eff));
				noteffBookLabel.setText(String.valueOf(noteff));										
			}
			
			RC_REIMBURS_AUTHTable ddit = new RC_REIMBURS_AUTHTable(serviceCenters.getSelectedServiceCenter().getConnection());
			String whereClause = "DOSSIER_OID = "+selectedRCDossier.getOID();
			Iterator dit = ddit.select(whereClause);
			
			if (dit.hasNext()) {
				RC_REIMBURS_AUTH item = (RC_REIMBURS_AUTH)dit.next();
				
				RC_AUDIT_RECORDTable rart = new RC_AUDIT_RECORDTable(serviceCenters.getSelectedServiceCenter().getConnection());
				Iterator auditit = rart.select("DOSSIER_ITEM_OID = "+item.getUOW_OID(), "RECORDING_TS DESC");
				
				if (auditit.hasNext()) {
					RC_AUDIT_RECORD rar = (RC_AUDIT_RECORD)auditit.next();
					
					itemLabel.setText(rar.getITEM_TYPE_CODE());
					
					approvalLabel.setText(rar.getROLE_CODE());
				} else {
					itemLabel.setText("");
					approvalLabel.setText("");					
				}
														
			} else {
				itemLabel.setText("");
				approvalLabel.setText("");
			}
			
			
			if (bu.equals("0011")) {
				changeButton.setText("Change BU to 0014");
				changeButton.setActionCommand("0014");
			} else {	
				changeButton.setText("Change BU to 0011");
				changeButton.setActionCommand("0011");
			}
			
			changeButton.setEnabled(true);		
			jlblStatus.setText("Dossier found");
		} else {
			selectedRCDossier = null;
			jlblStatus.setText("Dossier not found");
			clearLabels();
		}
	}
	
	
	public static void main(String[] args) {
		//PlafPanel.setNativeLookAndFeel(true);
		LoginDialog ldg = new LoginDialog(null, "Login to BUChanger", new GMBLoginValidator());
		ldg.setVisible(true);

		
		BUChanger tf = new BUChanger();
		
		
		tf.pack();
		//tf.setSize(450, 400);
		tf.setVisible(true);
	}

}