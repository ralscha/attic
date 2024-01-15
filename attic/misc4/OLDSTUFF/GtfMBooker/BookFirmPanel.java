
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import common.swing.*;
import common.util.*;
import matthew.awt.*;
import com.ibm.mask.imask.*;
import com.ibm.mask.inumeric.*;
import com.ibm.mask.idate.*;
import ViolinStrings.*;
	
public class BookFirmPanel extends BookPanel {

	private FirmLiability firmLiability;
	private JButton effectButton;
	private JComboBox serviceList;
	private IMask dossierField;
	private IMask sequenceField;
	private JComboBox isoList;
	private INumeric amountField;
	private IMask valueField;
	private IMask debitAccountField;
	private IMask creditAccountField;
	private JTextField customerField;
	private JComboBox gtfList;
	private JComboBox buList;
	private JTextField itemField;
			
	private FLTableModel tableModel;		
			
	public BookFirmPanel(FLTableModel model) {
		super();

		tableModel = model;

		firmLiability = new FirmLiability();

    	StrutLayout strutLayout = new StrutLayout ();
		setLayout(strutLayout);    	

		JLabel scLabel = new JLabel("Service Center");

		add(scLabel);

		serviceList = new JComboBox(serviceCenters);
		String tmp = (String)serviceList.getSelectedItem();
		firmLiability.setGtf_proc_center(tmp.substring(0,4));
		
		serviceList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String serviceCenter = (String) cb.getSelectedItem();
                              				firmLiability.setGtf_proc_center(serviceCenter.substring(0,4));
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
													firmLiability.setGtf_number(data);
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
													firmLiability.setSequence_number(Integer.parseInt(data));
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
		
		//Amount
		JLabel amountLabel = new JLabel("Amount");
		isoList = new JComboBox(iso);
		
		tmp = (String)isoList.getSelectedItem();
		firmLiability.setCurrency(tmp);
		Currency cu = CurrencyMap.getCurrency(tmp);
		if (cu != null) 
			firmLiability.setExchange_rate(cu.getInternalRate().doubleValue());

		
		isoList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String iso = (String) cb.getSelectedItem();
                              				firmLiability.setCurrency(iso);
                              				Currency curr = CurrencyMap.getCurrency(iso);
                              				if (curr != null) 
                              					firmLiability.setExchange_rate(curr.getInternalRate().doubleValue());
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
												firmLiability.setAmount(im.getValue());
												checkEffectButton();
											}
											}); 

		add(isoList, new StrutLayout.StrutConstraint(dossierField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(amountLabel, new StrutLayout.StrutConstraint(isoList, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
 
		add(amountField, new StrutLayout.StrutConstraint(isoList, StrutLayout.MID_RIGHT,
								StrutLayout.MID_LEFT,StrutLayout.EAST));
								
		//Value Date 
		JLabel valueLabel = new JLabel("Value Date");
		
		/*IDate valueField = new IDate();
		valueField.setColumns(8);
		valueField.setMask("ddmmyyyy"); 
		valueField.setSeparator("."); 
		*/
		valueField = new IMask("##.##.####", 7);
		
		valueField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												firmLiability.setValue_date(im.getData());
												checkEffectButton();
											}
											}); 
											
		add(valueField, new StrutLayout.StrutConstraint(isoList, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(valueLabel, new StrutLayout.StrutConstraint(valueField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
								
		//Account Number
		JLabel debitAccountLabel = new JLabel("Debit Account No");
		debitAccountField = new IMask("####-#######-##-###", 12);		
		
		add(debitAccountField, new StrutLayout.StrutConstraint(valueField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(debitAccountLabel, new StrutLayout.StrutConstraint(debitAccountField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		JLabel creditAccountLabel = new JLabel("Credit Account No");
		creditAccountField = new IMask("####-#######-##-###", 12);		
		
		add(creditAccountField, new StrutLayout.StrutConstraint(debitAccountField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(creditAccountLabel, new StrutLayout.StrutConstraint(creditAccountField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		debitAccountField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												firmLiability.setDeb_acct_number(im.getData());
												checkEffectButton();
											}
											}); 
											
		creditAccountField.addFocusListener(new FocusAdapter() {
											public void focusLost(FocusEvent e) {
												IMask im = (IMask)e.getSource();
												firmLiability.setCre_acct_number(im.getData());
												checkEffectButton();
											}
											}); 											
		
		// BUCode
		JLabel buLabel = new JLabel("BU");
		buList = new JComboBox(bu);
		
		tmp = (String)buList.getSelectedItem();
		firmLiability.setBu_code(tmp);
		
		buList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String bu = (String) cb.getSelectedItem();
                              				firmLiability.setBu_code(bu);
                              				checkEffectButton();
                              			}
                              		}
                             		);
		
		
		Component sep3 = Box.createHorizontalStrut(5);
		
		add(sep3, new StrutLayout.StrutConstraint(debitAccountField, StrutLayout.MID_RIGHT, 
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
												firmLiability.setCustomer_ref(tf.getText());
												checkEffectButton();
											}
											}); 		
		
		add(customerField, new StrutLayout.StrutConstraint(creditAccountField, StrutLayout.BOTTOM_LEFT, 
								StrutLayout.TOP_LEFT,StrutLayout.SOUTH));	
								
		add(customerLabel, new StrutLayout.StrutConstraint(customerField, StrutLayout.MID_LEFT,
								StrutLayout.MID_RIGHT,StrutLayout.WEST));
		
		//GtfType
		JLabel gtfLabel = new JLabel("Gtf Type");
		gtfList = new JComboBox(gtfType);
		
		tmp = (String)gtfList.getSelectedItem();
		firmLiability.setGtf_type(tmp);
		
		gtfList.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent e) {
                              				JComboBox cb = (JComboBox) e.getSource();
                              				String gtftype = (String) cb.getSelectedItem();
                              				String product = ProductMap.getProductCode(gtftype);
                              				firmLiability.setGtf_type(product);
                              				
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
												firmLiability.setDossier_item(tf.getText());
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
                              				//tableModel.addLiability(firmLiability);
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
			pw.print(firmLiability.getBookString());
			pw.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	private void clear() {
		try {
			firmLiability  = new FirmLiability();			
			String tmp = (String)serviceList.getSelectedItem();
			firmLiability.setGtf_proc_center(tmp.substring(0,4));
	
			dossierField.setData("");
			sequenceField.setData("");
			
			tmp = (String)isoList.getSelectedItem();
			firmLiability.setCurrency(tmp);
			Currency curr = CurrencyMap.getCurrency(tmp);
			if (curr != null) 
				firmLiability.setExchange_rate(curr.getInternalRate().doubleValue());
			
			amountField.setValue(0.0);
			valueField.setData("");
			debitAccountField.setData("");
			creditAccountField.setData("");		
			
			tmp = (String)buList.getSelectedItem();
			firmLiability.setBu_code(tmp);	
			
			customerField.setText("");
			
			tmp = (String)gtfList.getSelectedItem();
			String product = ProductMap.getProductCode(tmp);
          firmLiability.setGtf_type(product);
			
			
			itemField.setText("");
		} catch (IMaskInvalidInputException iiie) {
			System.err.println(iiie);
		}
	}
	
	private void checkEffectButton() {
		if (firmLiability.ok()) 
			effectButton.setEnabled(true);
		else
			effectButton.setEnabled(false);
			
	}
	
	/*
	public static void main(String[] args) {
		JExitFrame jef = new JExitFrame("BookFirmPanel");
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5,5,5,5));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new BookFirmPanel());
		jef.getContentPane().add(panel);
		jef.pack();
		jef.setVisible(true);
	}
	*/
}