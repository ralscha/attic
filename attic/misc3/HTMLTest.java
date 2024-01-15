

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;
import java.io.*;
import horst.*;

public class HTMLTest extends JFrame {


	
	public HTMLTest() {
		
		super("HTML Test");
		
		new PlafPanel(this);
		PlafPanel.setNativeLookAndFeel(true);
		
		setDefaultCloseOperation(3);



		String file = "d:/download/htmlwindowdemo_java2/htmlwindow/source.txt";


		HTMLWindow hw = new HTMLWindow();
		getContentPane().add(hw, BorderLayout.CENTER);
		
		HTMLPane hp = hw.getHTMLPane();
		try {
			BufferedReader br = new BufferedReader(new java.io.FileReader(file));
			hp.openPage(br, null);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}


		pack();
		setVisible(true);

	}

	public static void main(String[] args) {
		new HTMLTest();
	}

}