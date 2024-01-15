

import java.awt.image.*;
/**
 * Copyright Magazine zum Globus
 * Insert the type's description here.
 * Creation date: (30.05.2001 07:56:26)
 * @author: Wirth Denis
 */
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class AuthStartScreen extends JApplet implements ActionListener
{
	private Font font;
	static final String message = "User Security";

	//Buttons
	private JButton search;
	private JButton add;
	private JButton go;
	
	//Components
	private JComponent page;
	
	//Panels
	private JPanel panelSouth;
	private JPanel panelCenter;

	//Labels
	private JLabel racfIDLabel;
	private JLabel nameLabel;
	
/**
 * Insert the method's description here.
 * Creation date: (31.05.2001 15:16:10)
 * @param e java.awt.event.ActionEvent
 */
public void actionPerformed(ActionEvent e) 
{
	String cmd = e.getActionCommand();
	if (cmd.equals("Beenden")) clear();
		
}
/**
 * Insert the method's description here.
 * Creation date: (31.05.2001 15:18:09)
 */
public void clear() 
{
	Graphics g = this.getGraphics();
	g.setColor(this.getBackground());
	g.fillRect(0,0, this.getSize().width, this.getSize().height);	
}
/**
 * Insert the method's description here.
 * Creation date: (31.05.2001 07:39:14)
 * @param g java.awt.Graphics
 */
protected BufferedImage createImage(int w, int h, int c) 
{
	BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics();
	
	g.setColor(Color.red);
	g.fillOval(10, 10, 360, 100);
	
	g.setColor(Color.blue);
	g.drawOval(10, 10, 360, 100);
	g.drawOval( 9,  9, 362, 102);
	g.drawOval( 8,  8, 364, 104);
	g.drawOval( 7,  7, 366, 106);

	g.setColor(Color.black);
	g.setFont(font);
	g.drawString(message, 40, 75);

	return image;
}
/**
 * Initializes the applet.
 * 
 * @see #start
 * @see #stop
 * @see #destroy
 */
public void init() {

	this.getContentPane().setLayout(new BorderLayout());
	
	font = new Font("Helvetica", Font.BOLD, 48);

	panelSouth = new JPanel();
	search = new JButton("Suchen");
	add    = new JButton("Hinzufügen");
	panelSouth.add(search);
	panelSouth.add(add);
	
	this.getContentPane().add(BorderLayout.SOUTH, panelSouth);

	page = new JLabel(new ImageIcon(createImage(380, 200, 0)));

	this.getContentPane().add(BorderLayout.NORTH, page);

	setListeners();
	
	this.setSize(380, 220); 	

}
/**
 * Initializes the applet.
 * 
 * @see #start
 * @see #stop
 * @see #destroy
 */
public void initSearchScreen() {

  this.getContentPane().removeAll();
	//this.getContentPane().setLayout(new BorderLayout());
	
	panelCenter = new JPanel();
	nameLabel   = new JLabel("RACF ID");
	racfIDLabel = new JLabel("Name");
	panelCenter.add(nameLabel);
	panelCenter.add(racfIDLabel);
	
	this.getContentPane().add(BorderLayout.CENTER, panelCenter);


	panelSouth = new JPanel();
	search = new JButton("Suchen");
	add    = new JButton("Hinzufügen");
	panelSouth.add(search);
	panelSouth.add(add);


	go = new JButton("Go!");
	panelSouth.add(go);

	this.getContentPane().add(BorderLayout.SOUTH, panelSouth);
  this.getContentPane().setVisible(false);
  this.getContentPane().setVisible(true);

	setListeners();
	
	this.setSize(380, 220); 	

}
/**
 * Insert the method's description here.
 * Creation date: (31.05.2001 16:03:33)
 */
public void setListeners() 
{
	search.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			initSearchScreen();
			
		}
	});

	add.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("so is this one...!");
		}
	});
}
}
