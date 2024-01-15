import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.table.*;
import com.sun.java.swing.border.*;
import matthew.awt.StrutLayout;
import java.util.*;

public class GuestBookFrame extends JFrame {

	private JTextField nameTextField;
	private JButton addButton;	
	private JTextField messageTextField;
	private MyJTable entryTable;
    private EntryTableModel tableModel;
    private int messageTextLen, nameTextLen;

	public GuestBookFrame(boolean applet, String serverName) {
	    
	    super("GuestBook");
	    
	    messageTextLen = 0;
	    nameTextLen = 0;
	    
        setLAF(UIManager.getSystemLookAndFeelClassName());
	    	    
        //InputPanel
        JPanel inputPanel = new JPanel() {
                        public Insets getInsets() {
                            return (new Insets(5,5,5,5));
                        }};
                
        StrutLayout strutLayout2 = new StrutLayout ();
        inputPanel.setLayout(strutLayout2);
        
        JLabel nameLabel = new JLabel("Name");
        JLabel messageLabel = new JLabel("Message");
		nameTextField = new JTextField(20);

        nameTextField.getDocument().addDocumentListener(new NameTextListener());
		
        messageTextField = new JTextField(40);
        messageTextField.getDocument().addDocumentListener(new MessageTextListener());
        
        messageTextField.addKeyListener(new KeyAdapter() {
                                            public void keyPressed(KeyEvent e) {
                                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                                    addEntry();
                                                }
                                            }});
		
        addButton = new JButton("Add");		
		addButton.setActionCommand("Add");
        addButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent event) {
                                        addEntry();                                        
                                    }});
        addButton.setEnabled(false);
        
        inputPanel.add(nameLabel);

        inputPanel.add(nameTextField, new StrutLayout.StrutConstraint
               (nameLabel, StrutLayout.MID_RIGHT, StrutLayout.MID_LEFT,
                StrutLayout.EAST));

        inputPanel.add(messageTextField, new StrutLayout.StrutConstraint
                (nameTextField, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                 StrutLayout.SOUTH));

        inputPanel.add(messageLabel, new StrutLayout.StrutConstraint
                (messageTextField, StrutLayout.MID_LEFT, StrutLayout.MID_RIGHT,
                 StrutLayout.WEST));
        
        inputPanel.add(addButton, new StrutLayout.StrutConstraint
               (messageTextField, StrutLayout.MID_RIGHT, StrutLayout.MID_LEFT,
                StrutLayout.EAST));
        
        StrutLayout.SizeGroup sizeGroup = strutLayout2.createSizeGroup();
        sizeGroup.add(nameLabel, StrutLayout.SIZE_WIDTH);
        sizeGroup.add(messageLabel, StrutLayout.SIZE_WIDTH);        
  
        //Table        
        tableModel = new EntryTableModel(serverName);
   		entryTable = new MyJTable(tableModel);
		JScrollPane scrollpane = entryTable.createOwnScrollPane();

        for (int i = 0; i < tableModel.getColumnCount(); i++) {   
           TableColumn col = entryTable.getColumn(tableModel.getColumnName(i));            
           col.setPreferredWidth(tableModel.getWidth(i));
        }
        //entryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	         
        entryTable.setRowSelectionAllowed(false);
        // Main Panel
		JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
	
        StrutLayout strutLayout = new StrutLayout();
        panel.setLayout(strutLayout);

                
        panel.add(inputPanel);        
        panel.add(scrollpane, new StrutLayout.StrutConstraint
                (inputPanel, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                StrutLayout.SOUTH));

        strutLayout.setSprings(scrollpane, StrutLayout.SPRING_BOTH);   

		getContentPane().add(panel);

        if (applet) 
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        else    
      		addWindowListener(new Exit());				
      	      	
		pack();		
		setVisible(true);
        nameTextField.requestFocus();        
	}

/*
    public Dimension getPreferredSize() {
        return new Dimension(620, 300);
    }
*/
    void setLAF(String laf) {
        try {
            UIManager.setLookAndFeel(laf);
//          SwingUtilities.updateComponentTreeUI(this);            
        } catch(Exception ex) {
            ex.printStackTrace();
        }           
    }

    void addEntry() {
        if (addButton.isEnabled()) {
            tableModel.add(nameTextField.getText(), messageTextField.getText());
            messageTextField.setText("");        
            messageTextField.requestFocus();
        }        
    }




    class MessageTextListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            check();
        }
        
        public void insertUpdate(DocumentEvent e) {
            check();
        }
        
        public void removeUpdate(DocumentEvent e) {
            check();
        }
        
        private void check() {
            messageTextLen = messageTextField.getText().length();
            if ((nameTextLen > 0) && (messageTextLen > 0))
                addButton.setEnabled(true);
            else addButton.setEnabled(false);                
        }
    }


    class NameTextListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            check();
        }
        
        public void insertUpdate(DocumentEvent e) {
            check();
        }
        
        public void removeUpdate(DocumentEvent e) {
            check();
        }
        
        private void check() {

            nameTextLen = nameTextField.getText().length();
            if (nameTextLen == 0) addButton.setEnabled(false);
            else if (messageTextLen > 0) addButton.setEnabled(true);            
        }
    }

    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            Object object = event.getSource();
			if (object == GuestBookFrame.this) {
			    tableModel.removeListenerConnection();
                exit();
            }
        }
    }

	void exit() {
		System.exit(0); 
	}


}
