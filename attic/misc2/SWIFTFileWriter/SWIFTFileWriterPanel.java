/*
		A basic implementation of the JFrame class.
*/

import com.sun.java.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.JFrame;
import com.sun.java.swing.JProgressBar;
import com.sun.java.swing.JButton;
import com.sun.java.swing.JLabel;
import java.util.*;
import java.io.*;
import java.text.*;

public class SWIFTFileWriterPanel extends JFrame
{
    private Random rand;
    private int randNumber;
    private Timer timer;
    private Vector inputHeadData;
    private Vector inputMsgData;
    private int tosn;
    private DecimalFormat form;
    private String path;
    
	public SWIFTFileWriterPanel(String args[])
	{
        tosn = 1;
        if (args.length == 2) {
            tosn = Integer.parseInt(args[0]);
            path = args[1];
        } else {
            System.out.println("java SWIFTFileWriterPanel <startTOSN> <path>");
            System.exit(0);
        }

        form = new DecimalFormat("000000");

	    inputHeadData = new Vector();
	    inputMsgData = new Vector();
	    loadFile();
        rand = new Random();	
        randNumber = Math.abs(rand.nextInt() % 10 + 1);
        timer = new Timer(1000, new ActionListener() 
                            {
                                public void actionPerformed(ActionEvent event)
                                {
                                    incProgress();
                                }
                            });                                
        
	    
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setTitle("SWIFT File Writer for Testing ");
		setResizable(false);
		getContentPane().setLayout(null);
		setVisible(false);
		setSize(402,201);
	//	setBackground(new Color(-3355444));
		progressBar = new com.sun.java.swing.JProgressBar();
		progressBar.setBounds(59,48,284,36);
	//	progressBar.setFont(new Font("Dialog", Font.PLAIN, 12));
	//	progressBar.setForeground(new Color(-6710836));
	//	progressBar.setBackground(new Color(-3355444));
		getContentPane().add(progressBar);
		startButton = new com.sun.java.swing.JButton();
		startButton.setText("Start");
		startButton.setActionCommand("S");
		startButton.setBounds(139,96,124,39);
	//	startButton.setFont(new Font("Dialog", Font.BOLD, 12));
	//	startButton.setForeground(new Color(0));
	//	startButton.setBackground(new Color(-3355444));
		getContentPane().add(startButton);
		statusLabel = new com.sun.java.swing.JLabel();
		statusLabel.setText("Ready");
		statusLabel.setHorizontalAlignment(com.sun.java.swing.SwingConstants.CENTER);
		statusLabel.setHorizontalTextPosition(com.sun.java.swing.SwingConstants.CENTER);
		statusLabel.setBounds(93,12,215,29);
	//	statusLabel.setFont(new Font("Dialog", Font.BOLD, 12));
	//	statusLabel.setForeground(new Color(-10066279));
	//	statusLabel.setBackground(new Color(-3355444));
		getContentPane().add(statusLabel);
		//}}
        progressBar.setMaximum(randNumber);
		//{{INIT_MENUS
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		startButton.addActionListener(lSymAction);
		//}}
		
	    try {
            UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { }          
		
	}


	public void setVisible(boolean b)
	{
		if (b)
			setLocation(50, 50);
		super.setVisible(b);
	}

	static public void main(String args[])
	{	    	    
		(new SWIFTFileWriterPanel(args)).setVisible(true);
	}

	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension d = getSize();

		super.addNotify();

		if (fComponentsAdjusted)
			return;
		// Adjust components according to the insets
		setSize(insets().left + insets().right + d.width, insets().top + insets().bottom + d.height);
		Component components[] = getContentPane().getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets().left, insets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}

	// Used for addNotify check.
	boolean fComponentsAdjusted = false;

	//{{DECLARE_CONTROLS
	com.sun.java.swing.JProgressBar progressBar;
	com.sun.java.swing.JButton startButton;
	com.sun.java.swing.JLabel statusLabel;
	//}}

	//{{DECLARE_MENUS
	//}}

    void loadFile()
    {
        String line;
        
        try {
            BufferedReader inHead = new BufferedReader(new FileReader("SWIFTInputHeader.dat"));
            BufferedReader inMsg  = new BufferedReader(new FileReader("SWIFTInputMessage.dat"));
            while ((line = inHead.readLine()) != null)
                inputHeadData.addElement(line);

            while ((line = inMsg.readLine()) != null)
                inputMsgData.addElement(line);
                
            inHead.close();
            inMsg.close();
            
        } catch (FileNotFoundException fe) {
            System.out.println("File not found: SWIFTInput.dat");
        } catch (IOException ioe) {
            System.out.println("IOException : "+ioe);
        }

    }

    void writeFile()
    {
        
        String headerFilename = path+"\\SI_Head_"+System.currentTimeMillis()+".dat";
        String msgFilename    = path+"\\SI_Msg_"+System.currentTimeMillis()+".dat";
        
        try {
            PrintWriter pwHead = new PrintWriter(new FileWriter(headerFilename));
            PrintWriter pwMsg = new PrintWriter(new FileWriter(msgFilename));
            
            String helpStr, mTOSN;
            Enumeration e;
            
            e = inputHeadData.elements();
            int i = 0;
            
            while (e.hasMoreElements()) {
                helpStr = (String)e.nextElement();
                mTOSN = helpStr.substring(0,6);
                pwHead.println(form.format(tosn)+helpStr.substring(6));
                        
                while (i < inputMsgData.size() && ((helpStr = (String)inputMsgData.elementAt(i)).substring(0,6).equals(mTOSN))) {
                    pwMsg.println(form.format(tosn)+helpStr.substring(6));
                    i++;
                }
                tosn++;
            }
            
            pwHead.close();
            pwMsg.close();
        } catch (IOException ioe) {
            System.out.println("IOException : "+ioe);
        }
    }

    synchronized void incProgress()
    {
        if ((progressBar.getValue() + 1) >= progressBar.getMaximum())
        {
            writeFile();
            
            randNumber = Math.abs(rand.nextInt() % 10 + 1);
            progressBar.setMaximum(randNumber);
            progressBar.setValue(0);
   	        statusLabel.setText("Waiting "+randNumber + " s");

        }
        else
        {
            progressBar.setValue(progressBar.getValue() + 1);            
            statusLabel.setText("Waiting "+ (progressBar.getMaximum() - progressBar.getValue()) +" s");
        }
        
    }

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == SWIFTFileWriterPanel.this)
				JFrame1_WindowClosing(event);
		}
	}

	void JFrame1_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false); // hide the Frame
		dispose();	     // free the system resources
		System.exit(0);    // close the application
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == startButton)
				startButton_actionPerformed(event);
		}
	}

	void startButton_actionPerformed(java.awt.event.ActionEvent event)
	{
                			 
		//{{CONNECTION
		// setValue...
		{
		    if (timer.isRunning())
		    {
    			startButton.setText("Start");
    			statusLabel.setText("Ready");
    			
                randNumber = Math.abs(rand.nextInt() % 10 + 1);
                progressBar.setMaximum(randNumber);
                progressBar.setValue(0);
    						
    			timer.stop();
    			
    		}
    		else
    	    {
    	        startButton.setText("Stop");
    	        statusLabel.setText("Waiting "+randNumber+" s");
    	        timer.start();
    	    }
		}
		//}}
			 
	}
}