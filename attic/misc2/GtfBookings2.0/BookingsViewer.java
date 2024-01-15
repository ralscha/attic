
import com.sun.java.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.datatransfer.*;
import com.sun.java.swing.border.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.table.*;
import matthew.awt.StrutLayout;


public class BookingsViewer extends JFrame implements ClipboardOwner, Runnable
{

    static final String DB_NAME = "Liability.odb";

    Clipboard clipboard = getToolkit().getSystemClipboard();
       
    private JTable bookingTable;
    
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
//	private JMenuItem updateMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem copyAllMenuItem;

    final static String CONT_STR = "cont";
    final static String FIRM_STR = "firm";
    
    boolean showOnlyErrors = false;
    boolean showContingentLiability = true;
    String branch = "all";
    JScrollPane scrollpane;
    
    JTextField dossierField = null;
    CLTableModel cltablemodel;
    FLTableModel fltablemodel;

    Hashtable ht = new Hashtable();    

	public BookingsViewer(String title) {
        super(title);
    
        DbManager.initialize(DB_NAME, false);
           
        ht.put("Zurich", "0835");
        ht.put("Zug", "0823");
        ht.put("All", "all");
        
   	    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { }          


        cltablemodel = new CLTableModel();
        fltablemodel = new FLTableModel();        

        bookingTable = new JTable();
        /*
        if (showContingentLiability) {
    		bookingTable = new JTable(cltablemodel);
    		       			
            for (int i = 0; i < cltablemodel.getColumnCount(); i++) {            
                TableColumn col = bookingTable.getColumn(cltablemodel.getColumnName(i));            
                col.setWidth(cltablemodel.getWidth(i));
             }
        } else {
       		bookingTable = new JTable(fltablemodel);
            for (int i = 0; i < fltablemodel.getColumnCount(); i++) {            
                TableColumn col = bookingTable.getColumn(fltablemodel.getColumnName(i));            
                col.setWidth(fltablemodel.getWidth(i));
            }            
        }*/
        bookingTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	        
     //   bookingTable.setDoubleBuffered(true);
        scrollpane = new JScrollPane(bookingTable);
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		menuBar.add(fileMenu);
		
		editMenu = new JMenu();
		editMenu.setText("Edit");
		editMenu.setActionCommand("Edit");
		menuBar.add(editMenu);		
		
//		updateMenuItem = new JMenuItem("Update");
//		fileMenu.add(updateMenuItem);
//		fileMenu.add(new JSeparator());
		
		exitMenuItem = new JMenuItem();
		exitMenuItem.setText("Exit");
		exitMenuItem.setActionCommand("Exit");
		fileMenu.add(exitMenuItem);
		
		copyAllMenuItem = new JMenuItem();
		copyAllMenuItem.setText("Copy All");
		copyAllMenuItem.setActionCommand("Copy All");
		editMenu.add(copyAllMenuItem);
		
		setJMenuBar(menuBar);
//		updateMenuItem.setMnemonic('U');
		exitMenuItem.setMnemonic('x');
		copyAllMenuItem.setMnemonic('A');
		editMenu.setMnemonic('E');
        fileMenu.setMnemonic('F');		
        exitMenuItem.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            exit();
                                        }});
        copyAllMenuItem.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            copyAll();
                                        }});

