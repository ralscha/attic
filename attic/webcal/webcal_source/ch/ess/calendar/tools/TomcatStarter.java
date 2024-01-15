
package ch.ess.calendar.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Locale;

public class TomcatStarter extends JFrame {

	private JLabel status;
	private JButton stopButton;
	
	private final static String startText = "Server is running...";
	
   public TomcatStarter() {
		super("ESS Web Calendar Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		
		stopButton  = new JButton("Stop");
		status = new JLabel(startText);
		
		start();
	
		
		stopButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								stop();
							}
						});
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));		
		buttonPanel.add(stopButton);
		
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		status.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
		getContentPane().add(status, BorderLayout.SOUTH);
		
		
		//pack();
		setSize(new Dimension(250,90));
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
	         			public void windowClosing(WindowEvent e) {
	         				stop();
	         			}});
							
							

		
   }

	private void start() {
		org.apache.tomcat.startup.Tomcat.main(new String[] {});
		status.setText(startText);
	}

	private void stop() {
		org.apache.tomcat.startup.Tomcat.main(new String[] {"-stop"});		
		
	}


	public static void main(String[] args) {
		new TomcatStarter();
	}
}