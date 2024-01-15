package common.log;

import java.text.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;
import EDU.oswego.cs.dl.util.concurrent.QueuedExecutor;

public class ConsoleLogger extends Logger {
	
	private JFrame consoleFrame;
	private ConsolePane cp;
	private PrintWriter pw;
	private QueuedExecutor executor;
	
	public ConsoleLogger(int width, int height) {
		consoleFrame = new JFrame("Console Logger");
		consoleFrame.getContentPane().setLayout(new BorderLayout());
		cp = new ConsolePane();
		pw = cp.createPrintWriter();
		consoleFrame.getContentPane().add(cp, BorderLayout.CENTER);
		
		JButton clearButton = new JButton("Clear");
		consoleFrame.getContentPane().add(clearButton, BorderLayout.SOUTH);
		clearButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												cp.clear();
											}});
										
		
		consoleFrame.setSize(width,height);
		consoleFrame.setVisible(true);
		executor = new QueuedExecutor();		
	}
	
	private void writeLogEntryBackground(String logEntry) {
		pw.print(getTimeString());
		pw.print(" | ");
		pw.println(logEntry);	
	}
	
	public synchronized void writeLogEntry(final String logEntry) {		
		try {
			executor.execute(new Runnable() {
					public void run() {
						writeLogEntryBackground(logEntry);	
						cp.scrollToEnd();
					}});
		} catch (InterruptedException ie) {
		}
	}
	
	private void cleanUpBackground() {
		pw.close();
		consoleFrame.dispose();
		executor.getThread().interrupt();
	}	
	
	public synchronized void cleanUp() {
		try {
			executor.execute(new Runnable() {
					public void run() {
						cleanUpBackground();	
					}});
		} catch (InterruptedException ie) {
		}
	}
	
}