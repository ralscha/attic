
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

public class TestFrame1 extends JExitFrame {

	private ServiceCenters serviceCenters;
	private IMask dossierField;
	private JButton searchButton;
	private JLabel buLabel;
	private JLabel dossierLabel;
	private JButton changeButton;
	private DOSSIER selectedDossier = null;
	
	private JPanel jPanel;
	private JLabel jlblStatus;
	
	public TestFrame1() {
		super("Gtf Dossier BU Changer");
		
		Font defaultFont = new Font("Verdana", Font.PLAIN, 20);	
		
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
		scLabel.setFont(defaultFont);	
		
		leftPanel.add(scLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		
		serviceCenters = new ServiceCenters();
		serviceCenters.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									clearLabels();
									selectedDossier = null;
									jlblStatus.setText("Ready");	
								}
							});
		JComboBox jcb = serviceCenters.getComboBox();
		jcb.setFont(defaultFont);
		jcb.requestFocus();
		leftPanel.add(jcb, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.weightx = 0;
		gbc.insets = insets0;
		JLabel dossierDescrLabel = new JLabel("Dossier No");
		dossierDescrLabel.setFont(defaultFont);
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
		
		
		dossierField.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            searchButton.doClick();
                        }
                    }});
		
		dossierField.setFont(defaultFont);
		leftPanel.add(dossierField, gbc);
		
		searchPanel.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		searchButton = new JButton("Search");
		searchButton.setEnabled(false);
		
		getRootPane().setDefaultButton(searchButton);
		
		searchButton.setFont(defaultFont);
				
		rightPanel.add(searchButton);
		searchPanel.add(rightPanel);
		
		getContentPane().add(searchPanel, BorderLayout.NORTH);

		searchButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {

							try {
								DOSSIERTable dt = new DOSSIERTable(
									serviceCenters.getSelectedServiceCenter().getConnection());
							
								Iterator it = dt.select("DOSSIER_NUMBER = "+dossierField.getData().trim());
								if (it.hasNext()) {
									selectedDossier = (DOSSIER)it.next();
									dossierLabel.setText(selectedDossier.getBRANCH().trim()+" "+
															selectedDossier.getBRANCH_GROUP().trim()+"-"+
															Strings.rightJustify(String.valueOf(selectedDossier.getDOSSIER_NUMBER()), 7, '0'));
									String bu = selectedDossier.getBUSINESS_UNIT().trim();
									buLabel.setText(bu);
									if (bu.equals("0011")) {
										changeButton.setText("Change BU to 0014");
										changeButton.setActionCommand("0014");
									} else {	
										changeButton.setText("Change BU to 0011");
										changeButton.setActionCommand("0011");
									}
									
									changeButton.setEnabled(true);		
									jlblStatus.setText("Ready");
								} else {
									selectedDossier = null;
									jlblStatus.setText("Dossier not found");
									clearLabels();
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

		gbc.anchor = gbc.WEST;
		gbc.fill = gbc.HORIZONTAL;
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets0;
		JLabel dossierDescr2Label = new JLabel("Dossier No :");
		dossierDescr2Label.setFont(defaultFont);
		dossierPanel.add(dossierDescr2Label, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		dossierLabel = new JLabel();
		dossierLabel.setForeground(Color.black);
		dossierLabel.setFont(defaultFont);
		dossierPanel.add(dossierLabel, gbc);
		
		gbc.gridwidth = gbc.RELATIVE;
		gbc.insets = insets0;		
		JLabel buDescrLabel = new JLabel("Business Unit : ");
		buDescrLabel.setFont(defaultFont);
		dossierPanel.add(buDescrLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets1;
		buLabel = new JLabel();
		buLabel.setForeground(Color.black);
		buLabel.setFont(defaultFont);
		dossierPanel.add(buLabel, gbc);
		
		gbc.gridwidth = gbc.REMAINDER;
		gbc.weightx = 1;
		gbc.insets = insets0;
		changeButton = new JButton("Change BU to");
		changeButton.setEnabled(false);
		changeButton.setFont(defaultFont);
		
		changeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						try {
							if (selectedDossier != null) {
								DOSSIERTable dt = new DOSSIERTable(
									serviceCenters.getSelectedServiceCenter().getConnection());
								selectedDossier.setBUSINESS_UNIT(ae.getActionCommand());
								if (dt.updateBU(selectedDossier) == 1) {
									clearLabels();
									selectedDossier = null;
									changeButton.setEnabled(false);
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
	

	private void clearLabels() {
		changeButton.setEnabled(false);
		buLabel.setText("");
		dossierLabel.setText("");	
	}
	
	public static void main(String[] args) {
		//PlafPanel.setNativeLookAndFeel(true);
		TestFrame1 tf = new TestFrame1();
		
		
		tf.pack();
		//	tf.setSize(200,200);
		tf.setVisible(true);
	}

}