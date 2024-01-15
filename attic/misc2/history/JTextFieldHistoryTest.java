

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;
import java.util.*;

public class JTextFieldHistoryTest extends JFrame {

	private JTextFieldHistoryTest check1, check2, check3;

	
	public JTextFieldHistoryTest() {
		super("TextField History Test");
		
		/*
		new PlafPanel(this);
		PlafPanel.setNativeLookAndFeel(true);
		*/
		setDefaultCloseOperation(3);
	
		History.setHistoryTable(new Hashtable());

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(new JTextFieldHistory(new HistoryStringReference("TEST"), 10, 50));
		
		pack();
		setVisible(true);
	}

	

	
	
	public static void main(String[] args) {
		new JTextFieldHistoryTest();
	}

}