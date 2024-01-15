package gtf.mbooker;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;

import common.swing.*;
import common.util.*;


public class GtfMBooker extends JExitFrame {

	/*
	private CLTableModel cltablemodel;
	private FLTableModel fltablemodel;
	*/
	
	public GtfMBooker() {
		super("GtfMBooker");
		
		PlafPanel.setNativeLookAndFeel(true);

		/*
		cltablemodel = new CLTableModel();
		fltablemodel = new FLTableModel();    
		*/
		/*
		final HistoryFrame historyFrame = new HistoryFrame(cltablemodel, fltablemodel);
		*/
		//Menu
		JMenuBar menuBar;
		JMenu fileMenu;
		//JMenuItem historyMenuItem;
		JMenuItem exitMenuItem;
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		menuBar.add(fileMenu);

		/*
		historyMenuItem = new JMenuItem();
		historyMenuItem.setText("History");
		historyMenuItem.setActionCommand("History");
		fileMenu.add(historyMenuItem);
		
		fileMenu.add(new JSeparator());
		*/			
		exitMenuItem = new JMenuItem();
		exitMenuItem.setText("Exit");
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.setMnemonic('x');
		fileMenu.add(exitMenuItem);
		
		setJMenuBar(menuBar);
		/*
		historyMenuItem.addActionListener(new ActionListener() {
		                               public void actionPerformed(ActionEvent event) {
		                                   historyFrame.setVisible(true);
		                               }});    
		*/
		exitMenuItem.addActionListener(new ActionListener() {
		                               public void actionPerformed(ActionEvent event) {
		                                   doExit();
		                               }});
		
	
		                               
		JTabbedPane bookingTabbedPane = new JTabbedPane();
		
		JPanel cp = new JPanel();
		cp.setLayout(new FlowLayout(FlowLayout.LEFT));
		cp.add(new BookContingentPanel());

		bookingTabbedPane.add("Contingent", cp);
		
		JPanel fp = new JPanel();
		fp.setLayout(new FlowLayout(FlowLayout.LEFT));
		fp.add(new BookFirmPanel());

		bookingTabbedPane.add("Firm", fp);
		
		getContentPane().add(bookingTabbedPane);
	}
	
	protected void doExit() {
		super.doExit();
	}
		
	public static void main(String[] args) {
				
		//wenn wir hier sind hat das login geklappt
		//gtf.crapa.CrapaDbManager.openDb();
		
		GtfMBooker gmb = new GtfMBooker();
		gmb.pack();
		gmb.setVisible(true);
	}

}