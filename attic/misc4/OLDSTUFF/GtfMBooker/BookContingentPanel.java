
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import common.swing.*;
import matthew.awt.*;
import com.ibm.mask.imask.*;
import com.ibm.mask.inumeric.*;
import com.ibm.mask.idate.*;
import ViolinStrings.*;

public class BookContingentPanel extends BookPanel {

	private ContingentLiability contLiability;
	private JButton effectButton;
	private JComboBox serviceList;
	private IMask dossierField;
	private IMask sequenceField;
	private JComboBox isoList;
	private INumeric amountField;
	private IMask expiryField;
	private IMask accountField;
	private JTextField customerField;
	private JComboBox gtfList;
	private JComboBox buList;
	private JTextField itemField;
	private JRadioButton creditButton;
	private JRadioButton debitButton;

	private CLTableModel tableModel;

	public BookContingentPanel(CLTableModel model) {
		super();

		tableModel = model;

		contLiability = new ContingentLiability();

    	StrutLayout strutLayout = new StrutLayout ();
		setLayout(strutLayout);    	

		JLabel scLabel = new JLabel("Service Center");

		add(scLabel);

		serviceList = new JComboBox(serviceCenters);
		String tmp = (String)serviceList.getSelectedItem();
		contLiability.setGtf_proc_center(tmp.substring(0,4));
		
		serviceList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String serviceCenter = (String) cb.getSelectedItem();
                              				contLiability.setGtf_proc_center(serviceCenter.substring(0,4));
                              				checkEffectButton();
                              			}
                              		}
                             		);
		add(serviceList, new StrutLayout.StrutConstraint(scLabel, StrutLayout.MID_RIGHT, 
								StrutLayout.MID_LEFT,StrutLayout.EAST));	
	
	
		//Dossier No
		dossierField = new IMask("#######", 5);
		
		dossierField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												String data = im.getData().trim();
												if (!data.equals("")) {
													data = Strings.rightJustify(data, 7, '0');
													try {
														dossierField.setData(data);
													} catch (IMaskInvalidInputException iiie) {
														System.err.println(iiie);
													}
													contLiability.setGtf_number(data);
													checkEffectButton();
												}
											}
											}); 
		
		JLabel dossierLabel = new JLabel("Dossier No.", JLabel.LEFT);
		add(dossierField, new StrutLayout.StrutConstraint(serviceList, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(dossierLabel, new StrutLayout.StrutConstraint(dossierField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));

		
		//Sequnce No
		sequenceField = new IMask("######", 5);
		sequenceField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();	
												String data = im.getData().trim();						
												if (!data.equals(""))					
													contLiability.setSequence_number(Integer.parseInt(data));
												checkEffectButton();
											}
											}); 
		
		JLabel sequenceLabel = new JLabel("Sequence No.", JLabel.LEFT);
		
		Component sep = Box.createHorizontalStrut(5);
		add(sep, new StrutLayout.StrutConstraint(dossierField, StrutLayout.MID_RIGHT, 
								StrutLayout.MID_LEFT,StrutLayout.EAST));
		add(sequenceLabel, new StrutLayout.StrutConstraint(sep, StrutLayout.MID_RIGHT, 
								StrutLayout.MID_LEFT,StrutLayout.EAST));
		add(sequenceField, new StrutLayout.StrutConstraint(sequenceLabel, StrutLayout.MID_RIGHT,
								StrutLayout.MID_LEFT,StrutLayout.EAST));							
		
		//Bookings type 
		final String debit = "DEBIT";
		final String credit = "CREDIT";
		debitButton = new JRadioButton(debit);
       debitButton.setMnemonic('d');
		debitButton.setActionCommand(debit);
		creditButton = new JRadioButton(credit);
       creditButton.setMnemonic('c');
       creditButton.setActionCommand(credit);
    
       ButtonGroup group = new ButtonGroup();
       group.add(debitButton);
       group.add(creditButton);
       
		JPanel radioPanel = new JPanel();
		//radioPanel.setBorder(new LineBorder(Color.black));
		radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		radioPanel.add(debitButton);
		radioPanel.add(creditButton);
		
		add(radioPanel, new StrutLayout.StrutConstraint(dossierField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		
		ActionListener radioListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
								JRadioButton rb = (JRadioButton)e.getSource();
								String command = rb.getActionCommand();
								if (debit.equals(command)) {
									contLiability.setType("D");
									checkEffectButton();
								} else if (credit.equals(command)) {									
									contLiability.setType("C");
									checkEffectButton();									
								}
						}};
						
		debitButton.addActionListener(radioListener);
		creditButton.addActionListener(radioListener);
		
		debitButton.setSelected(true);
		contLiability.setType("D");
		
		//Amount
		JLabel amountLabel = new JLabel("Amount");
		isoList = new JComboBox(iso);

		tmp = (String)isoList.getSelectedItem();
		contLiability.setCurrency(tmp);
		Currency cu = CurrencyMap.getCurrency(tmp);
		if (cu != null) 
			contLiability.setExchange_rate(cu.getInternalRate().doubleValue());

		
		isoList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String iso = (String) cb.getSelectedItem();
                              				contLiability.setCurrency(iso);
                              				Currency curr = CurrencyMap.getCurrency(iso);
                              				if (curr != null) 
                              					contLiability.setExchange_rate(curr.getInternalRate().doubleValue());
                              				checkEffectButton();
                              			}
                              		}
                             		);


		
		amountField = new INumeric(15);
  		amountField.setMinValue( 0.0);
 		amountField.setDigitsBeforeDP(12);
 		amountField.setDigitsAfterDP(3);

 		amountField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												INumeric im = (INumeric)e.getSource();
												contLiability.setAmount(im.getValue());
												checkEffectButton();
											}
											}); 

		add(isoList, new StrutLayout.StrutConstraint(radioPanel, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(amountLabel, new StrutLayout.StrutConstraint(isoList, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
 
		add(amountField, new StrutLayout.StrutConstraint(isoList, StrutLayout.MID_RIGHT,
								StrutLayout.MID_LEFT,StrutLayout.EAST));
								
		//Expiry Date 
		JLabel expiryLabel = new JLabel("Expiry Date");
		
		/*IDate expiryField = new IDate();
		expiryField.setColumns(8);
		expiryField.setMask("ddmmyyyy"); 
		expiryField.setSeparator("."); 
		*/
		expiryField = new IMask("##.##.####", 7);
		
		expiryField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												contLiability.setExpiry_date(im.getData());
												checkEffectButton();
											}
											}); 
		
		add(expiryField, new StrutLayout.StrutConstraint(isoList, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(expiryLabel, new StrutLayout.StrutConstraint(expiryField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
								
		//Account Number
		JLabel accountLabel = new JLabel("Account No");
		accountField = new IMask("####-#######-##-###", 12);		
		
		accountField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												contLiability.setAccount_number(im.getData());
												checkEffectButton();
											}
											}); 
		
		add(accountField, new StrutLayout.StrutConstraint(expiryField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(accountLabel, new StrutLayout.StrutConstraint(accountField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		// BUCode
		JLabel buLabel = new JLabel("BU");
		buList = new JComboBox(bu);
		
		tmp = (String)buList.getSelectedItem();
		contLiability.setBu_code(tmp);
		
		buList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String bu = (String) cb.getSelectedItem();
                              				contLiability.setBu_code(bu);
                              				checkEffectButton();
                              			}
                              		}
                             		);

		
		Component sep3 = Box.createHorizontalStrut(5);
		
		add(sep3, new StrutLayout.StrutConstraint(accountField, StrutLayout.MID_RIGHT, 
								StrutLayout.MID_LEFT,StrutLayout.EAST));
		add(buLabel, new StrutLayout.StrutConstraint(sep3, StrutLayout.MID_RIGHT, 
								StrutLayout.MID_LEFT,StrutLayout.EAST));
		add(buList, new StrutLayout.StrutConstraint(buLabel, StrutLayout.MID_RIGHT,
								StrutLayout.MID_LEFT,StrutLayout.EAST));


		//Customer Ref
		JLabel customerLabel = new JLabel("Customer Ref");
		customerField = new JTextField(20);
		
		customerField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												JTextField tf = (JTextField)e.getSource();
												contLiability.setCustomer_ref(tf.getText());
												checkEffectButton();
											}
											}); 		
		
		add(customerField, new StrutLayout.StrutConstraint(accountField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(customerLabel, new StrutLayout.StrutConstraint(customerField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		//GtfType
		JLabel gtfLabel = new JLabel("Gtf Type");
		gtfList = new JComboBox(gtfType);
		
		tmp = (String)gtfList.getSelectedItem();
		contLiability.setGtf_type(tmp);
		
		gtfList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String gtftype = (String) cb.getSelectedItem();
                              				String product = ProductMap.getProductCode(gtftype);
                              				contLiability.setGtf_type(product);
                              				
                              				checkEffectButton();
                              			}
                              		}
                             		);
		

		
		add(gtfList, new StrutLayout.StrutConstraint(customerField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(gtfLabel, new StrutLayout.StrutConstraint(gtfList, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		
		//DossierItem
		JLabel itemLabel = new JLabel("Dossier Item");
		itemField = new JTextField(15);
		
		itemField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												JTextField tf = (JTextField)e.getSource();
												contLiability.setDossier_item(tf.getText());
												checkEffectButton();
											}
											}); 
		
		add(itemField, new StrutLayout.StrutConstraint(gtfList, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(itemLabel, new StrutLayout.StrutConstraint(itemField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));

	
		Component vertStrut = Box.createVerticalStrut(10);
		
		effectButton = new JButton("Effect Booking");
		effectButton.setEnabled(false);
		effectButton.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				toFile();
                              				//tableModel.addLiability(contLiability);
                              				clear();
                              				serviceList.requestFocus();
                              				effectButton.setEnabled(false);
                              			}
                              		}
                             		);	
	
	
		JLabel statusLabel = new JLabel("Ready                                               ");

		add(vertStrut, new StrutLayout.StrutConstraint(itemField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(effectButton, new StrutLayout.StrutConstraint(vertStrut, StrutLayout.BOTTOM_LEFT,
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));
		add(statusLabel, new StrutLayout.StrutConstraint(effectButton, StrutLayout.BOTTOM_LEFT,
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));
		
	}
	
	private void toFile() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("BookManual.txt", true));
			pw.print(contLiability.getBookString());
			pw.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	private void clear() {
		try {
			contLiability = new ContingentLiability();
			
			String tmp = (String)serviceList.getSelectedItem();
			contLiability.setGtf_proc_center(tmp.substring(0,4));
	
			dossierField.setData("");
			sequenceField.setData("");
			
			tmp = (String)isoList.getSelectedItem();
			contLiability.setCurrency(tmp);
			Currency curr = CurrencyMap.getCurrency(tmp);
			if (curr != null) 
				contLiability.setExchange_rate(curr.getInternalRate().doubleValue());
			
			amountField.setValue(0.0);
			expiryField.setData("");
			accountField.setData("");
			
			tmp = (String)buList.getSelectedItem();
			contLiability.setBu_code(tmp);	
			
			customerField.setText("");
			
			tmp = (String)gtfList.getSelectedItem();
			
			String product = ProductMap.getProductCode(tmp);
          contLiability.setGtf_type(product);
			
			
			itemField.setText("");
			
			debitButton.setSelected(true);
			contLiability.setType("D");
			
		} catch (IMaskInvalidInputException iiie) {
			System.err.println(iiie);
		}
	}
	
	private void checkEffectButton() {
		if (contLiability.ok()) 
			effectButton.setEnabled(true);
		else 
			effectButton.setEnabled(false);
	}
	
	/*
	public static void main(String[] args) {
		JExitFrame jef = new JExitFrame("BookContingentPanel");
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5,5,5,5));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new BookContingentPanel(null));
		jef.getContentPane().add(panel);
		jef.pack();
		jef.setVisible(true);
	}
	*/
}