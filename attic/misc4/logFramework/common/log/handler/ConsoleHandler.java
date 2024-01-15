package common.log.handler;

import java.text.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import common.swing.*;
import common.util.AppProperties;
import EDU.oswego.cs.dl.util.concurrent.QueuedExecutor;
import common.log.*;

public class ConsoleHandler extends Handler {
	
	private JFrame consoleFrame;
	private ConsolePane cp;
	private PrintWriter pw;
	private QueuedExecutor executor;
	private LogEventFormat format;
	
	public ConsoleHandler(String prefix, String name) {
		String propertyPrefix = prefix + name;
		
				
		String title  = AppProperties.getStringProperty(propertyPrefix + ".title", "Log Console");		
		String widthStr  = AppProperties.getStringProperty(propertyPrefix + ".width", "800");
		String heightStr = AppProperties.getStringProperty(propertyPrefix + ".height", "400");
		
		int width;
		int height;
		
		try {
			 width = Integer.parseInt(widthStr);
		} catch (NumberFormatException nfe) {
			width = 800;
		}
		
		try {
			 height = Integer.parseInt(heightStr);
		} catch (NumberFormatException nfe) {
			height = 400;
		}
		
		init(title, width, height);
	}
	
	public ConsoleHandler(String title, int width, int height) {
		init(title, width, height);
	}

	private void init(String title, int width, int height) {
		consoleFrame = new JFrame(title);
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
		format = new LogEventFormat();	
	}
	

	public void handle(final LogEvent event) {
		try {	
			executor.execute(new Runnable() {
					public void run() {
						handleBackground(event);	
						cp.scrollToEnd();
					}});
		} catch (InterruptedException ie) {}	
	}
	
	private void handleBackground(LogEvent event) {
		pw.print(format.format(event));
	}
	
	
	private void cleanUpBackground() {
		pw.close();
		consoleFrame.dispose();
		executor.getThread().interrupt();
	}	

	public void cleanUp() {
		try {
			executor.execute(new Runnable() {
					public void run() {
						cleanUpBackground();	
					}});
		} catch (InterruptedException ie) {}
	}
	
	public static void main(String args[]) {
		try {
			Log.addHandler(new ConsoleHandler("Log Test", 800, 400));
			//Log.addHandler(new StandardOutHandler());
			for (int i = 0; i < 100; i++) {
				Log.log("test", "test message " + i );
			}
			System.out.println("END LOG");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ie) {}

			Log.cleanUp();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}