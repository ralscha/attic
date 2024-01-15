
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import common.swing.*;
import common.util.*;

public class GtfMBooker extends JFrame {
		
	private CLTableModel cltablemodel;
	private FLTableModel fltablemodel;
	
	public GtfMBooker() {
		super("GtfMBooker");
		addWindowListener(new Exiter());
		
		PlafPanel.setNativeLookAndFeel(true);
		//new PlafPanel(this);

		cltablemodel = new CLTableModel();
		fltablemodel = new FLTableModel();    

		final HistoryFrame historyFrame = new HistoryFrame(cltablemodel, fltablemodel);

		//Menu
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem historyMenuItem;
		JMenuItem exitMenuItem;
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		menuBar.add(fileMenu);
				
		historyMenuItem = new JMenuItem();
		historyMenuItem.setText("History");
		historyMenuItem.setActionCommand("History");
		fileMenu.add(historyMenuItem);
		
		fileMenu.add(new JSeparator());
		
		exitMenuItem = new JMenuItem();
		exitMenuItem.setText("Exit");
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.setMnemonic('x');
		fileMenu.add(exitMenuItem);
		
		setJMenuBar(menuBar);
		
		exitMenuItem.addActionListener(new ActionListener() {
		                               public void actionPerformed(ActionEvent event) {
		                                   exit();
		                               }});
				
		historyMenuItem.addActionListener(new ActionListener() {
		                               public void actionPerformed(ActionEvent event) {
		                                   historyFrame.showFrame();
		                               }});    
		
		
		JTabbedPane bookingTabbedPane = new JTabbedPane();
		
		JPanel cp = new JPanel();
		cp.setLayout(new FlowLayout(FlowLayout.LEFT));
		cp.add(new BookContingentPanel(cltablemodel));
		bookingTabbedPane.add("Contingent", cp);
		
		JPanel fp = new JPanel();
		fp.setLayout(new FlowLayout(FlowLayout.LEFT));
		fp.add(new BookFirmPanel(fltablemodel));

		bookingTabbedPane.add("Firm", fp);
		
		getContentPane().add(bookingTabbedPane);
	}
	
	
	private void exit() {
		DbManager.shutdown();
		System.exit(0);
	}
	
	private class Exiter extends WindowAdapter {
		public void windowClosing(WindowEvent windowevent) {
			exit();
		}
	}
	
	public static void main(String[] args) {
		DbManager.initialize(AppProperties.getStringProperty("db.name"), false);
		
		GtfMBooker gmb = new GtfMBooker();
		//gmb.setSize(400,200);
		gmb.pack();
		gmb.setVisible(true);
	}
	
}