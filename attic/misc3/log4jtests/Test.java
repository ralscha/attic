
import org.apache.log4j.*;
import org.apache.log4j.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Test extends JFrame {

	private TextPaneAppender tpa;
  private Random rand;

	public Test()  {
		super("Logging");
		
		rand = new Random();

		//tpa = new TextPaneAppender(new PatternLayout("%-4r %-5p %c{2} %M.%L %x - %m\n"), "Test");
	
		setDefaultCloseOperation(3);
		JTextPane tp = new JTextPane();
		//tpa.setTextPane(tp);
		
		JScrollPane scrollPane = new JScrollPane(tp);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);	
		
		JButton button = new JButton("start");
		getContentPane().add(button, BorderLayout.SOUTH);
		
		button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							Thread t = new Thread(new Runnable() {
									public void run() {
										log();
									}});
							t.start();
	            Thread t2 = new Thread(new Runnable() {
									public void run() {
										log();
									}});
							t2.start();
						}
					});
			
		setSize(200,200);
		setVisible(true);
	}

	private void log() {
		
		Category cat = Category.getInstance(getClass().getName());
    Category cat2 = Category.getInstance("USER");
		cat.debug("Debug info");
		
		Calendar cal = null; 
		try {
			cal.get(Calendar.HOUR);
		} catch (Exception e) {
			cat.debug("NULLPOINTER", e);
		}
		
		for (int i = 0; i < 20; i++) {
      NDC.push("i = " + i);
      cat2.info("Benutzer hat sich eingeloggt");
      cat2.debug("hallo wie geht's denn so");
			cat.info(String.valueOf(i));
      NDC.pop();
			try {
				Thread.sleep(rand.nextInt(10)*1000);
			} catch (InterruptedException ie) { }

		}
	}

	public static void main(String[] args) {
	
		//BasicConfigurator.configure();
		//BasicConfigurator.flagAsShippedCode();
		
		PropertyConfigurator.configure("Test.lcf");
		new Test();

	
	}
}