/*        updateMenuItem.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent event) {
                                            update();
                                        }});
*/
        JPanel panel = new JPanel() {
                        public Insets getInsets() {
                            return (new Insets(5,5,5,5));
                        }};
        panel.setPreferredSize(new Dimension(800,540));
        panel.setMinimumSize(new Dimension(800,540));       
        
        StrutLayout strutLayout = new StrutLayout ();
        panel.setLayout(strutLayout);
            
        //Dossier Panel
        JPanel dossierPanel = new JPanel();
        StrutLayout strutLayout2 = new StrutLayout();

        JLabel dossierLabel = new JLabel("Number:");
        dossierField = new JTextField(6);
        JLabel branchLabel = new JLabel("Branch:");
        JComboBox branchCombo = new JComboBox();
                
        Enumeration e = ht.keys();
        while (e.hasMoreElements())
            branchCombo.addItem((String)e.nextElement());            
            
        branchCombo.setSelectedItem("All");
        branchCombo.setMaximumRowCount(4);
        branchCombo.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            branch = (String)ht.get(((JComboBox)e.getSource()).getSelectedItem());                                            
                                        }});
                        
        dossierPanel.setLayout(strutLayout2);
        dossierPanel.setBorder(new TitledBorder("Dossier"));
        
        dossierPanel.add(dossierLabel);
        
        dossierPanel.add(dossierField, new StrutLayout.StrutConstraint
               (dossierLabel, StrutLayout.MID_RIGHT, StrutLayout.MID_LEFT,
                StrutLayout.EAST));
                
        dossierPanel.add(branchCombo, new StrutLayout.StrutConstraint
                (dossierField, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                 StrutLayout.SOUTH));
        
        dossierPanel.add(branchLabel, new StrutLayout.StrutConstraint
                (branchCombo, StrutLayout.MID_LEFT, StrutLayout.MID_RIGHT,
                StrutLayout.WEST));
        
        StrutLayout.SizeGroup sizeGroup1 = strutLayout2.createSizeGroup();
        sizeGroup1.add(dossierField, StrutLayout.SIZE_WIDTH);
        sizeGroup1.add(branchCombo, StrutLayout.SIZE_WIDTH);
        
                                
        //RadioButton Panel
        JPanel radioPanel = new JPanel();
        
        ButtonGroup rbg = new ButtonGroup();
        JRadioButton contRB = new JRadioButton("contingent liability");
        JRadioButton firmRB = new JRadioButton("firm liability");
        rbg.add(contRB);
        rbg.add(firmRB);
        contRB.setActionCommand(CONT_STR);
        firmRB.setActionCommand(FIRM_STR);
        
        RadioActionListener ral = new RadioActionListener();
        contRB.addActionListener(ral);
        firmRB.addActionListener(ral);
        
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(contRB);
        radioPanel.add(firmRB);
        
        if (showContingentLiability)
            contRB.setSelected(true);
        else
            firmRB.setSelected(true);
            
        radioPanel.setBorder(new TitledBorder("Booking type"));
        
        //Button Panel
        JPanel buttonPanel = new JPanel();
        JCheckBox errorsCheck = new JCheckBox("only errors", true);
        errorsCheck.setSelected(showOnlyErrors);
        
        JButton searchButton = new JButton("Show");
        searchButton.setMnemonic('S');
        buttonPanel.setLayout(new GridLayout(2,1,0,10));        
        buttonPanel.add(errorsCheck);        
        buttonPanel.add(searchButton);
        
        searchButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            search();
                                        }});
        
        errorsCheck.addItemListener(new ItemListener() {
                                        public void itemStateChanged(ItemEvent e) {
                                            if (e.getStateChange() == ItemEvent.SELECTED)
                                                showOnlyErrors = true;
                                            else
                                                showOnlyErrors = false;                                            
                                        }});
        
        //Control Panel        
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(dossierPanel);
        controlPanel.add(radioPanel);
        controlPanel.add(buttonPanel);

        //Main Panel            
        panel.add(controlPanel);        
        panel.add(scrollpane, new StrutLayout.StrutConstraint
                (controlPanel, StrutLayout.BOTTOM_LEFT, StrutLayout.TOP_LEFT,
                StrutLayout.SOUTH));
  
        strutLayout.setSprings(scrollpane, StrutLayout.SPRING_BOTH);        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", panel);
        
		addWindowListener(new Exit());		
		
		pack();		
		setVisible(true);
	
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
       System.out.println("Clipboard contents replaced");
    }

	static public void main(String args[]) {
		new BookingsViewer("GTF BookingsViewer");
	}

    public void run() {
        MsgDialog md = new MsgDialog(this, "information", "please wait...");
        md.setVisible(true);
        
  
        if (showContingentLiability) {
            cltablemodel.searchDossier(dossierField.getText(), branch, showOnlyErrors);
            
            TableSorter sorter = new TableSorter(cltablemodel);
            bookingTable.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(bookingTable);
            
            for (int i = 0; i < cltablemodel.getColumnCount(); i++) {            
                TableColumn col = bookingTable.getColumn(cltablemodel.getColumnName(i));            
                DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
                dtcr.setHorizontalAlignment(cltablemodel.getAlignment(i));                
                col.setCellRenderer(dtcr);   
                col.setWidth(cltablemodel.getWidth(i));
            }
            sorter.sortByColumn(2) ;
  
        } else {
            fltablemodel.searchDossier(dossierField.getText(), branch, showOnlyErrors);
            
            TableSorter sorter = new TableSorter(fltablemodel);
            bookingTable.setModel(sorter);
            sorter.addMouseListenerToHeaderInTable(bookingTable);
            
            for (int i = 0; i < fltablemodel.getColumnCount(); i++) {            
                TableColumn col = bookingTable.getColumn(fltablemodel.getColumnName(i));            
                DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
                dtcr.setHorizontalAlignment(fltablemodel.getAlignment(i));                
                col.setCellRenderer(dtcr);   
                col.setWidth(fltablemodel.getWidth(i));
            }
            sorter.sortByColumn(1);
  
        }            
        md.setVisible(false);

    }
/*
    public void update() {
        UpdateDialog ud = new UpdateDialog(this);                        
    }
 */   

    public void search() {     
        new Thread(this).start();
    }    
    

    void copyAll() {            
        StringSelection st;        

        if (showContingentLiability)        
            st = new StringSelection(cltablemodel.getTransferString());
        else
            st = new StringSelection(fltablemodel.getTransferString());
            
        clipboard.setContents(st, this);
    }
    

	void exit() {
        DbManager.shutdown();
		System.exit(0); 
	}
    
    class Exit extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            Object object = event.getSource();
			if (object == BookingsViewer.this)
                exit();
        }
    }
    
    class RadioActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(CONT_STR))
                showContingentLiability = true;
            else    
                showContingentLiability = false;
        }
    }
		
}
