package gtf.usermanager;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class SplashWindow extends JWindow {


	public SplashWindow() {
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 3);
		Border empty = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		
		Border compound = BorderFactory.createCompoundBorder(blackline, empty);
		
		JLabel text = new JLabel("Loading GtfUserManager...");
		text.setBorder(compound);
		text.setFont(new Font("Verdana", Font.PLAIN, 20));
		getContentPane().add(text);
		
		pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		screenSize.height = screenSize.height/2;
		screenSize.width = screenSize.width/2;
		size.height = size.height/2;
		size.width = size.width/2;
		setLocation(screenSize.width - size.width, screenSize.height - size.height);
	}

}