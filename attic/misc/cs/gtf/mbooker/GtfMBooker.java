package gtf.mbooker;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.SQLException;
import java.io.*;

import common.swing.*;
import common.util.*;


public class GtfMBooker extends JFrame {

	
	public GtfMBooker() {
		super("GtfMBooker");
		
		PlafPanel.setNativeLookAndFeel(true);
		setDefaultCloseOperation(3);
		
		
		//Menu
		JMenuBar menuBar;
		JMenu fileMenu;
		JMenuItem exitMenuItem;
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		menuBar.add(fileMenu);

		exitMenuItem = new JMenuItem();
		exitMenuItem.setText("Exit");
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.setMnemonic('x');
		fileMenu.add(exitMenuItem);
		
		setJMenuBar(menuBar);
		exitMenuItem.addActionListener(new ActionListener() {
		                               public void actionPerformed(ActionEvent event) {
		                                   System.exit(0);
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
	
		
	public static void main(String[] args) {
						
		GtfMBooker gmb = new GtfMBooker();
		gmb.pack();
		gmb.setVisible(true);
		
		File bookFile = new File("BookManual.txt");
		if (bookFile.exists() && (bookFile.length() > 0)) {
           int n = JOptionPane.showOptionDialog(gmb,
               "Bookings file is not empty. \nDelete contents? ",
               	"Question",
               JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);

           if (n == JOptionPane.OK_OPTION) {
           	bookFile.delete();
           }
		}
		
	}

}