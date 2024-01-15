import com.sun.java.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;
import com.sun.java.swing.border.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.table.*;
import com.objectspace.voyager.*;
import COM.odi.util.*;

public class SWIFTInput extends JFrame {

    private final static String TODATESTR = "toDate";
    private final static String FROMDATESTR = "fromDate";
    
    private JTable swiftHeadersTable;
    private SWIFTHeaderModel headerModel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JTextArea swiftMessageArea;
	private JMenuItem exitMenuItem;
    private JScrollPane scrollpane, scrollpane2;
    private JComboBox fromDateCombo, toDateCombo, branchCombo;
    private JButton showButton;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");    
    private ISWIFTListServer isls = null;
    
    private Calendar from, to;
    
	public SWIFTInput(String sa) {
        super("SWIFTInput");   
        
   	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { }          

        
        try {
            isls = (ISWIFTListServer)Proxy.to(sa+":7500/SWIFTListServer");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        headerModel = new SWIFTHeaderModel(isls);
        
        from = isls.getFromDate();
        to   = isls.getToDate();
                
        headerModel.showHeaders(from, to);
        
  		swiftHeadersTable = new JTable(headerModel);
    		       			
        for (int i = 0; i < headerModel.getColumnCount(); i++) {            
            TableColumn col = swiftHeadersTable.getColumn(headerModel.getColumnName(i));            
            col.setWidth(headerModel.getWidth(i));
        }
        swiftHeadersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	        
        scrollpane = new JScrollPane(swiftHeadersTable);
		
		
		swiftHeadersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {		
    		public void valueChanged(ListSelectionEvent e) {
    		    showMessage(e);
    		}
    	});
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		menuBar.add(fileMenu);
		
		exitMenuItem = new JMenuItem();
		exitMenuItem.setText("Exit");
		exitMenuItem.setActionCommand("Exit");
		fileMenu.add(exitMenuItem);
		
		setJMenuBar(menuBar);
		exitMenuItem.setMnemonic('x');
        fileMenu.setMnemonic('F');		
        exitMenuItem.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            exit();
                                        }});

        
        Vector dates = new Vector();
        dates.addElement(dateFormat.format(from.getTime()));
        from.add(Calendar.DATE, +1);
        to.add(Calendar.DATE, +1);
        while (from.before(to)) {
            dates.addElement(dateFormat.format(from.getTime()));
            from.add(Calendar.DATE, +1);
        }

        from = isls.getFromDate();
        to   = isls.getToDate();

        fromDateCombo = new JComboBox(dates);
        fromDateCombo.setEditable(false);
        fromDateCombo.setSelectedIndex(0);
        fromDateCombo.setActionCommand(FROMDATESTR);
        fromDateCombo.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            updateDates(event);
                                        }});

        toDateCombo = new JComboBox(dates);
        toDateCombo.setEditable(false);
        toDateCombo.setSelectedIndex(dates.size()-1);
        toDateCombo.setActionCommand(TODATESTR);
        toDateCombo.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            updateDates(event);
                                        }});
        
        String[] branches = {"Zurich", "Zug", "All"};
        branchCombo = new JComboBox(branches);
        branchCombo.setEditable(false);
        branchCombo.setSelectedIndex(0);        
        branchCombo.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            System.out.println(event);
                                        }});
        
        
        showButton = new JButton("Show");
        showButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            showHeaders();
                                        }});
        
        JPanel p = new JPanel() ;

        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("from"));
        p.add(fromDateCombo);
        p.add(new JSeparator());
        p.add(new JLabel("to"));        
        p.add(toDateCombo);
        p.add(new JSeparator());
        p.add(new JLabel("branch"));        
        p.add(branchCombo);
        p.add(new JSeparator());
        p.add(showButton);
        
        
        //fromDateCombo.setFont(new Font("Courier", Font.PLAIN, 12));
        //toDateCombo.setFont(new Font("Courier", Font.PLAIN, 12));

        JPanel panel = new JPanel() ;/*{
                        public Insets getInsets() {
                            return (new Insets(5,5,5,5));
                        }};*/
        panel.setLayout(new BorderLayout());
        panel.add("North", p);
        panel.add("Center", scrollpane);


        JPanel messagePanel = new JPanel() ;/* {
                        public Insets getInsets() {
                            return (new Insets(5,5,5,5));
                        }};*/
        
        swiftMessageArea = new JTextArea();        
        swiftMessageArea.setFont(new Font("Courier", Font.PLAIN, 12));
        swiftMessageArea.setEditable(false);
        swiftMessageArea.setBackground(Color.lightGray);
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add("Center", swiftMessageArea);
        scrollpane2 = new JScrollPane(messagePanel);

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.add(panel);
        sp.add(scrollpane2);


        Dimension minimumSize = new Dimension(700, 200);
        panel.setMinimumSize(minimumSize);
        scrollpane2.setMinimumSize(minimumSize);
        sp.setPreferredSize(new Dimension(700, 450));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", sp);
        
		addWindowListener(new Exit());		
		
		pack();		
		setVisible(true);	
        sp.setDividerLocation(200);

    }

	static public void main(String args[]) {
	    if (args.length == 1)
    		new SWIFTInput(args[0]);
    	else
    	    System.out.println("SWIFTInput <server_address>");
	}

	void exit() {
		System.exit(0); 
	}

    void showMessage(ListSelectionEvent e) {
        String tosn = headerModel.getTOSN(e.getFirstIndex());
        if (tosn == null)
            swiftMessageArea.setText("");
        else {
            swiftMessageArea.setText("");
            SWIFTHeader sh = isls.getSWIFTHeader(tosn);
            if (sh != null) {
                if (sh.getMessage() != null) {
                    Iterator it = sh.getMessage().iterator();
                    while (it.hasNext())
                        swiftMessageArea.append(((SWIFTLines)it.next()).getString());
                }
            }
        }
    }
    
    void updateDates(ActionEvent event) {
        JComboBox cb = (JComboBox)event.getSource();
        
        try {
            String date = (String)cb.getSelectedItem();
            if (event.getActionCommand().equals(TODATESTR))
                to.setTime(dateFormat.parse(date));
            else if (event.getActionCommand().equals(FROMDATESTR))
                from.setTime(dateFormat.parse(date));
            System.out.println(date);        
                                                  
            if (to.before(from)) {
                Calendar help; 
                help = from; from = to; to = help;
            }
        
            System.out.println("from = "+dateFormat.format(from.getTime()));
            System.out.println("to   = "+dateFormat.format(to.getTime()));
        } catch (ParseException pe) {
            System.out.println(pe);
        }
            
    }
    
    
    void showHeaders() {
        headerModel.showHeaders(from, to);       
        
        for (int i = 0; i < headerModel.getColumnCount(); i++) {            
            TableColumn col = swiftHeadersTable.getColumn(headerModel.getColumnName(i));            
            col.setWidth(headerModel.getWidth(i));
        }
        
    }
    
    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            Object object = event.getSource();
			if (object == SWIFTInput.this)
                exit();
        }
    }
    
}
